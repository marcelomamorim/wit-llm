/*
 * @test
 * @summary Test ScheduledThreadPoolExecutor.contains(Object) method
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.RejectedExecutionHandler;

public class ScheduledThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        // Test contains with null (should return false, no exception)
        if (executor.contains(null)) {
            throw new RuntimeException("contains(null) should return false");
        }

        // Test contains with an object not scheduled (should return false)
        Object notScheduled = new Object();
        if (executor.contains(notScheduled)) {
            throw new RuntimeException("contains(non-scheduled) should return false");
        }

        // Schedule a simple Runnable task
        Runnable task = new Runnable() {
            public void run() {
                // no-op
            }
        };
        Future<?> future = executor.schedule(task, 100, TimeUnit.MILLISECONDS);

        // The scheduled task should be contained
        if (!executor.contains(future)) {
            throw new RuntimeException("contains(scheduled task) should return true");
        }

        // Wait for task to complete
        future.get();

        // After completion, contains should return false
        if (executor.contains(future)) {
            throw new RuntimeException("contains(completed task) should return false");
        }

        executor.shutdown();
        if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
            throw new RuntimeException("Executor did not terminate in time");
        }
    }
}
