/*
 * @test
 * @summary Test ScheduledThreadPoolExecutor.remove(Object) method behavior
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        // Schedule a simple task
        ScheduledFuture<?> future = executor.schedule(new Runnable() {
            public void run() {
                // no-op
            }
        }, 100, TimeUnit.MILLISECONDS);

        // Remove the scheduled task before it runs
        boolean removed = executor.remove(future);

        if (!removed) {
            throw new RuntimeException("Failed to remove scheduled task that was just scheduled");
        }

        // Try to remove an object not in the queue
        Object notScheduled = new Object();
        boolean removedNonExistent = executor.remove(notScheduled);

        if (removedNonExistent) {
            throw new RuntimeException("remove returned true for an object not scheduled");
        }

        executor.shutdown();
        if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
            throw new RuntimeException("Executor did not terminate in time");
        }
    }
}
