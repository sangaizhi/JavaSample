/**
 * 文件名称: TestProductAndConsume
 * 系统名称: juc
 * 模块名称:
 * 软件版权:
 * 功能说明:
 * 系统版本: 1.0.0.0
 * 开发人员: sangaizhi
 * 开发时间: 2017/5/4 20:15
 * 审核人员:
 * 相关文档:
 * 修改记录:
 * 修改日期:
 * 修改人员：
 * 修改说明：
 */
package org.sangaizhi.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @name TestProductAndConsume
 * @author sangaizhi
 * @date 2017/5/4  20:15
 * @version 1.0
 */
public class TestProductAndConsume {

    public static void main(String[] args) {
        Clerk clerk = new Clerk();
        Productor productor = new Productor(clerk);
        Consumer consumer = new Consumer(clerk);
        new Thread(productor, "生产者A").start();
        new Thread(consumer, "消费者A").start();
        new Thread(productor, "生产者B").start();
        new Thread(consumer, "消费者B").start();
    }

}

/**
 * 店员
 */
class Clerk{
    private int product = 0;

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();
    public  void get(){
        lock.lock();
        try {
            while (product >= 1) {
                System.out.println("产品已满");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName()+":"+ ++product);
            condition.signalAll();
        }finally {
            lock.unlock();
        }

    }

    public  void sale(){
        lock.lock();
        try {
            while(product <= 0){
                System.out.println("缺货!");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName()+":"+ --product);
            condition.signalAll();
        }finally {
            lock.unlock();
        }


    }
}

/**
 * 生产者
 */
class Productor implements Runnable{
    private Clerk clerk;

    public Productor(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for(int i = 0; i<20; i++){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.get();
        }
    }
}

class Consumer implements Runnable{
    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for(int i = 0; i<20; i++){
            clerk.sale();
        }
    }
}