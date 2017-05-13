package org.sangaizhi.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.Test;

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
public class TestBlockingNIO {

	@Test
	public void client() throws IOException{
		// 1、获取通道
		SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
		
		FileChannel inChannel = FileChannel.open(Paths.get("1.txt"), StandardOpenOption.READ);
		// 2、分配指定的缓冲区大小
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		
		// 3、读取本地文件，并发送到服务器
		while(inChannel.read(buffer) != -1){
			buffer.flip();
			socketChannel.write(buffer);
			buffer.clear();
		}
		inChannel.close();
		socketChannel.close();
		
	}
	
	@Test
	public void server() throws IOException{
		// 1、获取通道
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		
		// 2、绑定连接
		serverSocketChannel.bind(new InetSocketAddress(9898));
		
		// 3、获取客户端连接的通道
		SocketChannel socketChannel = serverSocketChannel.accept();
		
		// 4、接受客户端的数据,并保存到本地
		FileChannel outChannel = FileChannel.open(Paths.get("2.jpg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);
		
		// 5、分配指定大小的缓冲区
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		while (socketChannel.read(buffer) != -1) {
			buffer.flip();
			outChannel.write(buffer);
			buffer.clear();
			
		}
		outChannel.close();
		socketChannel.close();
		
	}
}
