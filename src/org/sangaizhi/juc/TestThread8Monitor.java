package org.sangaizhi.juc;



/**
 * 判断打印结果
 * @author sangaizhi
 */
public class TestThread8Monitor {
	public static void main(String[] args) {
		Number num = new Number();
		Number num2 = new Number();
		new Thread(()->{
			num.getOne();
		}).start();
		new Thread(()->{ 
			num2.getTwo();
		}).start();
//		new Thread(()->{
//			num.getThree();
//		}).start();
	}

}
class Number{
	public static synchronized void getOne(){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.err.println("one");
	}
	public static synchronized void getTwo(){
		System.err.println("two");
	}
	public  void getThree(){
		System.err.println("three");
	}
}