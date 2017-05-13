/**
 * 文件名称: TestVolatile
 * 系统名称: juc
 * 模块名称:
 * 软件版权:
 * 功能说明:
 * 系统版本: 1.0.0.0
 * 开发人员: sangaizhi
 * 开发时间: 2017/4/26 23:42
 * 审核人员:
 * 相关文档:
 * 修改记录:
 * 修改日期:
 * 修改人员：
 * 修改说明：
 */
package org.sangaizhi.juc;

/**
 * @name TestVolatile
 * @author sangaizhi
 * @date 2017/4/26  23:42
 * @version 1.0
 */
public class TestVolatile {
    public static void main(String[] args) {
        ThreadDemo td = new ThreadDemo();
        new Thread(td).start();
        while (true){
            if(td.isFlag()){
                System.out.println("------------");
                break;
            }
        }
    }
}

class ThreadDemo implements Runnable{

    private volatile boolean flag = false;
    @Override
    public void run() {
        try{
            Thread.sleep(200);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        flag = true;
        System.out.println("flag:" + isFlag());
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
