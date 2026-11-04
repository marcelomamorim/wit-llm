/* @test    @summary Test getPoolSize method of ThreadPoolExecutor    @run main ThreadPoolExecutorWitupTest */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testGetPoolSizeReturnsWorkersSize();
        testGetPoolSizeReturnsZeroWhenTerminating();
    }

    private static void testGetPoolSizeReturnsWorkersSize() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 1000L, java.util.concurrent.TimeUnit.MILLISECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10));
        int size = executor.getPoolSize();
        if (size != 0) {
            System.out.println("Test failed: Expected pool size 0 before starting threads, but got " + size);
        } else {
            System.out.println("Test passed: getPoolSize returns 0 before threads are active.");
        }
        // Submit a dummy task to start a thread
        executor.execute(() -> {});
        // Wait briefly to allow thread to start
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            // ignore
        }
        size = executor.getPoolSize();
        if (size < 1) {
            System.out.println("Test failed: Expected pool size >= 1 after task submission, but got " + size);
        } else {
            System.out.println("Test passed: getPoolSize returns >= 1 after task submission.");
        }
        executor.shutdownNow();
    }

    private static void testGetPoolSizeReturnsZeroWhenTerminating() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1000L, java.util.concurrent.TimeUnit.MILLISECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10));
        executor.shutdown();
        // Wait for shutdown to complete
        try {
            executor.awaitTermination(1, java.util.concurrent.TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            // ignore
        }
        int size = executor.getPoolSize();
        if (size != 0) {
            System.out.println("Test failed: Expected pool size 0 after shutdown, but got " + size);
        } else {
            System.out.println("Test passed: getPoolSize returns 0 after shutdown.");
        }
    }
}
