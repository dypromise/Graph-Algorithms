package Graph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;


/**
 * 逃逸问题：最大流问题
 * 在最大流问题里需要注意的是，原网络是有向图，而且没有反向平行边（有反向平行边，需要加入新节点构造称没有
 * 反向边的有向图。但是这里福德弗尔克森算法中用到的G直接就是残存网络图。所以这里构造初始G时，若(u,v)属于E，则
 * weight(u,v) = c(u,v) - f(u,v) = c(u,v) - 0 = c(u,v), weight(v,u) = 0;
 * @author 丁杨
 *
 */
class Graphfor26_1{
	private final int V;
	private int E;
	private ArrayList<ArrayList<Node>> adj;
	public int s;
	public int t;
	public int m;
	public Graphfor26_1(String filename) throws IOException{
		adj = new ArrayList<>();
		adj.add(null);
		BufferedReader bReader = new BufferedReader(new FileReader(filename));
		int n = Integer.parseInt(bReader.readLine());
		
		t = 2*n*n+2;
		s = 2*n*n+1;
		this.V = t;
		this.E = 0;
		
		for(int i=1;i<=2*n*n+2;i++){
			adj.add(new ArrayList<>());
		}
		for(int i= 1;i<=n;i++){
			for(int j = 1;j<=n;j++){
				int num_up = (i-1)*n+j;
				int num_down = num_up+n*n;
				if(j<n){
					adj.get(num_up).add(new Node(num_down+1, 1, 0, -1));
					adj.get(num_down+1).add(new Node(num_up, 0, 0, -1));//就是这里！需要注意
				}
				if(j>1){
					adj.get(num_up).add(new Node(num_down-1, 1, 0, -1));
					adj.get(num_down-1).add(new Node(num_up, 0, 0, -1));
				}
				if(i<n){
					adj.get(num_up).add(new Node(num_down+n, 1, 0, -1));
					adj.get(num_down+n).add(new Node(num_up, 0, 0, -1));
				}
				if(i>1){
					adj.get(num_up).add(new Node(num_down-n, 1, 0, -1));
					adj.get(num_down-n).add(new Node(num_up, 0, 0, -1));
				}
				if(i==1||i==n||j==1||j==n){
					adj.get(num_up).add(new Node(t,1,0, -1));
					adj.get(t).add(new Node(num_up, 0, 0, -1));
				}
				adj.get(num_down).add(new Node(num_up, 1, 0, -1));
				adj.get(num_up).add(new Node(num_down, 0, 0, -1));
			}
		}
		
		 m = Integer.parseInt(bReader.readLine());
		for(int i = 0;i<m;i++){
			String str = bReader.readLine();
			int x = Integer.parseInt(str.split(" ")[0]);
			int y = Integer.parseInt(str.split(" ")[1]);
			adj.get(s).add(new Node((x-1)*n+y+n*n,1,0,-1));
			adj.get((x-1)*n+y+n*n).add(new Node(s, 0, 0, -1));
		}
		
	}
	public int getV(){
		return this.V;
	}
	public Iterable<Node> getadj(int v){
		return this.adj.get(v);
	}
	public int get_weight(int u, int v){
		for(Node w:this.getadj(u)){
			if(w.vertex == v){
				return w.weight;
			}
		}
		return -1;
	}
	public void set_weight(int u,int v,int weight){
		for(Node w:this.getadj(u)){
			if(w.vertex == v){
				w.weight = weight;
			}
		}
	}
}


class BFS_for_BF_2 {
	public int[] parent;
	public int[] weight;
	public boolean[] marked;
	private LinkedList<Integer> queue;

	public BFS_for_BF_2(Graphfor26_1 G, int s, int t) {
		parent = new int[G.getV() + 1];
		marked = new boolean[G.getV() + 1];
		weight = new int[G.getV() + 1];
		queue = new LinkedList<>();

		for (int i = 0; i <= G.getV(); i++) {
			parent[i] = -1;
			marked[i] = false;
			weight[i] = 0;
		}
		// BFS(G, s, t);

	}

	public boolean BFS(Graphfor26_1 G, int s, int t) {
		queue.add(s);
		marked[s] = true;
		while (!queue.isEmpty()) {
			int v = queue.poll();
			for (Node w : G.getadj(v)) {
				if (w.weight != 0 && marked[w.vertex] == false) {
					marked[w.vertex] = true;
					parent[w.vertex] = v;
					weight[w.vertex] = w.weight;
					queue.add(w.vertex);
					if (w.vertex == t) {
						return true;
					}
				}
			}
		}
		return false;
	}
}

class Ford_Fulkerson_forthisproblem {
	private BFS_for_BF_2 bf;
	private Stack<Integer> stack;

	public Ford_Fulkerson_forthisproblem(Graphfor26_1 G, int s, int t) {
		bf = new BFS_for_BF_2(G, s, t);
		stack = new Stack<>();

		while (bf.BFS(G, s, t) == true) {
			int tmp = t;
			int min = 100000;
			while (tmp != s) {
				stack.push(tmp);
				if (bf.weight[tmp] < min) {
					min = bf.weight[tmp];
				}
				tmp = bf.parent[tmp];
			}
			int u = s;
			while (!stack.isEmpty()) {
				int v = stack.pop();
				G.set_weight(u, v, G.get_weight(u, v) - min);
				G.set_weight(v, u, G.get_weight(v, u) + min);
				u = v;
			}
			bf = new BFS_for_BF_2(G, s, t);
		}
		int max_flow = 0;
		for (Node w : G.getadj(t)) {
			max_flow += w.weight;
		}
		System.out.println(max_flow);
	}

	/*public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Graph_weighted G = new Graph_weighted("F:/max_flow.txt");
		Ford_Fulkerson ff = new Ford_Fulkerson(G, 1, 4);
	}*/

}
public class Problem_26_1 {
	
	public Problem_26_1(Graphfor26_1 G){
		Ford_Fulkerson_forthisproblem ff = new Ford_Fulkerson_forthisproblem(G, G.s, G.t);
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Graphfor26_1 G = new Graphfor26_1("F:/problem_26_1.txt");
		Problem_26_1 problem_26_1 = new Problem_26_1(G);
	}

}
