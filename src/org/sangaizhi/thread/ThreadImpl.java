package org.sangaizhi.thread;

public class ThreadImpl {
	public static void main(String[] args) {
		Thread thread = new Thread(new RunnableThread());
		thread.start();
	}
}
