/*
 * @test
 * @summary Test ThreadPoolExecutor.shutdownNow() returns queued tasks and interrupts workers
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a ThreadPoolExecutor with 1 core and max thread, and a LinkedBlockingQueue
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1L, TimeUnit.SECONDS, queue);

        // Submit a long-running task to occupy the single thread
        executor.execute(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(5000L); // sleep 5 seconds
                } catch (InterruptedException e) {
                    // Expected interruption
                    Thread.currentThread().interrupt();
                }
            }
        });

        // Submit additional tasks that will be queued
        Runnable queuedTask1 = new Runnable() { public void run() {} };
        Runnable queuedTask2 = new Runnable() { public void run() {} };
        executor.execute(queuedTask1);
        executor.execute(queuedTask2);

        // Call shutdownNow and capture returned queued tasks
        List<Runnable> returnedTasks = executor.shutdownNow();

        // The returned list should contain the queued tasks, but not the running one
        if (!returnedTasks.contains(queuedTask1) || !returnedTasks.contains(queuedTask2)) {
            throw new AssertionError("shutdownNow() did not return all queued tasks");
        }

        // The queue should be empty after shutdownNow
        if (!queue.isEmpty()) {
            throw new AssertionError("Queue not empty after shutdownNow()");
        }

        // The executor should be shutting down or terminated
        if (!executor.isShutdown()) {
            throw new AssertionError("Executor not shutdown after shutdownNow()");
        }

        // Wait for termination
        executor.awaitTermination(10, TimeUnit.SECONDS);

        if (!executor.isTerminated()) {
            throw new AssertionError("Executor not terminated after awaitTermination");
        }

        System.out.println("Test passed");
    }
}
