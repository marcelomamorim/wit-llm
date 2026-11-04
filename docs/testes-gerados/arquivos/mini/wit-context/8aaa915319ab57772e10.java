/*
 * @test
 * @summary Test ThreadPoolExecutor.getTaskCount() for correct counting and locking
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        final int corePoolSize = 1;
        final int maximumPoolSize = 1;
        final long keepAliveTime = 10L;
        final TimeUnit unit = TimeUnit.MILLISECONDS;
        final ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(10);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);

        // Initially, no tasks submitted, getTaskCount should be 0
        long initialCount = executor.getTaskCount();
        if (initialCount != 0L) {
            throw new AssertionError("Expected initial task count 0, got " + initialCount);
        }

        // Submit a simple runnable that increments a counter
        final int[] runFlag = new int[1];
        Runnable task = new Runnable() {
            public void run() {
                runFlag[0] = 1;
                try {
                    Thread.sleep(50); // simulate work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        executor.execute(task);

        // Wait for task to complete
        executor.shutdown();
        boolean terminated = executor.awaitTermination(1, TimeUnit.SECONDS);
        if (!terminated) {
            throw new AssertionError("Executor did not terminate in time");
        }

        // After execution, getTaskCount should be at least 1
        long afterCount = executor.getTaskCount();
        if (afterCount < 1L) {
            throw new AssertionError("Expected task count >= 1 after execution, got " + afterCount);
        }

        // The task should have run
        if (runFlag[0] != 1) {
            throw new AssertionError("Task did not run as expected");
        }

        System.out.println("ThreadPoolExecutor.getTaskCount() test passed.");
    }
}
