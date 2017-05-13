package org.sangaizhi.datastructure.graph;

import java.util.ArrayList;
import java.util.List;

import org.sangaizhi.datastructure.vertext.Vertex;

public class GenericGraphTest {


	public static void main(String[] args) {
		GenericGraph genericGraph = new GenericGraph<>();
		List<Edge> edges = new ArrayList<>();


		Vertex vertexA = new Vertex("A", false);
		genericGraph.add(vertexA);
		Vertex vertexB = new Vertex("B", false);
		genericGraph.add(vertexB);
		createEdge(genericGraph, vertexA, vertexB);

		Vertex vertexC = new Vertex("C", false);
		genericGraph.add(vertexC);
		createEdge(genericGraph, vertexA, vertexC);


		Vertex vertexD = new Vertex("D", false);
		genericGraph.add(vertexD);
		createEdge(genericGraph, vertexA, vertexD);

		Vertex vertexE = new Vertex("E", false);
		genericGraph.add(vertexE);
		createEdge(genericGraph, vertexA, vertexE);


		Vertex vertexF = new Vertex("F", false);
		genericGraph.add(vertexF);
		createEdge(genericGraph, vertexB, vertexF);

		Vertex vertexG = new Vertex("G", false);
		genericGraph.add(vertexG);
		createEdge(genericGraph, vertexD, vertexG);

		Vertex vertexH = new Vertex("H", false);
		genericGraph.add(vertexH);
		createEdge(genericGraph, vertexF, vertexH);

		Vertex vertexI = new Vertex("I", false);
		genericGraph.add(vertexI);
		createEdge(genericGraph, vertexG, vertexI);


		genericGraph.broadFirstSearch();
	}


	private static void createEdge(GenericGraph graph,Vertex vertexStart,Vertex vertexEnd) {
		graph.addEdge(vertexStart, vertexEnd);
	}
}
