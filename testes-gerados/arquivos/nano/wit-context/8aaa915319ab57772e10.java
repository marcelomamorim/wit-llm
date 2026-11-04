/* @test    @summary Test getTaskCount() method for ThreadPoolExecutor    @run main ThreadPoolExecutorWitupTest */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testGetTaskCountBasic();
        testGetTaskCountWithEmptyQueue();
        testGetTaskCountWithWorkers();
    }

    private static void testGetTaskCountBasic() {
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(10);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 10, TimeUnit.SECONDS, queue);
        long count = executor.getTaskCount();
        if (count != 0) {
            throw new AssertionError("Expected task count 0, but got " + count);
        }
        executor.shutdown();
    }

    private static void testGetTaskCountWithEmptyQueue() {
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(10);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 10, TimeUnit.SECONDS, queue);
        // No tasks submitted, queue empty
        long count = executor.getTaskCount();
        if (count != 0) {
            throw new AssertionError("Expected task count 0, but got " + count);
        }
        executor.shutdown();
    }

    private static void testGetTaskCountWithWorkers() {
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(10);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 10, TimeUnit.SECONDS, queue);
        // Submit a dummy task to create a worker
        executor.execute(() -> {});
        // Wait briefly to ensure worker is started
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        long count = executor.getTaskCount();
        if (count < 1) {
            throw new AssertionError("Expected task count >= 1, but got " + count);
        }
        executor.shutdown();
    }
}
