/*
 * @test
 * @summary Witup-derived checks for ThreadPoolExecutor.setKeepAliveTime argument validation
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        testNegativeTimeRejectedSeconds();
        testNegativeTimeRejectedNanos();
        testMinValueRejected();
        testZeroTimeAcceptedWithoutCoreTimeout();
        testPositiveTimeAccepted();
    }

    private static ThreadPoolExecutor newPool() {
        return new ThreadPoolExecutor(1, 1, 1L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
    }

    static void testNegativeTimeRejectedSeconds() {
        ThreadPoolExecutor ex = newPool();
        try {
            ex.setKeepAliveTime(-1L, TimeUnit.SECONDS);
            throw new AssertionError("expected IllegalArgumentException for negative time");
        } catch (IllegalArgumentException expected) {
        } finally {
            ex.shutdownNow();
        }
    }

    static void testNegativeTimeRejectedNanos() {
        ThreadPoolExecutor ex = newPool();
        try {
            ex.setKeepAliveTime(-500L, TimeUnit.NANOSECONDS);
            throw new AssertionError("expected IllegalArgumentException for negative nanos");
        } catch (IllegalArgumentException expected) {
        } finally {
            ex.shutdownNow();
        }
    }

    static void testMinValueRejected() {
        ThreadPoolExecutor ex = newPool();
        try {
            ex.setKeepAliveTime(Long.MIN_VALUE, TimeUnit.MILLISECONDS);
            throw new AssertionError("expected IllegalArgumentException for Long.MIN_VALUE");
        } catch (IllegalArgumentException expected) {
        } finally {
            ex.shutdownNow();
        }
    }

    static void testZeroTimeAcceptedWithoutCoreTimeout() {
        ThreadPoolExecutor ex = newPool();
        try {
            ex.setKeepAliveTime(0L, TimeUnit.SECONDS);
        } finally {
            ex.shutdownNow();
        }
    }

    static void testPositiveTimeAccepted() {
        ThreadPoolExecutor ex = newPool();
        try {
            ex.setKeepAliveTime(250L, TimeUnit.MILLISECONDS);
            ex.setKeepAliveTime(1L, TimeUnit.MINUTES);
        } finally {
            ex.shutdownNow();
        }
    }
}
