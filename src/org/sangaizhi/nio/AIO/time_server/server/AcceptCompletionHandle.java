package org.sangaizhi.nio.AIO.time_server.server;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * Accept 接收通知消息处理类
 * Created by sangaizhi on 2017/5/15.
 */
public class AcceptCompletionHandle implements CompletionHandler<AsynchronousSocketChannel, AsyncTimeServerHandle> {

    /**
     * 当操作完成时执行
     * @param result
     * @param attachment
     */
    @Override
    public void completed(AsynchronousSocketChannel result, AsyncTimeServerHandle attachment) {
        // 异步接收新的客户端连接
        attachment.asynchronousServerSocketChannel.accept(attachment, this);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        result.read(buffer, buffer, new ReadCompletionHandle(result));
    }

    @Override
    public void failed(Throwable exc, AsyncTimeServerHandle attachment) {
        attachment.latch.countDown();
    }
}
