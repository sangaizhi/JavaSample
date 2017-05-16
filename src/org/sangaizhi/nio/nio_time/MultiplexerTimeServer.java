/**
 * 文件名称: MultiplexerTimeServer
 * 系统名称: JavaSample
 * 模块名称:
 * 软件版权:
 * 功能说明:
 * 系统版本: 1.0.0.0
 * 开发人员: sangaizhi
 * 开发时间: 2017/5/14 22:30
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
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.Instant;
import java.util.Iterator;
import java.util.Set;

/**
 * @name MultiplexerTimeServer
 * @author sangaizhi
 * @date 2017/5/14  22:30
 * @version 1.0
 */
public class MultiplexerTimeServer implements Runnable {

    /**
     * 多路复用器
     */
    private Selector selector;

    /**
     * 服务端socket channel
     */
    private ServerSocketChannel serverSocketChannel;

    private volatile boolean stop;

    /**
     * 资源初始化，创建多路复用器，绑定监听端口，将ServerSocketChannel 注册到 Selectot
     * 初始化多路复用器，绑定监听端口
     * @param port
     */
    public MultiplexerTimeServer(int port){
        try{
            this.serverSocketChannel = ServerSocketChannel.open();
            this.selector = Selector.open();
            serverSocketChannel.configureBlocking(false);
            // 绑定监听端口，连接数限制为1024
            serverSocketChannel.bind(new InetSocketAddress(port), 1024);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("The time server is start in port:"+port);
        } catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void stop(){
        this.stop = true;
    }

    @Override
    public void run() {
        while (!stop){
            try{
                // 每隔1s 循环监听是否有就绪的 channel
                selector.select(1000);
                // 获取就绪 channel 的 句柄（selectKey）
                Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeySet.iterator();
                SelectionKey selectionKey = null;
                // 遍历就绪channel 的 句柄
                while (iterator.hasNext()){
                    selectionKey = iterator.next();
                    iterator.remove();
                    try{
                        // TODO 处理就绪的channel
                        handleInput(selectionKey);
                    }catch (Exception e){
                        if(null != selectionKey){
                            selectionKey.cancel();
                            if(selectionKey.channel() != null){
                                selectionKey.channel().close();
                            }
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if (null != selector){
            try{
                selector.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理就绪channel
     * @param selectionKey
     */
    private void handleInput(SelectionKey selectionKey) throws IOException {
        if(selectionKey.isValid()){

            if(selectionKey.isAcceptable()){  // 通道是否准备接受一个 连接
                // 接受一个新的连接
                System.out.println("接受一个新的连接");
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();

                // 得到新连接的channel
                SocketChannel socketChannel = serverSocketChannel.accept();
                // 设置新连接为非阻塞
                socketChannel.configureBlocking(false);

                // 把新连接注册到多路复用器
                socketChannel.register(selector, SelectionKey.OP_READ);
            }
            if(selectionKey.isReadable()){ // 通道是否读取导数据
                // 读取数据
                SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = socketChannel.read(readBuffer);
                if(readBytes > 0){
                    // 切换buffer 为读模式
                    readBuffer.flip();
                    // 把读取到数据存放到bytes 数组中
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);

                    String body = new String(bytes, "UTF-8");
                    System.out.println("The time server receive order :" + body);
                    String currentTime = "QUERY TIME ORDER".equals(body) ? Instant.now().toString() : "BAD ORDER";
                    doWrite(socketChannel, currentTime);
                }else if(readBytes < 0){
                    selectionKey.cancel();
                    socketChannel.close();
                }
            }
        }
    }


    /**
     * 处理回显信息
     * @param socketChannel
     * @param responseBody
     * @throws IOException
     */
    private void doWrite(SocketChannel socketChannel, String  responseBody) throws IOException {
        if(responseBody != null && responseBody.trim().length() > 0){
            byte[] outBytes = responseBody.getBytes();
            ByteBuffer outBuffer = ByteBuffer.allocate(1024);
            outBuffer.put(outBytes);
            outBuffer.flip();
            socketChannel.write(outBuffer);
        }
    }
}
