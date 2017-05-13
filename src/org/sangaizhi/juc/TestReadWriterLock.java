package org.sangaizhi.juc;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁: 读写、读写 互斥
 * @author sangaizhi
 *
 */
public class TestReadWriterLock {
	public static void main(String[] args) {
		ReadWriterLockDemo demo = new ReadWriterLockDemo();
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				demo.set(new Random().nextInt());
			}
		},"write").start();
		
		for(int i=0;i<100;i++){
			new Thread(new Runnable() {
				public void run() {
					demo.get();
				}
			}).start();
		}
		
	}
	
	
}

class ReadWriterLockDemo{
	private int number = 0;
	
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	
	public void get() {
		lock.readLock().lock();
		try{
			// TODO Auto-generated method stub
			System.out.println(Thread.currentThread().getName() + ":"+number);
		}catch(Exception e){}finally{
			lock.readLock().unlock();
		}
		
	}
	
	public void set(int number) {
		lock.writeLock().lock();
		try{
			// TODO Auto-generated method stub
			System.out.println(Thread.currentThread().getName() + ":"+number);
			this.number = number;
		}catch(Exception e){}finally{
			lock.writeLock().unlock();
		}
	
	}
}