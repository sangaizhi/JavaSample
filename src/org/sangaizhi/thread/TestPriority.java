/**
 * 文件名称: TestPriority
 * 系统名称: JavaSample
 * 模块名称:
 * 软件版权:
 * 功能说明:
 * 系统版本: 1.0.0.0
 * 开发人员: sangaizhi
 * 开发时间: 2017/5/13 17:15
 * 审核人员:
 * 相关文档:
 * 修改记录:
 * 修改日期:
 * 修改人员：
 * 修改说明：
 */
package org.sangaizhi.thread;

/**
 * 线程优先级
 * @name TestPriority
 * @author sangaizhi
 * @date 2017/5/13  17:15
 * @version 1.0
 */
public class TestPriority {

    public static class HighPriority extends Thread{
        static int i =0;
        public void run(){
                while (true){
                    synchronized (TestPriority.class){
                    i++;
                    if( i > 100000){
                        System.out.println("high is complete");
                        break;
                    }
                }

            }

        }
    }
    public static class LowPriority extends Thread{
        static int i =0;
        public void run(){
            while (true){
                synchronized (TestPriority.class){
                    i++;
                if( i > 100000){
                    System.out.println("low is complete");
                    break;
                }

            }
            }
        }
    }

    public static void main(String[] args) {
        Thread high = new HighPriority();
        Thread low = new LowPriority();
        high.setPriority(Thread.MAX_PRIORITY);
        low.setPriority(Thread.MIN_PRIORITY);
        low.start();
        high.start();
    }
}
