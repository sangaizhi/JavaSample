package org.sangaizhi.juc.heima;

import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestThread2 {

	public static void main(String[] args) {
		final Semaphore semaphore = new Semaphore(1);
		final Lock lock = new ReentrantLock();
		final SynchronousQueue<String> quene = new SynchronousQueue<>();
		for(int i=0;i<10;i++){
			new Thread(new Runnable() {
				@Override
				public void run() {
//					synchronized (quene) {
//						try {
//							String input = quene.take();
//							String output = TestDo.doSome(input);
//							System.out.println(Thread.currentThread().getName()+ ":" + output);
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}finally{
//						}
//					}
					
//					try {
//						lock.lock();
//						String input = quene.take();
//						String output = TestDo.doSome(input);
//						System.out.println(Thread.currentThread().getName()+ ":" + output);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}finally{
//						lock.unlock();
//					}
					
					try {
						semaphore.acquire();
						String input = quene.take();
						String output = TestDo2.doSome(input);
						System.out.println(Thread.currentThread().getName()+ ":" + output);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						semaphore.release();
					}
					
				}
			}).start();
		}
		System.out.println("begin:"+(System.currentTimeMillis()/1000));
		for(int i=0;i<10;i++){  //这行不能改动
			String input = i+"";  //这行不能改动
			try {
				quene.put(input);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

//不能改动此TestDo类
class TestDo2 {
	public static String doSome(String input){
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String output = input + ":"+ (System.currentTimeMillis() / 1000);
		return output;
	}
}