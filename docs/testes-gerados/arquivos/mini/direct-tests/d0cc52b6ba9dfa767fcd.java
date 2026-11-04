/*
 * @test
 * @summary Test getLargestPoolSize method of ThreadPoolExecutor
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        // Create ThreadPoolExecutor with corePoolSize=1, maxPoolSize=1
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
            1, 1, 1000L, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(1));

        // Initially, largestPoolSize should be 0 (no threads started yet)
        int initialLargest = executor.getLargestPoolSize();
        if (initialLargest != 0) {
            throw new Error("Expected initial largestPoolSize to be 0 but was " + initialLargest);
        }

        // Submit a task to cause thread creation
        executor.execute(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        // Wait for task to start and complete
        executor.shutdown();
        if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
            throw new Error("Executor did not terminate in time");
        }

        // After execution, largestPoolSize should be at least 1
        int largestAfter = executor.getLargestPoolSize();
        if (largestAfter < 1) {
            throw new Error("Expected largestPoolSize >= 1 but was " + largestAfter);
        }
    }
}
