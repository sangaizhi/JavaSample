package org.sangaizhi.nio.AIO.time_server.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * 客户端消息处理
 * @author sangaizhi
 * @date 2017/5/16
 */
public class AsyncTimeClientHandler implements Runnable,CompletionHandler<Void, AsyncTimeClientHandler> {


    // 与服务端连接的异步通道
    private AsynchronousSocketChannel clientAsynchronousSocketChannel;

    // 服务端的主机IP或者映射了主机IP的主机名
    private String host;
    // 服务端的端口
    private int port;
    // 线程计数器，用来阻塞当前线程，使之完成当前操作
    private CountDownLatch countDownLatch;

    AsyncTimeClientHandler(String host, int port){
        if(null == host || host.trim().length() <= 0){
            host = "127.0.0.1";
        }
        this.host = host;
        this.port = port;
        try {
            clientAsynchronousSocketChannel = AsynchronousSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // 让当前线程进行等待，防止异步操作没有执行完就退出
        countDownLatch = new CountDownLatch(1);
        clientAsynchronousSocketChannel.connect(new InetSocketAddress(host, port), this, this);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        try {
            clientAsynchronousSocketChannel.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    @Override
    public void completed(Void result, AsyncTimeClientHandler attachment) {
        byte[] req = "QUERY TIME ORDER".getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
        writeBuffer.put(req);
        writeBuffer.flip();
        clientAsynchronousSocketChannel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                if(attachment.hasRemaining()){
                    clientAsynchronousSocketChannel.write(attachment, attachment, this);
                }else{
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    clientAsynchronousSocketChannel.read(readBuffer, readBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                        @Override
                        public void completed(Integer result, ByteBuffer attachment) {
                            attachment.flip();
                            byte[] bytes = new byte[attachment.remaining()];
                            attachment.get(bytes);
                            String body ;
                            try {
                                body = new String(bytes, "UTF-8");
                                System.out.println("Now is :" + body);
                                countDownLatch.countDown();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {
                            try {
                                clientAsynchronousSocketChannel.close();
                                countDownLatch.countDown();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                try {
                    clientAsynchronousSocketChannel.close();
                    countDownLatch.countDown();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void failed(Throwable exc, AsyncTimeClientHandler attachment) {
        exc.printStackTrace();
        try {
            clientAsynchronousSocketChannel.close();
            countDownLatch.countDown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
