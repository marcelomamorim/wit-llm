/*
 * @test
 * @summary Test ThreadPoolExecutor.setRejectedExecutionHandler behavior
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;

public class ThreadPoolExecutorWitupTest {
    public static void main(String[] args) {
        testSetRejectedExecutionHandlerNull();
        testSetRejectedExecutionHandlerValid();
    }

    static void testSetRejectedExecutionHandlerNull() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
            1, 1, 1L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1));
        try {
            executor.setRejectedExecutionHandler(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            executor.shutdown();
        }
    }

    static void testSetRejectedExecutionHandlerValid() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
            1, 1, 1L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1));
        RejectedExecutionHandler handler = new RejectedExecutionHandler() {
            public void rejectedExecution(Runnable r, ThreadPoolExecutor exec) {
                // no-op
            }
        };
        executor.setRejectedExecutionHandler(handler);
        // No exception expected, test passes if no exception thrown
        executor.shutdown();
    }
}
