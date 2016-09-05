package Graph;

import java.io.IOException;
import java.util.Stack;

public class DFS {
	int count=0;
	public Stack<Integer> stack_topoSort;
	public DFS(Graph G){
		//DFSearch(G, s);
		stack_topoSort = new Stack<>();
		for(int i=0;i<G.getV();i++){
			if(G.marked[i] == false){
				DFSearch(G, i);
				stack_topoSort.push(i);
				
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
	public void topologicalSort(Graph G){
		System.out.println("toposort:");
		while (stack_topoSort.size()!=0){
			System.out.println(stack_topoSort.pop());
		}
		
	}
	public static void main(String[] args) throws IOException, CloneNotSupportedException {
		// TODO Auto-generated method stub
		Graph G = new Graph("F:/graph.txt");
		for(int w:G.adj(3)){
			System.out.print(" "+w+":"+G.marked[w]);
		}
		System.out.println();
		for(int w:G.adj_reverse(3)){
			System.out.print(" "+w+":"+G.marked[w]);
		}

		System.out.println();
		DFS dfs = new DFS(G);
		//dfs.topologicalSort(G);
		System.out.println("reverse:");
		
		
	}

}
