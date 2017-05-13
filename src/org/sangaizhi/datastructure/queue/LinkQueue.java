package org.sangaizhi.datastructure.queue;

/**
 * 链式队列
 *
 * @author saz
 *
 * @param <E>
 */
public class LinkQueue<E> {
	/**
	 * 节点类
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

	// 队尾
	private Node rear;

	// 队首
	private Node front;

	// 队列长度
	private int size = 0;

	/**
	 * 初始化数据
	 */
	public LinkQueue() {
		Node node = new Node();
		front = rear = node;
	}

	/**
	 * 将一个对象追加到队列尾部
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
	 * 队列对头出对
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
	 * 队列大小
	 *
	 * @return
	 */
	public int size() {
		return size;
	}

	/**
	 * 队列是否为空
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
