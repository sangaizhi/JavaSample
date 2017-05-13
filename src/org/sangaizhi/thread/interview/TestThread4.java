package org.sangaizhi.thread.interview;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 现有程序同时启动了4个线程去调用TestDo.doSome(key, value)方法，由于TestDo.doSome(key, value)方法内的代码是先暂停1秒，然后再输出以秒为单位的当前时间值，所以，会打印出4个相同的时间值，如下所示：
		4:4:1258199615
		1:1:1258199615
		3:3:1258199615
		1:2:1258199615
        请修改代码，如果有几个线程调用TestDo.doSome(key, value)方法时，传递进去的key相等（equals比较为true），则这几个线程应互斥排队输出结果，即当有两个线程的key都是"1"时，它们中的一个要比另外其他线程晚1秒输出结果，如下所示：
		4:4:1258199615
		1:1:1258199615
		3:3:1258199615
		1:2:1258199616
	  总之，当每个线程中指定的key相等时，这些相等key的线程应每隔一秒依次输出时间值（要用互斥），如果key不同，则并行执行（相互之间不互斥）。
 * @author sangaizhi
 *
 */
public class TestThread4 extends Thread {
	private TestDo4 testDo;
	private String key;
	private String value;
	
	public TestThread4(String key,String key2,String value){
		this.testDo = TestDo4.getInstance();
		/*常量"1"和"1"是同一个对象，下面这行代码就是要用"1"+""的方式产生新的对象，
		以实现内容没有改变，仍然相等（都还为"1"），但对象却不再是同一个的效果*/
		this.key = key+key2; 
		this.value = value;
	}


	public static void main(String[] args) throws InterruptedException{
			TestThread4 a = new TestThread4("1","","1");
			TestThread4 b = new TestThread4("1","","2");
			TestThread4 c = new TestThread4("3","","3");
			TestThread4 d = new TestThread4("4","","4");
			System.out.println("begin:"+(System.currentTimeMillis()/1000));
				a.start();
				b.start();
				c.start();
				d.start();
		

	}
	
	public void run(){
		testDo.doSome(key, value);
	}
}

class TestDo4 {

	private TestDo4() {}
	private static TestDo4 _instance = new TestDo4();	
	public static TestDo4 getInstance() {
		if(null == _instance){
			_instance = new TestDo4();
		}
		return _instance;
	}

	private CopyOnWriteArrayList keys = new CopyOnWriteArrayList();
	public void doSome(Object key, String value) {
		Object o = key;
		if(!keys.contains(o)){
			keys.add(o);
		}else{
			for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Object object = (Object) iterator.next();
				if(object.equals(o)){
					o = object;
				}
			}
		}
		synchronized (o) {
			// 以大括号内的是需要局部同步的代码，不能改动!
			{
				try {
					Thread.sleep(1000);
					System.out.println(key+":"+value + ":" + (System.currentTimeMillis() / 1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

}