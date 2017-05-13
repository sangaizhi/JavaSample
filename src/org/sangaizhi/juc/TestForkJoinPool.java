package org.sangaizhi.juc;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

import org.junit.Test;

public class TestForkJoinPool {
	public static void main(String[] args) {
		Instant start = Instant.now();
		ForkJoinPool pool = new ForkJoinPool();
		ForkJoinTask<Long> task = new ForkJoinSumCalculate(0L, 10000000000L);
		Long sum = pool.invoke(task);
		System.out.println(sum);
		Instant end = Instant.now();
		System.out.println("耗時：" + Duration.between(start, end).toMillis());
	}
	
	
	@Test
	public void test() throws Exception {
		Instant start = Instant.now();
		long sum = 0;
		for (long i = 0; i <= 50000000000L; i++) {
			sum += i;
		}
		
		System.out.println(sum);
		Instant end = Instant.now();
		System.out.println("耗時：" + Duration.between(start, end).toMillis());
	}
	
	@Test
	public void test2() throws Exception {
		Instant start = Instant.now();
		Long sum = LongStream.range(0L, 10000000000L).parallel().reduce(0L, Long::sum);
		System.out.println(sum);
		Instant end = Instant.now();
		System.out.println("s耗時：" + Duration.between(start, end).toMillis());
	}
}

class ForkJoinSumCalculate extends RecursiveTask<Long>{


	/**
	 * 
	 */
	private static final long serialVersionUID = 8401083173550050632L;

	private long start;
	
	private long end;
	
	private static final long THURSHOLD = 10000L;
	
	
	public ForkJoinSumCalculate(){}
	public ForkJoinSumCalculate(long start, long end) {
		super();
		this.start = start;
		this.end = end;
	}



	@Override
	protected Long compute() {
		long length = end - start;
		if(length <= THURSHOLD){
			
			long sum= 0L;
			for (long i = start; i <= end; i++) {
				sum += i;
			}
			return sum;
			
		}else{
			long middle = (start + end) / 2;
		
			ForkJoinSumCalculate left = new ForkJoinSumCalculate(start, middle);
			left.fork();
			ForkJoinSumCalculate right = new ForkJoinSumCalculate(middle+1, end);
			right.fork();
			return left.join() + right.join();
		}
	}
	
}