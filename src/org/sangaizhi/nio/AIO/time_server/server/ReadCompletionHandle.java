package org.sangaizhi.nio.AIO.time_server.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.time.Instant;

/**
 * 读取消息和应答处理类
 * Created by sangaizhi on 2017/5/15.
 */
public class ReadCompletionHandle implements CompletionHandler<Integer, ByteBuffer>{
    private AsynchronousSocketChannel asynchronousSocketChannel;

   public ReadCompletionHandle(AsynchronousSocketChannel asynchronousSocketChannel){
       if(this.asynchronousSocketChannel == null){
           this.asynchronousSocketChannel = asynchronousSocketChannel;
       }
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        // 切换缓冲区的模式
        attachment.flip();
        // 创建字节数组，并将缓冲区中的数据读到数组中
        byte[] body = new byte[attachment.remaining()];
        attachment.get(body);
        try{
            String req = new String(body, "UTF-8");
            System.out.println("the time server receive order: " + req);
            // 判断请求数据实现应答
            String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(req) ? Instant.now().toString() : "BAD ORDER";
            doWriter(currentTime);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 回写应答数组给客户端
     * @param currentTime
     */
    private void doWriter(String currentTime) {
       if(currentTime != null && currentTime.trim().length() > 0){
           byte[] bytes = currentTime.getBytes();
           ByteBuffer writerBuffer = ByteBuffer.allocate(bytes.length);
           writerBuffer.flip();
           asynchronousSocketChannel.write(writerBuffer, writerBuffer, new CompletionHandler<Integer, ByteBuffer>() {
               @Override
               public void completed(Integer result, ByteBuffer attachment) {
                   // 判断缓冲区中的数据是否发送完，没发送完继续发送
                   if(writerBuffer.hasRemaining()){
                       asynchronousSocketChannel.write(writerBuffer);
                   }
               }

               @Override
               public void failed(Throwable exc, ByteBuffer attachment) {
                   try {
                       asynchronousSocketChannel.close();
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
            asynchronousSocketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
