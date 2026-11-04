/*
 * @test
 * @summary Test getCompletedTaskCount returns correct count after tasks complete
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        final ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1, 1, 1L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1));

        // Submit 3 tasks that increment a counter
        final int taskCount = 3;
        final Counter counter = new Counter();

        for (int i = 0; i < taskCount; i++) {
            executor.execute(new Runnable() {
                public void run() {
                    counter.increment();
                }
            });
        }

        executor.shutdown();
        boolean terminated = executor.awaitTermination(5, TimeUnit.SECONDS);
        if (!terminated) {
            throw new RuntimeException("Executor did not terminate in time");
        }

        long completed = executor.getCompletedTaskCount();

        if (completed != taskCount) {
            throw new RuntimeException("Expected completed task count " + taskCount + ", but got " + completed);
        }
    }

    static class Counter {
        private volatile int count = 0;
        synchronized void increment() {
            count++;
        }
    }
}
