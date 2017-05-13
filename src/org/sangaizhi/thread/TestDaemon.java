/**
 * 文件名称: TestDaemon
 * 系统名称: JavaSample
 * 模块名称:
 * 软件版权:
 * 功能说明:
 * 系统版本: 1.0.0.0
 * 开发人员: sangaizhi
 * 开发时间: 2017/5/13 17:11
 * 审核人员:
 * 相关文档:
 * 修改记录:
 * 修改日期:
 * 修改人员：
 * 修改说明：
 */
package org.sangaizhi.thread;

/**
 * 守护线程
 * @name TestDaemon
 * @author sangaizhi
 * @date 2017/5/13  17:11
 * @version 1.0
 */
public class TestDaemon {
    public static class DaemonThread extends Thread{
        public void run(){
            while (true){
                System.out.println("I am alive");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread t = new DaemonThread();
        t.setDaemon(true);
        t.start();
    }
}
