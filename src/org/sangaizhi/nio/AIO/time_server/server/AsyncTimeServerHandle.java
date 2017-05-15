package org.sangaizhi.nio.AIO.time_server.server;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * 异步时间服务器处理类
 * Created by saz on 2017/5/15.
 */
public class AsyncTimeServerHandle implements Runnable{

    private int port;
    // 线程计数器，用于阻塞当前线程
    CountDownLatch latch;
    // 异步服务socket 通道
    AsynchronousServerSocketChannel asynchronousServerSocketChannel;

    AsyncTimeServerHandle(int port){
        this.port = port;
        try{
            // 创建一个异步的服务端通道
            asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
            // 给通道绑定监听的端口
            asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("the time server is start in port:" + port);
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }


    @Override
    public void run() {
        // latch 的作用是在完成一组正在执行的操作之前，允许当前的线程一直阻塞
        latch = new CountDownLatch(1);
        doAccept();
        try {
            latch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    /**
     * 处理接收的消息
     */
    private void doAccept() {
        // CompletionHandler<AsychronousSocketChannel, ? super A>类型的handler实例接收accept操作成功的通知消息
        asynchronousServerSocketChannel.accept(this, new AcceptCompletionHandle());
    }
}
