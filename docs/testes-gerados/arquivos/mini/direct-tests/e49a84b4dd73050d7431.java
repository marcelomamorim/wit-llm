/*
 * @test
 * @summary Test ScheduledThreadPoolExecutor.clear() method behavior
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        testClearOnEmptyExecutor();
        testClearRemovesScheduledTasks();
    }

    static void testClearOnEmptyExecutor() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        // Clear on empty executor should not throw and size remains zero
        executor.clear();
        if (executor.getQueue().size() != 0) {
            throw new AssertionError("Queue should be empty after clear on empty executor");
        }
        executor.shutdownNow();
    }

    static void testClearRemovesScheduledTasks() throws Exception {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        // Schedule a simple task with delay
        RunnableScheduledFuture<?> scheduledTask = (RunnableScheduledFuture<?>) executor.schedule(
            new Runnable() {
                public void run() {}
            }, 10, TimeUnit.SECONDS);

        // Confirm task is in the queue
        if (!executor.getQueue().contains(scheduledTask)) {
            throw new AssertionError("Scheduled task should be in the queue before clear");
        }

        // Call clear to remove all scheduled tasks
        executor.clear();

        // After clear, queue should be empty
        if (executor.getQueue().size() != 0) {
            throw new AssertionError("Queue should be empty after clear");
        }

        // The cleared task should have index set to -1 (not directly accessible, so we check it is removed from queue)
        if (executor.getQueue().contains(scheduledTask)) {
            throw new AssertionError("Scheduled task should be removed from queue after clear");
        }

        executor.shutdownNow();
    }
}
