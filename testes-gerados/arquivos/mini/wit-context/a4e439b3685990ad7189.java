/*
 * @test
 * @summary Test ScheduledThreadPoolExecutor.isEmpty() method behavior
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ScheduledThreadPoolExecutorWitupTest {
    public static void main(String[] args) {
        // Create ScheduledThreadPoolExecutor with 1 core thread
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        // Initially, executor should be empty (no tasks scheduled)
        if (!executor.isEmpty()) {
            throw new AssertionError("Expected executor to be empty initially");
        }

        // Schedule a no-op task with zero delay
        executor.schedule(() -> {}, 0, java.util.concurrent.TimeUnit.MILLISECONDS);

        // After scheduling a task, isEmpty() should return false
        if (executor.isEmpty()) {
            throw new AssertionError("Expected executor to not be empty after scheduling a task");
        }

        // Shutdown executor to clean up
        executor.shutdown();

        // After shutdown, isEmpty() may still reflect tasks; no exception expected
        // Just call to ensure no exception thrown
        executor.isEmpty();
    }
}
