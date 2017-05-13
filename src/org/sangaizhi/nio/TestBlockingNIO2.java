package org.sangaizhi.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.Test;

public class TestBlockingNIO2 {

	@Test
	public void client() throws Exception {
		SocketChannel scChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
		FileChannel fileChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		
		while (fileChannel.read(buffer) != -1) {
			buffer.flip();
			scChannel.write(buffer);
			buffer.clear();
		}
		scChannel.shutdownOutput();
		
		// 接受服务端的反馈
		int len = 0;
		while((len = scChannel.read(buffer)) != -1){
			buffer.flip();
			System.out.println(new String(buffer.array(), 0, len));
		}
		
		fileChannel.close();
		scChannel.close();
		
	}
	
	@Test
	public void server() throws Exception {
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.bind(new InetSocketAddress(9898));
		
		FileChannel outChannel = FileChannel.open(Paths.get("2.jpg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);
		
		SocketChannel socketChannel = serverSocketChannel.accept();
		ByteBuffer buffer = ByteBuffer.allocate(1024);

		int len = 0;
		while((socketChannel.read(buffer)) != -1){
			buffer.flip();
			outChannel.write(buffer);
			buffer.clear();
		}
		
		// 发送反馈到客户端
		buffer.put("服务端接受到数据".getBytes());
		buffer.flip();
		socketChannel.write(buffer);
		socketChannel.shutdownInput();
		socketChannel.close();
		outChannel.close();
		serverSocketChannel.close();
		
	}
}
