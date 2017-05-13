/**
 * 文件名称: TestAtomicDemo
 * 系统名称: juc
 * 模块名称:
 * 软件版权:
 * 功能说明:
 * 系统版本: 1.0.0.0
 * 开发人员: sangaizhi
 * 开发时间: 2017/4/27 0:17
 * 审核人员:
 * 相关文档:
 * 修改记录:
 * 修改日期:
 * 修改人员：
 * 修改说明：
 */
package org.sangaizhi.juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @name i++ 的原子性问题；i++ 操作实际上分为三个步骤：读-改-写
 * @author sangaizhi
 * @date 2017/4/27  0:17
 * @version 1.0
 *
 * 原子变量:
 */
public class TestAtomicDemo {
    public static void main(String[] args) {
        AtomicDemo atomicDemo = new AtomicDemo();
        for(int i=0;i<10;i++){
            new Thread(atomicDemo).start();
        }
    }
}

class AtomicDemo implements Runnable{
    private AtomicInteger serialNumber = new AtomicInteger();
    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ":" + getSerialNumber());
    }

    public int getSerialNumber() {
        return serialNumber.getAndIncrement();
    }
}