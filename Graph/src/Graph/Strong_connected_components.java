package Graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Strong_connected_components {

	int count=0;
	public Stack<Integer> stack_topoSort;
	public ArrayList<Integer> newlist;
	public ArrayList<ArrayList<Integer>> lists;
	public Strong_connected_components(Graph G) {
		// TODO Auto-generated constructor stub
	
		//DFSearch(G, s);
		stack_topoSort = new Stack<>();
		for(int i=0;i<G.getV();i++){
			if(G.marked[i] == false){
				DFSearch(G, i);
				stack_topoSort.push(i);
				
			}
		}
		for(int i=0;i<G.marked.length;i++){
			G.marked[i] = false;
		}
		lists = new ArrayList<>();
		for(int i=0;i<G.getV();i++){
			int w = stack_topoSort.pop();
			if(G.marked[i] == false){
				newlist = new ArrayList<>();
				DFSearch_2(G, w);
				lists.add((ArrayList<Integer>) newlist);
			}
			
		}
	}
	public void DFSearch(Graph G,int s){
		count++;
		G.fistfindtime[s] = count;
		G.marked[s] = true;
		System.out.println(s);
		for(int w:G.adj(s)){
			if(G.marked[w] == false){
				G.parent[w] = s;
				DFSearch(G, w);
				G.endtime[w] = count;
				stack_topoSort.push(w);
			}
		}
	}
		
	public void DFSearch_2(Graph G,int s){
		G.marked[s] = true;
		newlist.add(s);
		//System.out.println(s);
		for(int w:G.adj_reverse(s)){
			if(G.marked[w] == false){
				DFSearch_2(G, w);
			}
		}
	}
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		Graph G = new Graph("F:/graph.txt");
		Strong_connected_components strong_connected_components = new Strong_connected_components(G);
		System.out.println(strong_connected_components.lists);
		
		
	}

}
