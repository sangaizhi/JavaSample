package datastructure.queue;

public class QueueTest {
	public static void main(String[] args) {
		testCircleQueue();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void testCircleQueue() {
		CircleQueue queue = new CircleQueue();
		System.out.println(queue.isEmpty());
		queue.insert(1);
		queue.insert(2);
		queue.insert(3);
		System.out.println(queue.isEmpty());
		System.out.println(queue);
		queue.reomve();
		System.out.println(queue);
		queue.insert(1);
		System.out.println(queue);
		System.out.println(queue.length());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void testLinkQueue() {
		LinkQueue linkQueue = new LinkQueue();
		for (int i = 0; i < 5; i++) {
			linkQueue.insert(i);
		}
		linkQueue.displayQueue();
		System.out.println(linkQueue.remove());
		System.out.println(linkQueue.remove());
		System.out.println(linkQueue.remove());
		System.out.println(linkQueue.remove());
		linkQueue.displayQueue();
	}

}
