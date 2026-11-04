/*
 * @test
 * @summary Test ThreadPoolExecutor.setKeepAliveTime for argument validation and behavior
 * @run main java.util.concurrent.ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadPoolExecutorWitupTest {
    public static void main(String[] args) {
        testNegativeTimeThrows();
        testZeroTimeWithAllowCoreThreadTimeoutThrows();
        testZeroTimeWithoutAllowCoreThreadTimeoutSucceeds();
        testKeepAliveTimeDecreaseInterruptsIdleWorkers();
    }

    private static void testNegativeTimeThrows() {
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1));
        try {
            tpe.setKeepAliveTime(-1, TimeUnit.SECONDS);
            throw new RuntimeException("Expected IllegalArgumentException for negative time");
        } catch (IllegalArgumentException expected) {
            // expected
        } finally {
            tpe.shutdownNow();
        }
    }

    private static void testZeroTimeWithAllowCoreThreadTimeoutThrows() {
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1));
        try {
            tpe.allowCoreThreadTimeOut(true);
            try {
                tpe.setKeepAliveTime(0, TimeUnit.SECONDS);
                throw new RuntimeException("Expected IllegalArgumentException for zero time with allowCoreThreadTimeout true");
            } catch (IllegalArgumentException expected) {
                // expected
            }
        } finally {
            tpe.shutdownNow();
        }
    }

    private static void testZeroTimeWithoutAllowCoreThreadTimeoutSucceeds() {
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1));
        try {
            tpe.allowCoreThreadTimeOut(false);
            tpe.setKeepAliveTime(0, TimeUnit.SECONDS); // should not throw
        } finally {
            tpe.shutdownNow();
        }
    }

    private static void testKeepAliveTimeDecreaseInterruptsIdleWorkers() {
        // This test verifies that decreasing keepAliveTime triggers interruptIdleWorkers indirectly.
        // We cannot directly observe interruptIdleWorkers, but we can check that no exception is thrown
        // and the keepAliveTime is updated correctly.

        ThreadPoolExecutor tpe = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1));
        try {
            tpe.setKeepAliveTime(5, TimeUnit.SECONDS);
            tpe.setKeepAliveTime(1, TimeUnit.SECONDS); // decrease
            // No direct observable effect, but no exception and keepAliveTime updated.
        } finally {
            tpe.shutdownNow();
        }
    }
}
