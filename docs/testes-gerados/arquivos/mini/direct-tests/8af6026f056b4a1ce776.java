/*
 * @test
 * @summary Test ScheduledThreadPoolExecutor.peek() method behavior
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class ScheduledThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        // Initially, queue is empty, peek() should return null
        RunnableScheduledFuture<?> peeked = executor.peek();
        if (peeked != null) {
            throw new AssertionError("Expected peek() to return null on empty queue");
        }

        // Schedule a simple task with delay 0
        RunnableScheduledFuture<?> scheduled = executor.schedule(new Callable<String>() {
            public String call() {
                return "done";
            }
        }, 0, TimeUnit.MILLISECONDS);

        // peek() should return the scheduled task (not null)
        RunnableScheduledFuture<?> peeked2 = executor.peek();
        if (peeked2 == null) {
            throw new AssertionError("Expected peek() to return non-null after scheduling a task");
        }

        // The peeked task should be the same as the scheduled task
        if (peeked2 != scheduled) {
            throw new AssertionError("peek() did not return the expected scheduled task");
        }

        executor.shutdownNow();
    }
}
