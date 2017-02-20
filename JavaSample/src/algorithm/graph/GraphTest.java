package algorithm.graph;

public class GraphTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Graph graph = new Graph();
		char[] vertexs = new char[]{'A','B','C','D','E','F','G','H','I'};
		for(char vertex : vertexs){
			//≤Â»Î∂•µ„
			graph.addVertex(vertex);
		}
		graph.addEdge(0, 1);
		graph.addEdge(0, 2);
		graph.addEdge(0, 3);
		graph.addEdge(0, 4);
		graph.addEdge(1, 5);
		graph.addEdge(5, 7);
		graph.addEdge(3, 6);
		graph.addEdge(6, 8);
		graph.broadFirstSearch();
	}
}
