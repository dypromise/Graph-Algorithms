package Graph;

import java.io.IOException;
import java.util.ArrayList;

import Graph.Prim.BiHeap;

class BinaryHeap {
	public  int[] heap;
	public  int[] position;
	private int heap_size;
	private int[] distance;

	public BinaryHeap(int[] nodearray, int[] distance) {
		heap = nodearray;
		this.distance = distance;
		
		heap_size = heap.length - 1;
		position = heap.clone();

		CREATHEAP();
		for(int i=1;i<position.length;i++){
			System.out.println(i+" :"+position[i]);
		}

	}

	public void ADJUST_UP(int i) {
		while (PARENT(i) >= 1 && GetNodeDistance(i) < GetNodeDistance(PARENT(i))) {
			position[heap[i]] = PARENT(i);
			position[heap[PARENT(i)]] = i;
			int tmp = heap[i];
			heap[i] = heap[PARENT(i)];
			heap[PARENT(i)] = tmp;
		}
	}

	public int EXTRACT_MIN() {
		position[heap[heap_size]] = 1;
		int min = heap[1];
		heap[1] = heap[heap_size];
		heap_size--;
		MAXI_HEAPIFY(1);
		return min;
	}

	public void CREATHEAP() {
		for (int i = heap_size / 2; i >= 1; i--) {
			MAXI_HEAPIFY(i);
		}
	}

	public void MAXI_HEAPIFY(int i) {
		int l = LEFT(i);
		int r = RIGHT(i);
		int min;
		if (l <= heap_size && GetNodeDistance(l) < GetNodeDistance(i))
			min = l;
		else
			min = i;
		if (r <= heap_size && GetNodeDistance(r) < GetNodeDistance(min))
			min = r;
		if (min != i) {
			position[heap[i]] = min;
			position[heap[min]] = i;
			int tmp = heap[i];
			heap[i] = heap[min];
			heap[min] = tmp;
			MAXI_HEAPIFY(min);
		}
	}

	public int LEFT(int i) {
		return i * 2;
	}

	public int RIGHT(int i) {
		return i * 2 + 1;
	}

	public int PARENT(int i) {
		return i / 2;
	}

	public int GetNodeDistance(int i) {
		return distance[heap[i]];
	}

	public int getPosition(int vertex) {
		return position[vertex];
	}

	public int size() {
		return heap_size;
	}
}

public class Dijkstra {
	private int[] distance;
	private int[] parent;
	private int[] nodearray;

	private int NIL = 10000;
	private BinaryHeap binaryHeap;

	public Dijkstra(Graph_weighted G, int s) {
		distance = new int[G.getV() + 1];
		parent = new int[G.getV() + 1];
		nodearray = new int[G.getV() + 1];
		for (int i = 1; i <= G.getV(); i++) {
			nodearray[i] = i;
		}
		Dij_alg(G, s);

	}

	public void Dij_alg(Graph_weighted G, int s) {
		INIT_SIGLE_SOURCE(G, s);
		binaryHeap = new BinaryHeap(nodearray, distance);
		while (binaryHeap.size() != 0) {
			int u = binaryHeap.EXTRACT_MIN();
		
			for (Node v : G.getadj(u)) {
				RELEX_and_ADJUST(u, v.vertex, v.weight);

			}
			
		}
	}

	public void INIT_SIGLE_SOURCE(Graph_weighted G, int s) {
		for (int i = 1; i <= G.getV(); i++) {
			distance[i] = NIL;
			parent[i] = -1;
		}
		distance[s] = 0;
	}

	public void PRINT_PATH(int s, int v) {
		if (s == v)
			System.out.println(s);
		else {
			PRINT_PATH(s, parent[v]);
			System.out.println(v);
		}
	}

	public void RELEX_and_ADJUST(int u, int v, int weight) {
		if ((distance[u] != NIL || distance[v] != NIL) && distance[v] > distance[u] + weight) {
			distance[v] = distance[u] + weight;
			parent[v] = u;
			binaryHeap.ADJUST_UP(binaryHeap.getPosition(v));
		}
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Graph_weighted G = new Graph_weighted("F:/graph_5.txt");
		Dijkstra dijkstra = new Dijkstra(G, 1);
		dijkstra.PRINT_PATH(1, 4);

	}

}
