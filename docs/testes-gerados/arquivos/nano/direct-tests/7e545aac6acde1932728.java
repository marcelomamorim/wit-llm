/*
 * @test
 * @summary Test put method of ScheduledThreadPoolExecutor for basic functionality
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testPutWithRunnable();
        testPutNullRunnable();
    }

    static void testPutWithRunnable() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        Runnable task = () -> System.out.println("Task executed");
        executor.put(task);
        executor.shutdown();
        try {
            if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }

    static void testPutNullRunnable() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        try {
            executor.put(null);
            executor.shutdown();
            if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (NullPointerException e) {
            // Expected, as put(null) should throw NullPointerException
        } catch (Exception e) {
            // Fail if any other exception occurs
            throw new RuntimeException("Unexpected exception", e);
        }
    }
}
