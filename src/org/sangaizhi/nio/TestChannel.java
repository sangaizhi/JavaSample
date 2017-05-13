package org.sangaizhi.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;

import org.junit.Test;

/**
 * 一、通道（channel）:用于源节点与目标节点的链接。在Java NIO 中负责缓冲区中数据的传输。Channel 本身不存储数据，因此需要配合缓冲区进行传输
 * 二、主要实现类：
 * 	java.nio.channels.Channel 接口：
 * 		|-- FileChannel
 * 		|-- SocketChannel
 * 		|-- ServerScoketChannel
 * 		|-- DatagramChannel
 * 三、获取通道
 * 	1、Java 针对支持通道的类提供了getChannel() 方法
 * 		本地IO：
 * 			FileInputStream / FileOutputStream
 * 			RandomAccessFile
 * 		网络IO：
 * 			Socket
 * 			ServerScoket
 * 			DatagramSocket 
 *  2、 在 JDK 1.7 中的 NIO2 针对各个通道提供了静态方法 open()
 *  3、 在 JDK 1.7 中的 NIO2 的Files 工具类的 newByteChannel()
 *  
 *  四：通道之间的数据传输
 *  	transFrom();
 *  	transTo();
 *  
 *  五：分散(Scatter)与聚集(Gather)
 *   分散读取(Scatering Reads):将通道中的数据分散到多个缓冲区(buffer)中
 *   聚集写入(Gathering Writes):将多个缓冲区中的数据聚集到通道中
 *   
 *  六：字符集：Charset
 *   编码：字符串  -->  字符数组
 *   解码：字符数组 -->  字符串
 * @author sangaizhi
 *
 */
public class TestChannel {
	
	
	
	
	@Test
	public void test6() throws Exception {
		Charset charset = Charset.forName("GBK");
		// 获取编码器
		CharsetEncoder encoder =  charset.newEncoder();
		// 获取解码器
		CharsetDecoder decoder =  charset.newDecoder();
		
		CharBuffer charBuffer = CharBuffer.allocate(1024);
		
		charBuffer.put("桑爱智");
		charBuffer.flip();
		
		// 编码
		ByteBuffer buffer = encoder.encode(charBuffer);
		for(int i = 0; i< buffer.capacity();i++){
			System.out.println(buffer.get());
		}
		
		// 解码
		buffer.flip();
		CharBuffer charBuffer2 = decoder.decode(buffer);
		System.out.println(charBuffer2.toString());
		
		System.out.println("------------------------------");
		buffer.flip();
		Charset charsetUTF8 = Charset.forName("UTF-8");
		CharBuffer charBufferUTF8 = charsetUTF8.decode(buffer);
		System.out.println(charBufferUTF8.toString());
		
	}
	
	@Test
	public void test5() throws Exception {
		Map<String,Charset> map = Charset.availableCharsets();
		for (Map.Entry<String, Charset> entry : map.entrySet()) {
			System.out.println(entry.getKey() + " = "+ entry.getValue());
		}
	}
	
	// 分散于聚集
	@Test
	public void test4() throws Exception {
		RandomAccessFile file = new RandomAccessFile("1.txt", "rw");
		
		
		// 获取通道
		FileChannel channel1 = file.getChannel();
		
		// 分配指定大小的缓冲区
		ByteBuffer buffer1 = ByteBuffer.allocate(100);
		ByteBuffer buffer2 = ByteBuffer.allocate(1024);
		
		// 分散读取
		ByteBuffer[] bufs = {buffer1, buffer2};
		channel1.read(bufs);
		
		for(ByteBuffer temp : bufs){
			temp.flip();
		}
		System.out.println(new String(bufs[0].array(), 0, bufs[0].limit()));
		System.out.println("_________________");
		System.out.println(new String(bufs[1].array(), 0, bufs[1].limit()));
		
		// 聚集写入
		RandomAccessFile file2 = new RandomAccessFile("2.txt", "rw");
		FileChannel channel2 = file2.getChannel();
		channel2.write(bufs);
		channel2.close();
	}
	
	@Test
	public void test3() throws Exception {
		FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
		FileChannel outChannel = FileChannel.open(Paths.get("5.jpg"), StandardOpenOption.WRITE,StandardOpenOption.READ,StandardOpenOption.CREATE);
		
//		inChannel.transferTo(0, inChannel.size(), outChannel);
		outChannel.transferFrom(inChannel, 0, inChannel.size());
		inChannel.close();
		outChannel.close();
	}
	
	
	
	// 使用直接缓冲区完成文件的复制(内存映射文件)
	@Test
	public void test2() throws IOException {
		FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
		FileChannel outChannel = FileChannel.open(Paths.get("3.jpg"), StandardOpenOption.WRITE,StandardOpenOption.READ,StandardOpenOption.CREATE);
		
		
		MappedByteBuffer inMappedByteBuffer = inChannel.map(MapMode.READ_ONLY, 0, inChannel.size());
		MappedByteBuffer outMappedByteBuffer = outChannel.map(MapMode.READ_WRITE, 0, inChannel.size());
		
		byte[] dst = new byte[inMappedByteBuffer.limit()];
		// 直接对缓冲区中的数据进行读写操作
		inMappedByteBuffer.get(dst);
		outMappedByteBuffer.put(dst);
		inChannel.close();
		outChannel.close();
	}
	
	
	// 利用通道实现文件的复制（非直接缓冲区）
	@Test
	public void test1() throws Exception {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		// 获取通道
		FileChannel inChannel = null;
		FileChannel outChannel = null;
		try {
			fis = new FileInputStream("1.jpg");
			fos = new FileOutputStream("2.jpg");
			
			inChannel = fis.getChannel();
			outChannel = fos.getChannel();
			
			// 分配指定大小的缓冲区
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			
			
			// 将通道的中数据存入缓冲区
			while (inChannel.read(buffer) != -1) {
				buffer.flip();
				outChannel.write(buffer);
				buffer.clear();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(null != outChannel){outChannel.close();}
		if(null != inChannel){inChannel.close();}
		if(null != fos){fos.close();}
		if(null != fis){fis.close();}
		
		
		
		
		
	}
}
