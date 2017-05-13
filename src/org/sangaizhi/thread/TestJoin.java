/**
 * 文件名称: TestJoin
 * 系统名称: JavaSample
 * 模块名称:
 * 软件版权:
 * 功能说明:
 * 系统版本: 1.0.0.0
 * 开发人员: sangaizhi
 * 开发时间: 2017/5/13 14:00
 * 审核人员:
 * 相关文档:
 * 修改记录:
 * 修改日期:
 * 修改人员：
 * 修改说明：
 */
package org.sangaizhi.thread;

/**
 * @name TestJoin
 * @author sangaizhi
 * @date 2017/5/13  14:00
 * @version 1.0
 */
public class TestJoin {

    private volatile  static  int i=0;
    public static class AddThread extends  Thread{
        int sum = 0;
        public void run(){
            for(int i=0;i<1000000000;i++){
                sum += i;
                if(i == 1000000000-1){
                    System.out.println(i);
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AddThread add = new AddThread();
        add.start();
        add.join();
        System.out.println("00000");
    }
}

