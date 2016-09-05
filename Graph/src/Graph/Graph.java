package Graph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Graph implements Cloneable {
	private final int V;
	private int E;
	private ArrayList<ArrayList<Integer>> adj;
	private ArrayList<ArrayList<Integer>> adj_reverse;
	public  int[] parent;
	public  boolean[] marked;
	public  int[] fistfindtime;
	public int[] endtime;
	
	public Graph(String filename) throws IOException{
		
		BufferedReader bufferedReader = new BufferedReader(new FileReader("F:/graph.txt"));
		this.V = Integer.parseInt(bufferedReader.readLine());
		this.E = 0;
		int E = Integer.parseInt(bufferedReader.readLine());
		adj = new ArrayList<>(V);
        adj_reverse = new ArrayList<>(V);
		parent = new int[V];
		marked = new boolean[V];
		fistfindtime = new int[V];
		endtime = new int[V];
		for(int i=0;i<this.V;i++){
			adj.add(new ArrayList<>());
			adj_reverse.add(new ArrayList<>());
			parent[i] = -1;
			marked[i] = false;
			fistfindtime[i] = 100000;
			endtime[i] = 100000;
		}
		for(int i=0;i<E;i++){
			String tmp = bufferedReader.readLine();
			addEdge(Integer.parseInt(tmp.split(" ")[0]), Integer.parseInt(tmp.split(" ")[1]));
		}
	}
	public void addEdge(int v,int w){
		adj.get(v).add(w);
		adj_reverse.get(w).add(v);
		E++;
	}
	public Iterable<Integer> adj(int v){
		return this.adj.get(v);
	}
	public Iterable<Integer> adj_reverse(int v){
		return this.adj_reverse.get(v);
	}
	public int getV(){
		return this.V;
	}
	public int getE(){
		return this.E;
	}
	
	

}
