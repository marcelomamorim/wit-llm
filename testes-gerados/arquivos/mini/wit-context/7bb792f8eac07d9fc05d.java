/*
 * @test
 * @summary Test ThreadPoolExecutor.getCompletedTaskCount() for correct aggregation and locking
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        final int corePoolSize = 1;
        final int maximumPoolSize = 1;
        final long keepAliveTime = 10L;
        final ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(1);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.MILLISECONDS,
                queue
        );

        // Before any task submitted, completedTaskCount should be 0
        long initialCount = executor.getCompletedTaskCount();
        if (initialCount != 0L) {
            throw new AssertionError("Expected initial completedTaskCount to be 0 but was " + initialCount);
        }

        // Submit a simple task that completes quickly
        Runnable task = new Runnable() {
            public void run() {
                // no-op
            }
        };

        executor.execute(task);

        // Wait for task completion
        executor.shutdown();
        boolean terminated = executor.awaitTermination(5, TimeUnit.SECONDS);
        if (!terminated) {
            throw new AssertionError("Executor did not terminate in time");
        }

        // After task completion, completedTaskCount should be at least 1
        long completedCount = executor.getCompletedTaskCount();
        if (completedCount < 1L) {
            throw new AssertionError("Expected completedTaskCount >= 1 but was " + completedCount);
        }
    }
}
