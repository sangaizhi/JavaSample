/**
 * 文件名称: TestABCAlternate
 * 系统名称: juc
 * 模块名称:
 * 软件版权:
 * 功能说明:
 * 系统版本: 1.0.0.0
 * 开发人员: sangaizhi
 * 开发时间: 2017/5/4 21:04
 * 审核人员:
 * 相关文档:
 * 修改记录:
 * 修改日期:
 * 修改人员：
 * 修改说明：
 */
package org.sangaizhi.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @name TestABCAlternate
 * @author sangaizhi
 * @date 2017/5/4  21:04
 * @version 1.0
 */
public class TestABCAlternate {
	
	public static void main(String[] args) {
		AlterDemo alterDemo = new AlterDemo();
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				for(int i=1;i<=20;i++){
					alterDemo.loopA(i);
				}
			}
		},"A").start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				for(int i=1;i<=20;i++){
					alterDemo.loopB(i);
				}
			}
		},"B").start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				for(int i=1;i<=20;i++){
					alterDemo.loopC(i);
				}
			}
		},"C").start();
		
	}
}
class AlterDemo{

    private int number = 1; //当前正在执行线程的标记
    
    private Lock lock = new ReentrantLock();
    
    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();
    private Condition conditionC = lock.newCondition();
    
    public void loopA(int totalLoop){
    	lock.lock();
    	try{
    		// 判断
    		if(number != 1){
    			conditionA.await();
    		}
    		for(int i=0;i<1;i++){
    			System.out.println(Thread.currentThread().getName()+"\t"+i+"\t"+totalLoop);
    		}
    		number = 2;
    		conditionB.signal();
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		lock.unlock();
    	}
    }
    public void loopB(int totalLoop){
    	lock.lock();
    	try{
    		// 判断
    		if(number != 2){
    			conditionB.await();
    		}
    		for(int i=0;i<1;i++){
    			System.out.println(Thread.currentThread().getName()+"\t"+i+"\t"+totalLoop);
    		}
    		number = 3;
    		conditionC.signal();
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		lock.unlock();
    	}
    }
    public void loopC(int totalLoop){
    	lock.lock();
    	try{
    		// 判断
    		if(number != 3){
    			conditionC.await();
    		}
    		for(int i=0;i<1;i++){
    			System.out.println(Thread.currentThread().getName()+"\t"+i+"\t"+totalLoop);
    		}
    		number = 1;
    		conditionA.signal();
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		lock.unlock();
    	}
    }

}