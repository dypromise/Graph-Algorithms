package Graph;

import java.io.IOException;
import java.util.ArrayList;

public class DAG_shortest_path_SingleSource {
	private int[] distance;
	private int[] parent;
	private int NIL = 100000;
	
	public DAG_shortest_path_SingleSource(Graph_weighted G,int s){
		distance = new int[G.getV()+1];
		parent = new int[G.getV()+1];
		DAG_shortest_alg(G, s);
		/*for(int i=1;i<parent.length;i++){
			System.out.println(i+": "+parent[i]+" ");
		}*/
	}
	public void DAG_shortest_alg(Graph_weighted G,int s){
		INIT_SINGLE_SOURCE(G, s);
		Topolosort topolosort = new Topolosort(G);
		for(int i=topolosort.vertexlist.size()-1;i>=0;i--){
			int u = topolosort.vertexlist.get(i);
			//System.out.println(u);
			for(Node v:G.getadj(u)){
				RELEX(u, v.vertex, v.weight);
			}
		}
		
	}
	public void INIT_SINGLE_SOURCE(Graph_weighted G,int s){
		for(int i=0;i<=G.getV();i++){
			distance[i] = NIL;
			parent[i] = -1;
		}
		distance[s] = 0;
	}
	public void RELEX(int u,int v, int weight){
		if((distance[u]!=NIL||distance[v]!=NIL)&&distance[v]>distance[u]+weight){
			distance[v] = distance[u] +weight;
			parent[v] = u;
		}
	}
	public void PRINT_PATH(int s, int v){
		if(s==v)
			System.out.println(s);
		else{
			PRINT_PATH(s, parent[v]);
			System.out.println(v);
		}
	}
	class Topolosort{
		private ArrayList<Integer> vertexlist;
		private boolean[] marked;
		private int[] parent;
		public Topolosort(Graph_weighted G){
			vertexlist = new ArrayList<>();
			marked = new boolean[G.getV()+1];
			parent = new int[G.getV()+1];
			for(int i=1;i<=G.getV();i++){
				marked[i] = false;
				parent[i] = -1;
			}
			topologicalsort(G);
		}
		public void topologicalsort(Graph_weighted G){
			DFS(G, 1);
			vertexlist.add(1);//ºÜÖØÒª£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡
		}
		public void DFS(Graph_weighted G,int s){
			marked[s] = true;
			for(Node node:G.getadj(s)){
				if(marked[node.vertex]==false){
					parent[node.vertex] = s;
					DFS(G, node.vertex);
					vertexlist.add(node.vertex);
				}
			}
		}
		
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Graph_weighted G = new Graph_weighted("F:/graph_4.txt");
		DAG_shortest_path_SingleSource dsps = new DAG_shortest_path_SingleSource(G, 1);
		dsps.PRINT_PATH(1, 6);
				
	}

}
