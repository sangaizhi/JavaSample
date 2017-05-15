/**
 * 文件名称: TimeClientHandle
 * 系统名称: JavaSample
 * 模块名称:
 * 软件版权:
 * 功能说明:
 * 系统版本: 1.0.0.0
 * 开发人员: sangaizhi
 * 开发时间: 2017/5/14 23:23
 * 审核人员:
 * 相关文档:
 * 修改记录:
 * 修改日期:
 * 修改人员：
 * 修改说明：
 */
package org.sangaizhi.nio.nio_time;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 客戶端处理
 * @name TimeClientHandle
 * @author sangaizhi
 * @date 2017/5/14  23:23
 * @version 1.0
 */
public class TimeClientHandle implements Runnable {

    private String host;
    private int port;
    private SocketChannel socketChannel;
    private Selector selector;
    private volatile boolean stop;

    public TimeClientHandle(String host, int port){
        this.host = null == host ? "127.0.0.1" : host;
        this.port = port;
        try{
            this.selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 连接服务端
     */
    public void doConnect() throws IOException {

        //直接连接服务端，如果连接成功，则将通道注册到堵路复用器上，发送请求，读取应答信息；
        // 否则向多路复用器注册 channel 的OP_CONNECT 事件

        if(socketChannel.connect(new InetSocketAddress(this.host, this.port))){
            socketChannel.register(selector, SelectionKey.OP_READ);
        }else{
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }
    }


    @Override
    public void run() {
        try {
            doConnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (!stop){
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeySet.iterator();
                SelectionKey key = null;
                while (iterator.hasNext()){
                    key = iterator.next();
                    iterator.remove();
                    try{
                        handInput(key);
                    }catch (IOException e){
                        if(null != key){
                            key.cancel();
                            if(key.channel() != null){
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(null != selector){
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理回显的消息
     * @param selectionKey
     * @throws IOException
     */
    public void handInput(SelectionKey selectionKey) throws IOException {
        if(selectionKey.isValid()){
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            if(selectionKey.isConnectable()){
                if(socketChannel.finishConnect()){
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    doWrite(socketChannel);
                }else {
                    System.exit(1);
                }
            }
            if(selectionKey.isReadable()){
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                int len = socketChannel.read(byteBuffer);
                if (len > 0){
                    byteBuffer.flip();
                    byte[] bytes = new byte[byteBuffer.remaining()];
                    byteBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("Now is body:" + body);
                    this.stop = true;
                }else if(len < 0){
                    selectionKey.cancel();
                    socketChannel.close();
                }
            }
        }
    }

    /**
     * 回写消息
     * @param socketChannel
     */
    private void doWrite(SocketChannel socketChannel) throws IOException {
        byte[] bytes = "QUERY TIME ORDER".getBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
        byteBuffer.put(bytes);
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
        if(byteBuffer.hasRemaining()){
            System.out.println("send order 2 server succeed");
        }
    }
}
