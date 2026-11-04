/*
 * @test
 * @summary Verify allowCoreThreadTimeOut argument validation against keepAliveTime
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        testTrueWithZeroKeepAliveThrows();
        testTrueWithPositiveKeepAliveAccepted();
        testFalseWithZeroKeepAliveAccepted();
        testFailedCallDoesNotBlockLaterFalseCall();
    }

    private static ThreadPoolExecutor newPool(long keepAlive) {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
        return new ThreadPoolExecutor(1, 1, keepAlive, TimeUnit.MILLISECONDS, queue);
    }

    private static void testTrueWithZeroKeepAliveThrows() {
        ThreadPoolExecutor pool = newPool(0L);
        try {
            pool.allowCoreThreadTimeOut(true);
            throw new RuntimeException("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            String expected = "Core threads must have nonzero keep alive times";
            if (!expected.equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
        } finally {
            pool.shutdown();
        }
    }

    private static void testTrueWithPositiveKeepAliveAccepted() {
        ThreadPoolExecutor pool = newPool(1000L);
        try {
            pool.allowCoreThreadTimeOut(true);
            pool.allowCoreThreadTimeOut(false);
        } finally {
            pool.shutdown();
        }
    }

    private static void testFalseWithZeroKeepAliveAccepted() {
        ThreadPoolExecutor pool = newPool(0L);
        try {
            pool.allowCoreThreadTimeOut(false);
        } finally {
            pool.shutdown();
        }
    }

    private static void testFailedCallDoesNotBlockLaterFalseCall() {
        ThreadPoolExecutor pool = newPool(0L);
        try {
            try {
                pool.allowCoreThreadTimeOut(true);
                throw new RuntimeException("Expected IllegalArgumentException was not thrown");
            } catch (IllegalArgumentException expected) {
                // expected
            }
            pool.allowCoreThreadTimeOut(false);
        } finally {
            pool.shutdown();
        }
    }
}
