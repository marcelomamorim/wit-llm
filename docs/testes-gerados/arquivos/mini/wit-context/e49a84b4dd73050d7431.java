/*
 * @test
 * @summary Test ScheduledThreadPoolExecutor.clear() method behavior
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;

public class ScheduledThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        // Create ScheduledThreadPoolExecutor with 1 core thread
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        // Schedule a simple task with delay
        Future<?> future = executor.schedule(new Runnable() {
            public void run() {
                // no-op
            }
        }, 10, TimeUnit.MILLISECONDS);

        // Call clear() to remove all scheduled tasks
        executor.getQueue().clear(); // The clear method tested is on the queue, not on executor directly

        // After clear, the queue size should be zero
        if (executor.getQueue().size() != 0) {
            throw new AssertionError("Queue not empty after clear");
        }

        // Schedule another task to verify executor still works after clear
        Future<?> future2 = executor.schedule(new Callable<String>() {
            public String call() {
                return "done";
            }
        }, 10, TimeUnit.MILLISECONDS);

        // Wait for the task to complete
        String result = future2.get(1, TimeUnit.SECONDS);
        if (!"done".equals(result)) {
            throw new AssertionError("Callable task did not return expected result");
        }

        executor.shutdown();
        if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
            throw new AssertionError("Executor did not terminate in time");
        }
    }
}
