package Graph;

import java.io.IOException;

public class BellmanFord {
	private int[] distance;
	private int[] parent;
	private int NIL = 100000;
	
	public BellmanFord(Graph_weighted G,int s){
		distance = new int[G.getV()+1];
		parent = new int[G.getV()+1];
		System.out.println(Bellman_ford_alg(G,s));
		//PRINT_PATH(s, v);
		
	}
	public boolean Bellman_ford_alg(Graph_weighted G,int s){
		INIT_SIGLE_SOURCE(G, s);
		for(int i=0;i<G.getV()-1;i++){
			for(Edge edge:G.getEdgeList()){
				RELEX(edge.v, edge.w,edge.weight);
				
			}
		}
		/*for(int i=1;i<distance.length;i++){
			System.out.println(i+": "+distance[i]);
		}*/
		for(Edge edge:G.getEdgeList()){
			if(distance[edge.w]>distance[edge.v]+edge.weight){
				//System.out.println(edge.v+" "+edge.w+" "+edge.weight);
				return false;
				
			}
				
		}
		return true;
		
	}
	public void INIT_SIGLE_SOURCE(Graph_weighted G,int s){
		for(int i=1;i<=G.getV();i++){
			distance[i] = NIL;
			parent[i] = -1;
		}
		distance[s] = 0;
	}
	public void RELEX(int u,int v,int weight){
		if((distance[u]!=NIL||distance[v]!=NIL)&&distance[v]>distance[u]+weight){
			distance[v] = distance[u]+weight;
			parent[v] = u;
		}
	}
	public int weight(Graph_weighted G,int u,int v){
		return G.getadj_(v).get(v).weight;
	}
	public void PRINT_PATH(int s, int v){
		if(s==v) System.out.println(s);
		else{
			PRINT_PATH(s, parent[v]);
			System.out.println(v);
		}
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Graph_weighted G = new Graph_weighted("F:/graph_3.txt");
		BellmanFord bellmanFord = new BellmanFord(G, 1);
		bellmanFord.PRINT_PATH(1, 4);
	}

}
