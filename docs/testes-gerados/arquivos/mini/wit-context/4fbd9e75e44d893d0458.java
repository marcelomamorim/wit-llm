/*
 * @test
 * @summary Test ThreadPoolExecutor.getActiveCount() for correct active thread count and lock handling
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a ThreadPoolExecutor with corePoolSize=1, maxPoolSize=1, and a small queue
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
            1, 1, 1000L, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(1));

        // Initially, no active threads
        int activeCountInitial = executor.getActiveCount();
        if (activeCountInitial != 0) {
            throw new AssertionError("Expected 0 active threads initially, got " + activeCountInitial);
        }

        // Submit a task that blocks to keep thread active
        final Object lock = new Object();
        Runnable blockingTask = new Runnable() {
            public void run() {
                synchronized (lock) {
                    try {
                        lock.wait(5000); // wait to keep thread busy
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        };

        synchronized (lock) {
            executor.execute(blockingTask);

            // Give some time for the thread to start and lock
            Thread.sleep(100);

            int activeCountDuring = executor.getActiveCount();
            if (activeCountDuring < 1) {
                throw new AssertionError("Expected at least 1 active thread during task execution, got " + activeCountDuring);
            }
        }

        // Shutdown executor
        executor.shutdownNow();
        if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
            throw new AssertionError("Executor did not terminate in time");
        }

        // After shutdown, active count should be 0
        int activeCountAfter = executor.getActiveCount();
        if (activeCountAfter != 0) {
            throw new AssertionError("Expected 0 active threads after shutdown, got " + activeCountAfter);
        }
    }
}
