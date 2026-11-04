/*
 * @test
 * @summary Test ThreadPoolExecutor.setKeepAliveTime behavior and exceptions
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadPoolExecutorWitupTest {
    public static void main(String[] args) {
        testNegativeTimeThrows();
        testZeroTimeWithCoreTimeoutAllowedThrows();
        testSetKeepAliveTimeReducesTime();
        testSetKeepAliveTimeIncreasesTime();
    }

    static void testNegativeTimeThrows() {
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(1, 1, 1L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1));
        try {
            tpe.setKeepAliveTime(-1L, TimeUnit.SECONDS);
            throw new RuntimeException("Expected IllegalArgumentException for negative time");
        } catch (IllegalArgumentException expected) {
        }
        tpe.shutdownNow();
    }

    static void testZeroTimeWithCoreTimeoutAllowedThrows() {
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(1, 1, 1L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1));
        tpe.allowCoreThreadTimeOut(true);
        try {
            tpe.setKeepAliveTime(0L, TimeUnit.SECONDS);
            throw new RuntimeException("Expected IllegalArgumentException for zero time with core timeout allowed");
        } catch (IllegalArgumentException expected) {
        }
        tpe.shutdownNow();
    }

    static void testSetKeepAliveTimeReducesTime() {
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(1, 1, 10L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1));
        long oldTime = tpe.getKeepAliveTime(TimeUnit.NANOSECONDS);
        tpe.setKeepAliveTime(1L, TimeUnit.SECONDS);
        long newTime = tpe.getKeepAliveTime(TimeUnit.NANOSECONDS);
        if (newTime >= oldTime) {
            throw new RuntimeException("KeepAliveTime was not reduced as expected");
        }
        tpe.shutdownNow();
    }

    static void testSetKeepAliveTimeIncreasesTime() {
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(1, 1, 1L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1));
        long oldTime = tpe.getKeepAliveTime(TimeUnit.NANOSECONDS);
        tpe.setKeepAliveTime(10L, TimeUnit.SECONDS);
        long newTime = tpe.getKeepAliveTime(TimeUnit.NANOSECONDS);
        if (newTime <= oldTime) {
            throw new RuntimeException("KeepAliveTime was not increased as expected");
        }
        tpe.shutdownNow();
    }
}