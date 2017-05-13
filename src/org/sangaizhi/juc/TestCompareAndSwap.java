/**
 * 文件名称: TestCompareAndSwap
 * 系统名称: juc
 * 模块名称:
 * 软件版权:
 * 功能说明:
 * 系统版本: 1.0.0.0
 * 开发人员: sangaizhi
 * 开发时间: 2017/4/27 0:49
 * 审核人员:
 * 相关文档:
 * 修改记录:
 * 修改日期:
 * 修改人员：
 * 修改说明：
 */
package org.sangaizhi.juc;

/**
 * @name TestCompareAndSwap
 * @author sangaizhi
 * @date 2017/4/27  0:49
 * @version 1.0
 */
public class TestCompareAndSwap {
    public static void main(String[] args) {
        final CompareAndSwap compareAndSwap = new CompareAndSwap();
        for(int i=0;i<10;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int expectedValue = compareAndSwap.get();
                    boolean b = compareAndSwap.compareAndSet(expectedValue, (int) Math.random());
                    System.out.println(b);
                }
            }).start();
        }
    }
}
class CompareAndSwap{
    private int value;

    public synchronized int get(){
        return value;
    }
    public synchronized int compareAndSwap(int expectedValue, int newValue){
        int oldValue = value;
        if(oldValue == expectedValue){
            this.value = newValue;
        }
        return oldValue;
    }

    public synchronized boolean compareAndSet(int expectedValue, int newValue){
        return expectedValue == this.compareAndSwap(expectedValue, newValue);
    }
}

