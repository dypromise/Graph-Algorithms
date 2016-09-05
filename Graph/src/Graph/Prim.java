package Graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Prim {
	
	private BiHeap bHeap;
	private boolean[] inHeap;
	private ArrayList<Node> nodelist;
	public Prim(Graph_weighted G) throws CloneNotSupportedException {
		inHeap = new boolean[G.getV()+1];
		nodelist = new ArrayList<>();
		prim_alg(G);
	}
	public void prim_alg(Graph_weighted G) throws CloneNotSupportedException{
		nodelist.add(null);
		Node node = new Node(1,0,0,-1);
		node.vertex = 1;
		node.parent = -1;
		node.distance = 0;
		nodelist.add(node);
		inHeap[1] = true;
		for(int i=2;i<=G.getV();i++){
			Node node2 = new Node(i,0,100000,-1);
			node2.vertex = i;
			node2.distance = 100000;
			node2.parent = -1;
			nodelist.add(node2);
			inHeap[i] = true;
		}

		bHeap = new BiHeap(nodelist);
		
		while(bHeap.Heap_size>0){
			Node u = bHeap.EXTRACT_MIN();
			System.out.println(u.vertex);
			inHeap[u.vertex] = false;
			for(Node v:G.getadj(u.vertex)){
				int v_position = bHeap.position[v.vertex];
				if(inHeap[v.vertex]&&v.weight<bHeap.nodelist.get(v_position).distance){
					bHeap.nodelist.get(v_position).parent = u.vertex;
					bHeap.nodelist.get(v_position).distance = v.weight;
					bHeap.adjust_up(v_position);
				}
			}
		}
		
	}

	
	class BiHeap{
		private int Heap_size;
		private ArrayList<Node> nodelist;
		private int[] position;
		public BiHeap(ArrayList<Node> nodelist) throws CloneNotSupportedException{
			this.nodelist = nodelist;
			Heap_size = nodelist.size()-1;
			position = new int[nodelist.size()];
			for(int i=1;i<=Heap_size;i++){
				position[i] = i;
			}
			BuildHeap(nodelist);
		}
		public void BuildHeap(ArrayList<Node> nodelist) throws CloneNotSupportedException{
			for(int i=Heap_size/2;i>=1;i--){
				MaxHeapify(i);
			}
		}
		public void MaxHeapify(int i) throws CloneNotSupportedException{
			int l = LEFT(i);
			int r = RIGHT(i);
			int min;
			if(l<=Heap_size&&nodelist.get(l).distance<nodelist.get(i).distance)
				min = l;
			else
				min = i;
			if(r<=Heap_size&&nodelist.get(r).distance<nodelist.get(min).distance)
				min = r;
			if(min!=i){
				position[nodelist.get(i).vertex] =  min;
				position[nodelist.get(min).vertex] = i;
				Node tmp = (Node) nodelist.get(i).clone();
				nodelist.set(i, nodelist.get(min));
				nodelist.set(min, tmp);
				MaxHeapify(min);
			}
			
		}
		private int LEFT(int i){
			return 2*i;
		}
		private int RIGHT(int i){
			return 2*i+1;
		}

		public void adjust_up(int i) throws CloneNotSupportedException{
			while(i/2>=1&&nodelist.get(i).distance<nodelist.get(i/2).distance){
				position[nodelist.get(i).vertex] =  i/2;
				position[nodelist.get(i/2).vertex] = i;
				Node tmp = (Node) nodelist.get(i).clone();
				nodelist.set(i, nodelist.get(i/2));
				nodelist.set(i/2, tmp);
				i/=2;
			}
		}
		public Node EXTRACT_MIN() throws CloneNotSupportedException{
			Node node_min = (Node) nodelist.get(1).clone();
			position[nodelist.get(Heap_size).vertex] = 1;
			nodelist.set(1, nodelist.get(Heap_size));
			Heap_size--;
			MaxHeapify(1);
			return node_min;
		}
		
	}
	
	public static void main(String[] args) throws IOException, CloneNotSupportedException {
		// TODO Auto-generated method stub
	   Graph_weighted G = new Graph_weighted("F:/graph_2.txt");
	   
	   Prim prim = new Prim(G);
	}
	

}
