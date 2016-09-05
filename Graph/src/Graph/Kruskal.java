package Graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

import org.omg.CORBA.UnionMember;

public class Kruskal {

	private ArrayList<Edge> MST_edges;

	public Kruskal(Graph_weighted G) {

		MST_edges = new ArrayList<>();
		Kruskal_alg(G);
	}

	public void Kruskal_alg(Graph_weighted G) {
		Union_Search vertexs_set = new Union_Search(G.getV());
		Collections.sort(G.edgelist, new com_());
		
		for (Edge edge : G.edgelist) {
			int a = vertexs_set.FindSet(edge.v);
			int b = vertexs_set.FindSet(edge.w);
			if (a!=b) {
				MST_edges.add(edge);
				vertexs_set.Union(a, b);

			}
		}

	}

	class Union_Search {
		private ArrayList<Integer> sets;

		public Union_Search(int V) {
			// TODO Auto-generated constructor stub
			sets = new ArrayList<>();
			for (int i = 0; i < V; i++)
				sets.add(-1);
		}

		public int FindSet(int v) {
			while (sets.get(v) != -1)
				v = sets.get(v);
			return v;
		}

		public void Union(int v, int w) {
			sets.set(v, w);
		}
	}



	class com_ implements Comparator<Edge> {

		@Override
		public int compare(Edge o1, Edge o2) {
			// TODO Auto-generated method stub
			if (o1.weight < o2.weight)
				return -1;
			else if (o1.weight > o2.weight)
				return 1;
			else
				return 0;
		}

	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Graph_weighted G = new Graph_weighted("F:/graph_2.txt");
		Kruskal kruskal = new Kruskal(G);
		for(Edge edge:kruskal.MST_edges){
			System.out.println(edge.v+" "+edge.w);
		}
	}

}
