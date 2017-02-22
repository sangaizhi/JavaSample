package datastructure.stack;

/**
 * ʵ��������ȱ�����ջ��
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
	 *������ϵĽڵ�
	 * @return
	 */
	public E peek() {
		return st[top];
	}

	public boolean isEmpty() {
		return top == -1;
	}
}
