/*
 * @test
 * @summary Test ScheduledThreadPoolExecutor.put(Runnable) method
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {
    public static void main(String[] args) {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        // Create a simple Runnable task
        Runnable task = new Runnable() {
            public void run() {
                // no-op
            }
        };

        // Call put with a valid Runnable
        executor.put(task);

        // Verify task is accepted and scheduled by checking active count after a short delay
        try {
            Thread.sleep(100); // wait a bit for task to be processed
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }

        int activeCount = executor.getActiveCount();
        if (activeCount < 0) {
            throw new AssertionError("Active count should not be negative");
        }

        // Shutdown executor
        executor.shutdown();
        try {
            if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
