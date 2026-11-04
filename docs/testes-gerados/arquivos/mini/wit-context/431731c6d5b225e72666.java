/*
 * @test
 * @summary Test ThreadPoolExecutor.getPoolSize() behavior and locking
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPoolExecutorWitupTest {
    public static void main(String[] args) {
        // Create a ThreadPoolExecutor with corePoolSize=1, maxPoolSize=1, keepAliveTime=1 second
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(1);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1L, TimeUnit.SECONDS, queue);

        // Test getPoolSize() returns 0 initially (no threads started yet)
        int initialPoolSize = executor.getPoolSize();
        if (initialPoolSize != 0) {
            throw new AssertionError("Expected initial pool size 0, got " + initialPoolSize);
        }

        // Submit a task to start a thread
        executor.execute(new Runnable() {
            public void run() {
                // do nothing
            }
        });

        // Wait briefly to allow thread to start
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError("Interrupted during sleep");
        }

        // Now getPoolSize() should be at least 1
        int poolSizeAfterSubmit = executor.getPoolSize();
        if (poolSizeAfterSubmit < 1) {
            throw new AssertionError("Expected pool size >= 1 after task submission, got " + poolSizeAfterSubmit);
        }

        // Shutdown executor
        executor.shutdown();
        try {
            if (!executor.awaitTermination(2, TimeUnit.SECONDS)) {
                throw new AssertionError("Executor did not terminate in time");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError("Interrupted during awaitTermination");
        }

        // After termination, getPoolSize() should return 0
        int poolSizeAfterShutdown = executor.getPoolSize();
        if (poolSizeAfterShutdown != 0) {
            throw new AssertionError("Expected pool size 0 after shutdown, got " + poolSizeAfterShutdown);
        }
    }
}
