package org.sangaizhi.nio;


import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

import org.junit.Test;


public class TestPipe {

	
	@Test
	public void test1() throws Exception {
		// 获取管道
		Pipe pipe = Pipe.open();
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		Pipe.SinkChannel sinkChannel = pipe.sink();
		
		buffer.put("通过单向管道发送数据".getBytes());
		buffer.flip();
		sinkChannel.write(buffer);
		
		Pipe.SourceChannel sourceChannel = pipe.source();
		
		buffer.flip();
		int len = sourceChannel.read(buffer);
		System.out.println(new String(buffer.array(), 0, len));
		
		sourceChannel.close();
		sinkChannel.close();
		
		
	}
}
