/* @test    @summary Test getCompletedTaskCount method for ThreadPoolExecutor    @run main ThreadPoolExecutorWitupTest */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testGetCompletedTaskCount();
    }

    public static void testGetCompletedTaskCount() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, java.util.concurrent.TimeUnit.MILLISECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10));
        long countBefore = executor.getCompletedTaskCount();
        assert countBefore >= 0 : "Initial completed task count should be non-negative";
        // Submit a dummy task to increase completed task count
        executor.execute(new Runnable() {
            public void run() {
                // do nothing
            }
        });
        // Wait for task to complete
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            // ignore
        }
        long countAfter = executor.getCompletedTaskCount();
        assert countAfter >= countBefore : "Completed task count should not decrease";
        executor.shutdown();
    }
}
