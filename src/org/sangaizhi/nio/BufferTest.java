package org.sangaizhi.nio;

import java.nio.ByteBuffer;

import org.junit.Test;

/**
 *  
 *  一、缓冲区( Buffer ): 在Java NIO 中负责数据的存取，缓冲区就是数组。用于存储不同的数据类型的数据
 *  根据数据类型的不同（boolean 除外）提供了相应类型的缓冲区：
 *  ByteBuffer
 *  CharBuffer
 *  ShortBuffer
 *  IntBuffer
 *  LongBuffer
 *  FloatBuffer
 *  DoubleBuffer
 *  
 *  这七个缓冲区的管理方式几乎一致，都是通过 allocate() 获取缓冲区
 *  
 *  二、Buffer 存取数据的两个核心方法:
 *  	put(): 存入数据到缓冲区
 *      get(): 获取缓冲区的数据
 *      rewind(): 可重复读数据
 *      clear()：清空缓冲区，但是缓冲区中的数据依然存在，但是处于“被遗忘数据”
 *      
 *  三：Buffer 的四个核心属性：
 *  	capacity ： 容量，表示缓冲区中最大存储数据的容量。一旦生成，不可改变
 *  	limit ：界限，表示缓冲器可以操作数据的大小。（limit 后的数据不能进行读写）
 *  	position ：位置，表示缓冲区中正在操作数据的位置      
 *  	mark：标记，表示记录当前position 的位置，可以通过reset() 恢复到 mark 的位置 
 *  	0 <= mark <= position <= limit <= capacity
 *  
 *  四：直接缓冲区与非直接缓冲区
 *  	非直接缓冲区：通过 allocate() 方法分配缓冲区，将缓冲区建立在 JVM 的内存中。
 *  	直接缓冲区：通过 allocateDirect() 方法分配缓冲区，将缓冲区建立在系统的物理内存中，可以提高效率。
 * @author sangaizhi
 *
 */
public class BufferTest {

	@Test
	public void test3() throws Exception {
		
		// 分配置直接缓冲区
		ByteBuffer buffer =  ByteBuffer.allocateDirect(1024);
		System.out.println(buffer.isDirect());
		
	}
	
	@Test
	public void test2() throws Exception {
		String str = "abcde";
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		buffer.put(str.getBytes());
		buffer.flip();
		byte[] bytes = new byte[buffer.limit()];
		buffer.get(bytes,0,2);
		System.out.println(new String(bytes, 0, 2));
		System.out.println(buffer.position());
		buffer.mark();
		buffer.get(bytes,2,2);
		System.out.println(new String(bytes, 2, 2));
		System.out.println(buffer.position());
		buffer.reset();
		System.out.println(buffer.position());
		
		if(buffer.hasRemaining()){
			System.out.println(buffer.remaining());
		}
 	}
	
	@Test
	public void testBuffer(){
		
		String str = "abcde";
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		System.out.println("初始创建  buffer：");
		System.out.println(buffer.position());
		System.out.println(buffer.limit());
		System.out.println(buffer.capacity());
		// 存数据
		buffer.put(str.getBytes());
		System.out.println("存数据开始  buffer：");
		System.out.println(buffer.position());
		System.out.println(buffer.limit());
		System.out.println(buffer.capacity());
		System.out.println("切换到读数据模式  buffer：");
		//切换到读数据模式
		buffer.flip();
		System.out.println(buffer.position());
		System.out.println(buffer.limit());
		System.out.println(buffer.capacity());
		System.out.println("读数据开始  buffer：");
		
		byte[] bytes = new byte[buffer.limit()];
		// 读数据到一个数组
		buffer.get(bytes);
		System.out.println(new String(bytes,0,bytes.length));
		System.out.println(buffer.position());
		System.out.println(buffer.limit());
		System.out.println(buffer.capacity());
		
		// rewind()  可重复读
		buffer.rewind();
		System.out.println(buffer.position());
		System.out.println(buffer.limit());
		System.out.println(buffer.capacity());
		
		//清空缓冲区，但是缓冲区中的数据依然存在，但是处于“被遗忘数据”
		buffer.clear();
		System.out.println(buffer.position());
		System.out.println(buffer.limit());
		System.out.println(buffer.capacity());
		System.out.println((char)buffer.get());
	}
}
