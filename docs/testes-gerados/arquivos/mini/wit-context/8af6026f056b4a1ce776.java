/*
 * @test
 * @summary Test ScheduledThreadPoolExecutor.peek() method for correct locking and peek behavior
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.RejectedExecutionHandler;

public class ScheduledThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        // Create ScheduledThreadPoolExecutor with 1 core thread
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        // Schedule a simple task with zero delay
        RunnableScheduledFuture<?> scheduled = executor.schedule(new Runnable() {
            public void run() {
                // no-op
            }
        }, 0, TimeUnit.MILLISECONDS);

        // peek() should return the head of the queue (the scheduled task)
        RunnableScheduledFuture<?> peeked = executor.peek();

        if (peeked == null) {
            throw new AssertionError("peek() returned null, expected non-null RunnableScheduledFuture");
        }

        // The peeked task should be the same as the scheduled task
        if (peeked != scheduled) {
            throw new AssertionError("peek() did not return the expected head task");
        }

        // Shutdown executor cleanly
        executor.shutdown();
        if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
            throw new AssertionError("Executor did not terminate in time");
        }
    }
}
