package datastructure.queue;

/**
 * ѭ������
 * @author saz
 *
 * @param <E>
 */
public class CircleQueue<E> {
	private int DEFAULT_SIZE = 10;
	private int capacity;
	private E[] elements;
	private int front;
	private int rear;

	@SuppressWarnings("unchecked")
	public CircleQueue() {
		capacity = DEFAULT_SIZE;
		elements = (E[]) new Object[capacity];
	}

	public CircleQueue(int size) {
		capacity = size;
	}

	public boolean isEmpty() {
		return front == rear;
	}

	public boolean isFull() {
		return rear == front && elements[front] != null;
	}

	public int length() {
		if (isEmpty()) {
			return 0;
		} else {
			return rear > front ? rear - front : capacity - (front - rear);
		}
	}

	public void insert(E element) {
		if (isFull()) {
			throw new IndexOutOfBoundsException("����������");
		} else {
			elements[rear] = element;
			rear = (rear + 1) % capacity;
		}
	}

	public E reomve() {
		if (isEmpty()) {
			return null;
		} else {
			E element = elements[front];
			front =  (front + 1) % capacity;
			return element;
		}
	}

	 public String toString(){  
	        if(isEmpty()){  
	            return "[]";  
	        }else{  
	            //���front<rear����ô��ЧԪ�ؾ���front��rear֮���Ԫ��  
	            if(front<rear){  
	                StringBuilder sb=new StringBuilder("[");  
	                for(int i=front;i<rear;i++){  
	                    sb.append(elements[i].toString()+",");  
	                }  
	                int len=sb.length();  
	                return sb.delete(len-1, len).append("]").toString();  
	            }else{ //���front>=rear,��ô��ЧԪ��Ϊfront->capacity֮���0->front֮���Ԫ��  
	                StringBuilder sb=new StringBuilder("[");  
	                for(int i=front;i<capacity;i++){  
	                    sb.append(elements[i].toString()+",");  
	                }  
	                for(int i=0;i<rear;i++){  
	                    sb.append(elements[i].toString()+",");  
	                }  
	                int len=sb.length();  
	                return sb.delete(len-1, len).append("]").toString();  
	            }  
	        }  
	    }  
}
