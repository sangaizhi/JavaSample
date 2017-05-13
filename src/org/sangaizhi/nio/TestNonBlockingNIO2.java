package org.sangaizhi.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Scanner;

import org.junit.Test;

public class TestNonBlockingNIO2 {

	
	@Test
	public void send() throws IOException{
		// 获取通道
		DatagramChannel datagramChannel = DatagramChannel.open();
		
		// 切换为非阻塞模式
		datagramChannel.configureBlocking(false);
		
		// 定义缓冲区
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		
		
		Scanner scanner = new Scanner(System.in);
		
		while(scanner.hasNext()){
			String str = scanner.next();
			buffer.put((LocalDateTime.now().toString() + "\n" + str).getBytes());
			buffer.flip();
			datagramChannel.send(buffer, new InetSocketAddress("127.0.0.1", 9898));
			buffer.clear();
			
		}
		datagramChannel.close(); 
		
		
	}
	
	@Test
	public void receive() throws IOException{
		DatagramChannel datagramChannel = DatagramChannel.open();
		datagramChannel.configureBlocking(false);
		datagramChannel.bind(new InetSocketAddress(9898));
		
		Selector selector = Selector.open();
		datagramChannel.register(selector, SelectionKey.OP_READ);
		while(selector.select() > 0){
			Iterator<SelectionKey> it =  selector.selectedKeys().iterator();
			while(it.hasNext()){
				SelectionKey selectionKey = it.next();
				if(selectionKey.isReadable()){
					ByteBuffer buffer = ByteBuffer.allocate(1024);
					datagramChannel.receive(buffer);
					buffer.flip();
					System.out.println(new String(buffer.array(), 0, buffer.limit()));
					buffer.clear();
				}
			}
			it.remove();
		}
		datagramChannel.close();
	}
	
}
