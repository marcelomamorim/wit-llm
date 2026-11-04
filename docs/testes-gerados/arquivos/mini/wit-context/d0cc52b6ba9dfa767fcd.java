/*
 * @test
 * @summary Test ThreadPoolExecutor.getLargestPoolSize() for correct locking and return
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a ThreadPoolExecutor with corePoolSize=1, maxPoolSize=1
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1, 1, 1000L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(1));

        // The largestPoolSize should be at least corePoolSize initially
        int largest = executor.getLargestPoolSize();
        if (largest < 1) {
            throw new AssertionError("largestPoolSize " + largest + " is less than 1");
        }

        // Submit a task to increase pool size
        executor.execute(new Runnable() {
            public void run() {
                // no-op
            }
        });

        // Wait briefly to allow thread to start
        Thread.sleep(100);

        // largestPoolSize should be >= 1
        int largestAfter = executor.getLargestPoolSize();
        if (largestAfter < 1) {
            throw new AssertionError("largestPoolSize after execute " + largestAfter + " is less than 1");
        }

        executor.shutdown();
        if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
            throw new AssertionError("Executor did not terminate in time");
        }

        System.out.println("Test passed");
    }
}
