package guava;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.util.concurrent.RateLimiter;

/**
 * 令牌算法实现——RateLimiter
 */
public class RateLimiterTest {

//	@Test
	public void normalTest() {
		// 表示桶容量为5，且每秒新增5个令牌(桶内令牌初始量为1)
		RateLimiter limiter = RateLimiter.create(5);
		long s = System.currentTimeMillis();
		// acquire()/acquire(int permits)方法是阻塞的。
		assertEquals(0.0, limiter.acquire(), 0.01);
		assertEquals(0.2, limiter.acquire(), 0.1);
		assertEquals(0.2, limiter.acquire(), 0.01);
		long e = System.currentTimeMillis();
		System.out.println(e - s);
	}

//	@Test
	public void suddenlyTest() {
		RateLimiter limiter = RateLimiter.create(5);
		// limiter.acquire(5)表示一次性消费五个令牌
		// 令牌算法是运行提前消费的，但是要在之后补上
		assertEquals(0.0, limiter.acquire(5), 0.01);
		assertEquals(1.0, limiter.acquire(5), 0.01);
		assertEquals(1.0, limiter.acquire(5), 0.01);
		assertEquals(1.0, limiter.acquire(), 0.01);
	}
}
