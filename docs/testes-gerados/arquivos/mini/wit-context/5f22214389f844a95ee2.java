/*
 * @test
 * @summary Test ThreadPoolExecutor.setRejectedExecutionHandler for null rejection handler
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadPoolExecutorWitupTest {
    public static void main(String[] args) {
        final int corePoolSize = 1;
        final int maximumPoolSize = 1;
        final long keepAliveTime = 1L;
        final TimeUnit unit = TimeUnit.SECONDS;
        final ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(1);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);

        // Test that setRejectedExecutionHandler throws NullPointerException if handler is null
        boolean npeThrown = false;
        try {
            executor.setRejectedExecutionHandler(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException when setting null RejectedExecutionHandler");
        }

        // Test that setRejectedExecutionHandler accepts a non-null handler
        RejectedExecutionHandler dummyHandler = new RejectedExecutionHandler() {
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                // no-op
            }
        };

        executor.setRejectedExecutionHandler(dummyHandler);

        // No exception expected, test passes if reached here
        System.out.println("Test passed: setRejectedExecutionHandler correctly rejects null and accepts non-null handler.");

        executor.shutdown();
    }
}
