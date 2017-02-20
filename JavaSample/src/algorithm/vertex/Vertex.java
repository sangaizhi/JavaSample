package algorithm.vertex;

/**
 * ∂•µ„¿‡
 * @author saz
 *
 * @param <E>
 */
public class Vertex<E> {
	private E value;
	private boolean wasVisited;

	public Vertex() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Vertex(E value, boolean wasVisited) {
		super();
		this.value = value;
		this.wasVisited = wasVisited;
	}

	public E getValue() {
		return value;
	}

	public void setValue(E value) {
		this.value = value;
	}

	public boolean isWasVisited() {
		return wasVisited;
	}

	public void setWasVisited(boolean wasVisited) {
		this.wasVisited = wasVisited;
	}
	
}
