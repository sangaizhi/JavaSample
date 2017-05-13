package org.sangaizhi.datastructure.graph;

import org.sangaizhi.datastructure.queue.LinkQueue;
import org.sangaizhi.datastructure.stack.StackX;
import org.sangaizhi.datastructure.vertext.Vertex;

/**
 * 图
 * @author saz
 *
 */
public class Graph {
	// 最大定点数
	private final int MAX_VERTS = 20;

	// 顶点集合
	private Vertex[] vertexs;

	// 邻接矩阵表示图
	private int[][] adjMat;

	// 顶点数量
	private int nVerts;

	// 深度优先遍历时存储临时数据
	private StackX stackX;

	// 广度优先遍历时存储临时
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
	 * 增加一个顶点
	 *
	 * @param label
	 */
	public void addVertex(char label) {
		vertexs[nVerts++] = new Vertex(label, false);
	}

	/**
	 * 增加一条边
	 *
	 * @param start
	 * @param end
	 */
	public void addEdge(int start, int end) {
		adjMat[start][end] = 1;
		adjMat[end][start] = 1;
	}

	/**
	 * 显示某个顶点
	 *
	 * @param v
	 */
	public void displayVertex(int v) {
		System.out.println(vertexs[v].getValue());
	}

	/**
	 * 得到与V顶点邻接且未访问过的顶点编号
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
	 * 深度优先遍历
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
	 * 广度优先遍历
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
