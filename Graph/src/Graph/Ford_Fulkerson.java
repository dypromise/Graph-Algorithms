package Graph;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;

import javax.xml.bind.ParseConversionEvent;

class BFS_for_BF {
	public int[] parent;
	public int[] weight;
	public boolean[] marked;
	private LinkedList<Integer> queue;

	public BFS_for_BF(Graph_weighted G, int s, int t) {
		parent = new int[G.getV() + 1];
		marked = new boolean[G.getV() + 1];
		weight = new int[G.getV() + 1];
		queue = new LinkedList<>();

		for (int i = 0; i <= G.getV(); i++) {
			parent[i] = -1;
			marked[i] = false;
			weight[i] = 0;
		}
		// BFS(G, s, t);

	}

	public boolean BFS(Graph_weighted G, int s, int t) {
		queue.add(s);
		marked[s] = true;
		while (!queue.isEmpty()) {
			int v = queue.poll();
			for (Node w : G.getadj(v)) {
				if (w.weight != 0 && marked[w.vertex] == false) {
					marked[w.vertex] = true;
					parent[w.vertex] = v;
					weight[w.vertex] = w.weight;
					queue.add(w.vertex);
					if (w.vertex == t) {
						return true;
					}
				}
			}
		}
		return false;
	}
}


/**
 * ���������
 * ���������������Ҫע����ǣ�ԭ����������ͼ������û�з���ƽ�бߣ��з���ƽ�бߣ���Ҫ�����½ڵ㹹���û��
 * ����ߵ�����ͼ���������︣�¸�����ɭ�㷨���õ���Gֱ�Ӿ��ǲд�����ͼ���������ﹹ���ʼGʱ����(u,v)����E����
 * weight(u,v) = c(u,v) - f(u,v) = c(u,v) - 0 = c(u,v), weight(v,u) = 0;
 * @author ����
 *
 */
public class Ford_Fulkerson {
	private BFS_for_BF bf;
	private Stack<Integer> stack;

	public Ford_Fulkerson(Graph_weighted G, int s, int t) {
		bf = new BFS_for_BF(G, s, t);
		stack = new Stack<>();

		while (bf.BFS(G, s, t) == true) {
			int tmp = t;
			int min = 100000;
			while (tmp != s) {
				stack.push(tmp);
				if (bf.weight[tmp] < min) {
					min = bf.weight[tmp];
				}
				tmp = bf.parent[tmp];
			}
			int u = s;
			while (!stack.isEmpty()) {
				int v = stack.pop();
				G.set_weight(u, v, G.get_weight(u, v) - min);
				G.set_weight(v, u, G.get_weight(v, u) + min);
				u = v;
			}
			bf = new BFS_for_BF(G, s, t);
		}
		int max_flow = 0;
		for (Node w : G.getadj(t)) {
			max_flow += w.weight;
		}
		System.out.println(max_flow);
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Graph_weighted G = new Graph_weighted("F:/max_flow.txt");
		Ford_Fulkerson ff = new Ford_Fulkerson(G, 1, 4);
	}

}
