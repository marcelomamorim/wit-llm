/* @test    @summary Test getTaskCount() method of ThreadPoolExecutor    @run main ThreadPoolExecutorWitupTest */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws InterruptedException {
        testGetTaskCount();
    }

    public static void testGetTaskCount() throws InterruptedException {
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(10);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS, workQueue);

        long initialCount = executor.getTaskCount();
        assert initialCount == 0 : "Initial task count should be 0";

        // Submit a task
        executor.execute(() -> {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Wait for task to be accepted
        Thread.sleep(100);
        long countAfterOneTask = executor.getTaskCount();
        assert countAfterOneTask >= 1 : "Task count should be at least 1 after submitting a task";

        // Submit multiple tasks
        for (int i = 0; i < 5; i++) {
            executor.execute(() -> {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        Thread.sleep(200);
        long countAfterMultipleTasks = executor.getTaskCount();
        assert countAfterMultipleTasks >= 6 : "Task count should be at least 6 after submitting multiple tasks";

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);
    }
}
