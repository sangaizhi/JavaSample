package algorithm.queue;

/**
 * ��ʽ����
 * 
 * @author saz
 *
 * @param <E>
 */
public class LinkQueue<E> {
	/**
	 * �ڵ���
	 * 
	 * @author saz
	 *
	 */
	private class Node {
		private E data;
		private Node next;

		public Node() {
			data = null;
			next = null;
		}

		public Node(E data, Node next) {
			this.data = data;
			this.next = next;
		}
	}

	// ��β
	private Node rear;

	// ����
	private Node front;

	// ���г���
	private int size = 0;

	/**
	 * ��ʼ������
	 */
	public LinkQueue() {
		Node node = new Node();
		front = rear = node;
	}

	/**
	 * ��һ������׷�ӵ�����β��
	 * 
	 * @param value
	 */
	public void insert(E value) {
		Node node = new Node(value, null);
		rear.next = node;
		rear = node;
		size++;
	}

	/**
	 * ���ж�ͷ����
	 * 
	 * @return
	 */
	public E remove() {
		if (rear == front) {
			return null;
		} else {
			Node temp = front.next;
			front.next = temp.next;
			if (temp.next == null) {
				front = rear;
			}
			size--;
			return temp.data;
		}
	}

	/**
	 * ���д�С
	 * 
	 * @return
	 */
	public int size() {
		return size;
	}

	/**
	 * �����Ƿ�Ϊ��
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return front == rear;
	}

	public E getQueueFront() {
		return front.next.data;
	}

	public void displayQueue() {
		Node temp = front.next;
		while (null != temp) {
			System.out.println(temp.data + "\t");
			temp = temp.next;
		}
		System.out.println();
	}
}
