package org.sangaizhi.datastructure.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.sangaizhi.datastructure.queue.LinkQueue;
import org.sangaizhi.datastructure.stack.StackX;
import org.sangaizhi.datastructure.vertext.Vertex;


/**
 * 存储边
 *
 * @author saz
 *
 */
class Edge<E> {
	// 边的起始顶点
	private Vertex<E> startVertex;
	// 边的结束顶点
	private Vertex<E> endVertex;

	public Vertex<E> getStartVertex() {
		return startVertex;
	}

	public void setStartVertex(Vertex<E> startVertex) {
		this.startVertex = startVertex;
	}

	public Vertex<E> getEndVertex() {
		return endVertex;
	}

	public void setEndVertex(Vertex<E> endVertex) {
		this.endVertex = endVertex;
	}

	public static List<Edge> getEdgeByVertex(List<Edge> edges, Vertex v) {
		List<Edge> resultEdge = new ArrayList<Edge>();
		for (Edge edge : edges) {
			if (edge.getStartVertex() == v || edge.getEndVertex() == v) {
				resultEdge.add(edge);
			}
		}
		return resultEdge;
	}

}

public class GenericGraph<E> {

	// 顶点数量
	private int vertexNum;

	// 顶点集合
	private List<Vertex> vertexList;

	// 边集合
	private List<Edge> edges;

	private StackX<Vertex> stack;

	private LinkQueue<Vertex> linkQueue;

	public GenericGraph() {
		// TODO Auto-generated constructor stub
		vertexList = new ArrayList<>();
		edges = new ArrayList<>();
		stack = new StackX<>();
		linkQueue = new LinkQueue<Vertex>();
	}

	public Vertex add(Vertex v) {
		vertexList.add(vertexNum++, v);
		return v;
	}

	public Vertex remove(Vertex v) {
		int index = Collections.binarySearch(vertexList, v, null);
		if (index == -1) {
			return null;
		}
		//移除该节点关联的边
		List<Edge> vertexEdges = Edge.getEdgeByVertex(edges, v);
		edges.removeAll(vertexEdges);
		return vertexList.get(index);
	}

	public Edge addEdge(Vertex start, Vertex end){
		Edge edge = new Edge();
		edge.setStartVertex(start);
		edge.setEndVertex(end);
		edges.add(edge);
		return edge;
	}

	public void displayVertex(Vertex v){
		System.out.println(v.getValue());
	}

	@SuppressWarnings("rawtypes")
	public Vertex getUnVisitedVertexWithVertex(Vertex v){
		Set<Vertex> adjacentVertexSet = new HashSet<>();
		//找出所有相邻节点
		for(Edge edge : edges){
			Vertex startV = edge.getStartVertex();
			Vertex endV = edge.getEndVertex();
			if(Objects.equals(startV, v) && endV.isWasVisited() == false){
				adjacentVertexSet.add(endV);
				return endV;
			}
			if(Objects.equals(endV, v) && startV.isWasVisited() == false){
				adjacentVertexSet.add(startV);
				return startV;
			}
		}
		return null;
	}

	/**
	 * 深度优先遍历
	 */
	@SuppressWarnings("rawtypes")
	public void deepFirstSearch(){
		vertexList.get(0).setWasVisited(true);
		displayVertex(vertexList.get(0));
		stack.push(vertexList.get(0));
		while(!stack.isEmpty()){
			// 查询相邻未被访问的顶点
			Vertex adjacentV = getUnVisitedVertexWithVertex(stack.peek());
			if(adjacentV == null){
				stack.pop();
			}else{
				adjacentV.setWasVisited(true);
				displayVertex(adjacentV);
				stack.push(adjacentV);
			}
		}

		for (int j = 0; j < vertexNum; j++) {
			vertexList.get(j).setWasVisited(false);
		}
	}

	/**
	 * 广度优先遍历
	 */
	@SuppressWarnings("rawtypes")
	public void broadFirstSearch(){
		vertexList.get(0).setWasVisited(true);
		displayVertex(vertexList.get(0));
		linkQueue.insert(vertexList.get(0));
		Vertex v2;
		while(!linkQueue.isEmpty()){
			Vertex tempVertex = linkQueue.remove();
			while(null != (v2 = getUnVisitedVertexWithVertex(tempVertex))){
				v2.setWasVisited(true);
				displayVertex(v2);
				linkQueue.insert(v2);
			}
		}
		for (int j = 0; j < vertexNum; j++) {
			vertexList.get(j).setWasVisited(false);
		}
	}


}
