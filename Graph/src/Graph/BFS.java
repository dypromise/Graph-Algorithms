package Graph;

import java.io.IOException;
import java.security.KeyStore.PrivateKeyEntry;
import java.util.LinkedList;
import java.util.Queue;

public class BFS {
	private int[] parent;
	private boolean[] marked;
	private Queue<Integer> queue;
	public BFS(Graph G,int s){
		parent = new int[G.getV()];
		marked = new boolean[G.getV()];
		queue = new LinkedList<>();
		for(int i=0;i<G.getV();i++){
			parent[i] = -1;
			marked[i] = false;
		}
		BFSearch(G, s);
		
		
	}
	public void BFSearch(Graph G,int s){
		queue.add(s);
		while(queue.size()!=0){
			int v = queue.poll();
			System.out.println(v);
			for(int w:G.adj(v)){ 
				if(marked[w]==false){
					marked[w] = true;
					queue.add(w);
					parent[w] = v;
				}
			}
		}
	}
	public void printpath(int s,int v){
		if(s==v){
			System.out.println(s);
		}
		else{
			printpath(s, parent[v]);
			System.out.println(v);
		}
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Graph G = new Graph("F:/graph.txt");
		BFS bfs = new BFS(G, 0);
		bfs.printpath(0, 3);
	}

}
