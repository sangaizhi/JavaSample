package org.sangaizhi.nio;

import static org.junit.Assert.*;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Scanner;

import org.junit.Test;import com.sun.jndi.url.iiopname.iiopnameURLContextFactory;

/**
 * 一、使用NIO 完成网络通信的三个核心
 * 	1、通道(channel)：负责连接
 * 		java.nio.channels.Channel 接口：
 * 			|-- SelectableChannel
 * 				|-- SocketChannel
 * 				|-- ServerScoketChannel
 * 				|-- DatagramChannel
 * 				
 * 				|-- Pipe.SinkChannel
 * 				|--Pipe.SourceChannel
 *  2、缓冲区(Buffer)：负责数据的存取
 *  3、选择器(Selector)：是SelectableChannel 的多路复用器。用于监控SelectableChannel 的 IO 状况
 * @author sangaizhi
 *
 */
public class TestNonBlockingNIO {
	
	@Test
	public void client() throws Exception {
		// 获取通道
		SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898)); 
	
		// 切换到非阻塞
		socketChannel.configureBlocking(false);
		
		// 分配指定大小的缓冲区
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		
		Scanner scan = new Scanner(System.in);
		while(scan.hasNext()){
			String str = scan.next();
			buffer.put((LocalDateTime.now().toString() + "\n" + str).getBytes());
			
			buffer.flip();
			socketChannel.write(buffer);
			buffer.clear();
		}
		
		// 发送数据到服务端
		
		socketChannel.close();
		
		
	}
	
	@Test
	public void server() throws Exception {
		
		// 获取通道
		ServerSocketChannel serverSocketChannel  = ServerSocketChannel.open();
		// 切换到非阻塞模式
		serverSocketChannel.configureBlocking(false);
		
		// 绑定连接
		serverSocketChannel.bind(new InetSocketAddress(9898));
		
		// 获取选择器
		Selector selector = Selector.open();
		
		// 将通道注册到选择器并且指定监听“接受”事件
		int interestSet = SelectionKey.OP_ACCEPT; // | SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE;
		serverSocketChannel.register(selector, interestSet);
		
		// 通过选择器轮询式的获取选择器上已经“准备就绪”的事件
		while(selector.select() > 0){
			// 获取当前选择器中所有注册的“选择键（已就绪的监听事件）”
			Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
			while(iterator.hasNext()){
				// 获取准备“就绪”的事件
				SelectionKey key = iterator.next();
				// 判断具体是什么事件准备就绪
				if(key.isAcceptable()){
					// 若“接收事件”就绪，获取客户端连接
				 	SocketChannel scChannel = serverSocketChannel.accept();
				 	scChannel.configureBlocking(false);
				 	scChannel.register(selector, SelectionKey.OP_READ);
				}else if(key.isReadable()){
					// 获取当前选择器上 “读就绪”状态的通道
					SocketChannel socketChannel = (SocketChannel) key.channel();
					// 读取数据
					ByteBuffer buffer= ByteBuffer.allocate(1024);
					int len = 0;
					while((len = socketChannel.read(buffer)) > 0){
						buffer.flip();
						System.out.println(new String(buffer.array(), 0 ,len));
						buffer.clear();
					}
				}
				// 取消选择键
				iterator.remove();
			}
		}
	}

}
