/*
 * @test
 * @summary Test ThreadPoolExecutor.shutdown() behavior
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;

public class ThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        testShutdownTransitionsStateAndTerminates();
    }

    private static void testShutdownTransitionsStateAndTerminates() throws Exception {
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(1);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1000L, TimeUnit.MILLISECONDS, queue);

        // Submit a simple task that sleeps briefly
        executor.execute(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // ignore
                }
            }
        });

        // Call shutdown and verify no new tasks are accepted
        executor.shutdown();

        boolean rejected = false;
        try {
            executor.execute(new Runnable() {
                public void run() {}
            });
        } catch (java.util.concurrent.RejectedExecutionException e) {
            rejected = true;
        }

        if (!rejected) {
            throw new RuntimeException("Expected RejectedExecutionException after shutdown");
        }

        // Await termination to ensure shutdown completes
        boolean terminated = executor.awaitTermination(5, TimeUnit.SECONDS);
        if (!terminated) {
            throw new RuntimeException("Executor did not terminate after shutdown within timeout");
        }
    }
}
