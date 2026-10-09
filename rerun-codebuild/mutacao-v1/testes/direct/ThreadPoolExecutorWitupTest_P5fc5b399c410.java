/* @test    @summary Test setKeepAliveTime with valid positive time and unit    @run main ThreadPoolExecutorWitupTest_P5fc5b399c410 */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorWitupTest_P5fc5b399c410 {

    public static void main(String[] args) {
        testSetKeepAliveTimePositive();
        testSetKeepAliveTimeZero();
        testSetKeepAliveTimeNegative();
    }

    private static void testSetKeepAliveTimePositive() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS, new java.util.concurrent.ArrayBlockingQueue<>(10));
        long newTime = 20;
        executor.setKeepAliveTime(newTime, TimeUnit.SECONDS);
        if (executor.keepAliveTime != TimeUnit.SECONDS.toNanos(newTime)) {
            throw new AssertionError("Failed: keepAliveTime not set correctly for positive value");
        }
    }

    private static void testSetKeepAliveTimeZero() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS, new java.util.concurrent.ArrayBlockingQueue<>(10));
        try {
            executor.setKeepAliveTime(0, TimeUnit.SECONDS);
            if (executor.keepAliveTime != 0) {
                throw new AssertionError("Failed: keepAliveTime not zero after setting zero");
            }
        } catch (IllegalArgumentException e) {
            throw new AssertionError("Unexpected exception for zero keepAliveTime");
        }
    }

    private static void testSetKeepAliveTimeNegative() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS, new java.util.concurrent.ArrayBlockingQueue<>(10));
        boolean thrown = false;
        try {
            executor.setKeepAliveTime(-1, TimeUnit.SECONDS);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException for negative keepAliveTime");
        }
    }
}
