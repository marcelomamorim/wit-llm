/*
 * @test
 * @summary Test ThreadPoolExecutor.getActiveCount() returns correct count of active workers
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a ThreadPoolExecutor with corePoolSize=2, maxPoolSize=2
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(2);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 1L, TimeUnit.SECONDS, queue);

        // Initially, no active threads
        int initialActive = executor.getActiveCount();
        if (initialActive != 0) {
            throw new RuntimeException("Expected 0 active threads initially, got " + initialActive);
        }

        // Submit two long-running tasks to occupy both threads
        Runnable longTask = new Runnable() {
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        executor.execute(longTask);
        executor.execute(longTask);

        // Wait briefly to ensure tasks start
        Thread.sleep(200);

        int activeDuringTasks = executor.getActiveCount();
        if (activeDuringTasks != 2) {
            throw new RuntimeException("Expected 2 active threads during tasks, got " + activeDuringTasks);
        }

        // Shutdown executor and wait for tasks to finish
        executor.shutdown();
        if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
            throw new RuntimeException("Executor did not terminate in time");
        }

        // After shutdown, active count should be 0
        int activeAfterShutdown = executor.getActiveCount();
        if (activeAfterShutdown != 0) {
            throw new RuntimeException("Expected 0 active threads after shutdown, got " + activeAfterShutdown);
        }
    }
}
