/*
 * @test
 * @summary Test ScheduledThreadPoolExecutor.isEmpty() method
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ScheduledThreadPoolExecutorWitupTest {
    public static void main(String[] args) {
        // Create ScheduledThreadPoolExecutor with 1 core thread
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        // Initially, executor should be empty
        if (!executor.isEmpty()) {
            throw new AssertionError("Executor should be empty initially");
        }

        // Schedule a task with zero delay
        executor.schedule(new Runnable() {
            public void run() {
                // no-op
            }
        }, 0, java.util.concurrent.TimeUnit.MILLISECONDS);

        // After scheduling, executor should not be empty
        if (executor.isEmpty()) {
            throw new AssertionError("Executor should not be empty after scheduling a task");
        }

        // Shutdown executor and wait for termination
        executor.shutdown();
        try {
            if (!executor.awaitTermination(1000, java.util.concurrent.TimeUnit.MILLISECONDS)) {
                throw new AssertionError("Executor did not terminate in time");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError("Test interrupted");
        }

        // After termination, executor should be empty
        if (!executor.isEmpty()) {
            throw new AssertionError("Executor should be empty after termination");
        }
    }
}
