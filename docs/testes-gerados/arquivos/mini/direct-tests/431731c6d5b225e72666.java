/*
 * @test
 * @summary Test ThreadPoolExecutor.getPoolSize() behavior
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ThreadFactory;

public class ThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        testGetPoolSizeInitiallyZero();
        testGetPoolSizeAfterTaskSubmission();
        testGetPoolSizeAfterShutdown();
    }

    static void testGetPoolSizeInitiallyZero() {
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(10);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1L, TimeUnit.SECONDS, queue);
        int poolSize = executor.getPoolSize();
        if (poolSize != 0) {
            throw new RuntimeException("Expected pool size 0 before any task submission, got " + poolSize);
        }
        executor.shutdownNow();
    }

    static void testGetPoolSizeAfterTaskSubmission() throws InterruptedException {
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(10);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1L, TimeUnit.SECONDS, queue);

        final AtomicInteger runCount = new AtomicInteger(0);
        Runnable task = new Runnable() {
            public void run() {
                runCount.incrementAndGet();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        executor.execute(task);

        // Wait briefly to allow thread to start
        Thread.sleep(50);

        int poolSize = executor.getPoolSize();
        if (poolSize != 1) {
            throw new RuntimeException("Expected pool size 1 after task submission, got " + poolSize);
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);

        if (runCount.get() == 0) {
            throw new RuntimeException("Task did not run as expected");
        }
    }

    static void testGetPoolSizeAfterShutdown() throws InterruptedException {
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(10);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1L, TimeUnit.SECONDS, queue);

        Runnable task = new Runnable() {
            public void run() {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        executor.execute(task);

        // Wait briefly to allow thread to start
        Thread.sleep(20);

        executor.shutdown();

        // After shutdown, getPoolSize should eventually return 0
        // Wait for termination
        executor.awaitTermination(2, TimeUnit.SECONDS);

        int poolSize = executor.getPoolSize();
        if (poolSize != 0) {
            throw new RuntimeException("Expected pool size 0 after termination, got " + poolSize);
        }
    }
}
