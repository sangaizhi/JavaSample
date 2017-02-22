package datastructure.graph;

import datastructure.queue.LinkQueue;
import datastructure.stack.StackX;
import datastructure.vertext.Vertex;

/**
 * ͼ
 * @author saz
 *
 */
public class Graph {
	// ��󶨵���
	private final int MAX_VERTS = 20;

	// ���㼯��
	private Vertex[] vertexs;

	// �ڽӾ����ʾͼ
	private int[][] adjMat;

	// ��������
	private int nVerts;

	// ������ȱ���ʱ�洢��ʱ����
	private StackX stackX;

	// ������ȱ���ʱ�洢��ʱ
	private LinkQueue linkQueue;

	public Graph() {
		vertexs = new Vertex[MAX_VERTS];
		adjMat = new int[MAX_VERTS][MAX_VERTS];
		nVerts = 0;
		for (int i = 0; i < MAX_VERTS; i++) {
			for (int j = 0; j < MAX_VERTS; j++) {
				adjMat[i][j] = 0;
			}
		}
		stackX = new StackX();
		linkQueue = new LinkQueue<>();
	}

	/**
	 * ����һ������
	 * 
	 * @param label
	 */
	public void addVertex(char label) {
		vertexs[nVerts++] = new Vertex(label, false);
	}

	/**
	 * ����һ����
	 * 
	 * @param start
	 * @param end
	 */
	public void addEdge(int start, int end) {
		adjMat[start][end] = 1;
		adjMat[end][start] = 1;
	}

	/**
	 * ��ʾĳ������
	 * 
	 * @param v
	 */
	public void displayVertex(int v) {
		System.out.println(vertexs[v].getValue());
	}

	/**
	 * �õ���V�����ڽ���δ���ʹ��Ķ�����
	 * 
	 * @param v
	 * @return
	 */
	public int getAdjUnVisitedVertex(int v) {
		for (int i = 0; i < nVerts; i++) {
			if (adjMat[v][i] == 1 && vertexs[i].isWasVisited() == false) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * ������ȱ���
	 */
	public void deepFirstSearch() {
		vertexs[0].setWasVisited(true);
		displayVertex(0);
		stackX.push(0);
		while (!stackX.isEmpty()) {
			int v = getAdjUnVisitedVertex((int) stackX.peek());
			if (v == -1) {
				stackX.pop();
			} else {
				vertexs[v].setWasVisited(true);
				displayVertex(v);
				stackX.push(v);
			}
		}
		for (int j = 0; j < nVerts; j++) {
			vertexs[j].setWasVisited(false);
		}
	}

	/**
	 * ������ȱ���
	 */
	public void broadFirstSearch() {
		vertexs[0].setWasVisited(true);
		displayVertex(0);
		linkQueue.insert(0);
		int v2;
		while (!linkQueue.isEmpty()) {
			int v1 = (int) linkQueue.remove();
			while ((v2 = getAdjUnVisitedVertex(v1)) != -1) {
				vertexs[v2].setWasVisited(true);
				displayVertex(v2);
				linkQueue.insert(v2);
			}
		}
		for (int i = 0; i < nVerts; i++) {
			vertexs[i].setWasVisited(false);
		}
	}

}
