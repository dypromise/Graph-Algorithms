package Graph;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;

import javax.xml.bind.ParseConversionEvent;

class BFS_for_BF {
	public int[] parent;
	public int[] weight;
	public boolean[] marked;
	private LinkedList<Integer> queue;

	public BFS_for_BF(Graph_weighted G, int s, int t) {
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

	public boolean BFS(Graph_weighted G, int s, int t) {
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


/**
 * 最大流问题
 * 在最大流问题里需要注意的是，原网络是有向图，而且没有反向平行边（有反向平行边，需要加入新节点构造称没有
 * 反向边的有向图。但是这里福德弗尔克森算法中用到的G直接就是残存网络图。所以这里构造初始G时，若(u,v)属于E，则
 * weight(u,v) = c(u,v) - f(u,v) = c(u,v) - 0 = c(u,v), weight(v,u) = 0;
 * @author 丁杨
 *
 */
public class Ford_Fulkerson {
	private BFS_for_BF bf;
	private Stack<Integer> stack;

	public Ford_Fulkerson(Graph_weighted G, int s, int t) {
		bf = new BFS_for_BF(G, s, t);
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
			bf = new BFS_for_BF(G, s, t);
		}
		int max_flow = 0;
		for (Node w : G.getadj(t)) {
			max_flow += w.weight;
		}
		System.out.println(max_flow);
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Graph_weighted G = new Graph_weighted("F:/max_flow.txt");
		Ford_Fulkerson ff = new Ford_Fulkerson(G, 1, 4);
	}

}
