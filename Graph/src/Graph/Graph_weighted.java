package Graph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class Node implements Cloneable{
	int vertex;
	int weight;
	int distance;
	int parent;
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	public Node(int vertex,int weight,int distance,int parent){
		this.distance = distance;
		this.parent = parent;
		this.weight = weight;
		this.vertex = vertex;
	}
	
}
class Edge{
	int v;
	int w;
	int weight;
		
}

public class Graph_weighted {
	private int V;
	private int E;
	private ArrayList<ArrayList<Node>> adj;
	public ArrayList<Edge> edgelist;

	public Graph_weighted(String filename) throws IOException {
		adj = new ArrayList<>();
		adj.add(null);
		edgelist = new ArrayList<>();
		BuildGraph(filename);
	}

	public void BuildGraph(String filename) throws IOException {
		BufferedReader bReader = new BufferedReader(new FileReader(filename));
		this.V = Integer.parseInt(bReader.readLine());
		this.E = 0;
		
		for(int i= 1 ;i<=V;i++){
			adj.add(new ArrayList<>());
		}
		int E = Integer.parseInt(bReader.readLine());
		for (int i = 0; i < E; i++) {
			String tmp = bReader.readLine();
			int v = Integer.parseInt(tmp.split(" ")[0]);
			int w = Integer.parseInt(tmp.split(" ")[1]);
			int weight = Integer.parseInt(tmp.split(" ")[2]);
			addEdge(v, w, weight);
		}
	}

	public void addEdge(int v, int w, int weight) {
		Node node = new Node(w,weight,0,-1);
		Node node2 = new Node(v,0,0,-1);//最大流问题时直接把G构造成残存网络，所以一开始反向平行边的权重是0
		adj.get(v).add(node);
		adj.get(w).add(node2);
/*		node.vertex = w;
		node.weight = weight;
		adj.get(v).add(node);
		
		node2.vertex = v;
		node2.weight = 0;
		adj.get(w).add(node2);*/
		
		Edge edge = new Edge();
		edge.v = v;
		edge.w = w;
		edge.weight = weight;
		edgelist.add(edge);
		E++;
	}
	
	public int getV(){
		return this.V;
	}
	
	public int getE(){
		return this.E;
	}
	
	public Iterable<Node> getadj(int v){
		return this.adj.get(v);
	}
	public ArrayList<Node> getadj_(int v){
		return this.adj.get(v);
	}
	public Iterable<Edge> getEdgeList(){
		return this.edgelist;
	}
	public void set_weight(int u,int v,int weight){
		for(Node w:this.getadj(u)){
			if(w.vertex == v){
				w.weight = weight;
			}
		}
	}
	public int get_weight(int u,int v){
		for(Node w:this.getadj(u)){
			if(w.vertex == v){
				return w.weight;
			}
		}
		return -1;
	}
	

}
