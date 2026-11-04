/*
 * @test
 * @summary Test getTaskCount method of ThreadPoolExecutor for correctness
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        // Create ThreadPoolExecutor with core=1, max=1, queue size=2
        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(2);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1L, TimeUnit.SECONDS, queue);

        // Initially, no tasks submitted, getTaskCount should be 0
        long initialCount = executor.getTaskCount();
        if (initialCount != 0L) {
            throw new Error("Expected initial task count 0 but got " + initialCount);
        }

        // Submit one task that sleeps briefly
        Runnable task1 = new Runnable() {
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        executor.execute(task1);

        // Submit two more tasks to fill the queue
        Runnable task2 = new Runnable() {
            public void run() {}
        };
        Runnable task3 = new Runnable() {
            public void run() {}
        };

        executor.execute(task2);
        executor.execute(task3);

        // At this point, one task running, two queued
        // getTaskCount should be 3 (1 running + 2 queued + 0 completed)
        long countDuringExecution = executor.getTaskCount();
        if (countDuringExecution != 3L) {
            throw new Error("Expected task count 3 during execution but got " + countDuringExecution);
        }

        // Wait for all tasks to complete
        executor.shutdown();
        if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
            throw new Error("Executor did not terminate in time");
        }

        // After completion, getTaskCount should still be 3
        long finalCount = executor.getTaskCount();
        if (finalCount != 3L) {
            throw new Error("Expected final task count 3 but got " + finalCount);
        }
    }
}
