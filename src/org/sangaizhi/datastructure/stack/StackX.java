package org.sangaizhi.datastructure.stack;

/**
 * 实现深度优先遍历的栈类
 *
 * @author saz
 *
 */
public class StackX<E> {
	private final int SIZE = 20;
	private E[] st;
	private int top;

	@SuppressWarnings("unchecked")
	public StackX() {
		st = (E[]) new Object[SIZE];
		top = -1;
	}

	public void push(E j) {
		st[++top] = j;
	}

	public E pop() {
		return st[top--];
	}

	/**
	 *返回最顶上的节点
	 * @return
	 */
	public E peek() {
		return st[top];
	}

	public boolean isEmpty() {
		return top == -1;
	}
}
