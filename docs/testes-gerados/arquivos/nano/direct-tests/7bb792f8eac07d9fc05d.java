/* @test    @summary Test getCompletedTaskCount() method of ThreadPoolExecutor    @run main ThreadPoolExecutorWitupTest */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testGetCompletedTaskCount();
    }

    public static void testGetCompletedTaskCount() {
        ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(10);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 10, TimeUnit.SECONDS, workQueue);
        long initialCount = executor.getCompletedTaskCount();
        Runnable task = new Runnable() {
            public void run() {
                // empty task
            }
        };
        executor.execute(task);
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            // ignore
        }
        long finalCount = executor.getCompletedTaskCount();
        // The count should be at least 1 after task completion
        if (finalCount < 1) {
            throw new AssertionError("Expected getCompletedTaskCount() >= 1, but got " + finalCount);
        }
        // Also, the count should be >= initialCount
        if (finalCount < initialCount) {
            throw new AssertionError("getCompletedTaskCount() decreased from " + initialCount + " to " + finalCount);
        }
    }
}
