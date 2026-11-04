/*
 * @test
 * @summary Checks that ThreadPoolExecutor.getCompletedTaskCount() starts at zero, counts completed tasks, and remains readable after shutdown
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        initialCountIsZero();
        countsCompletedTasksAfterTermination();
        countIsReadableAfterShutdown();
    }

    static void initialCountIsZero() {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
        ThreadPoolExecutor executor =
            new ThreadPoolExecutor(2, 2, 1L, TimeUnit.SECONDS, queue);
        try {
            long count = executor.getCompletedTaskCount();
            if (count != 0L) {
                throw new RuntimeException("Expected 0 completed tasks initially but was " + count);
            }
        } finally {
            executor.shutdownNow();
        }
    }

    static void countsCompletedTasksAfterTermination() throws InterruptedException {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
        ThreadPoolExecutor executor =
            new ThreadPoolExecutor(2, 2, 1L, TimeUnit.SECONDS, queue);
        final int taskCount = 5;
        for (int i = 0; i < taskCount; i++) {
            executor.execute(new Runnable() {
                public void run() {
                }
            });
        }
        executor.shutdown();
        if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
            executor.shutdownNow();
            throw new RuntimeException("Executor did not terminate in time");
        }
        long count = executor.getCompletedTaskCount();
        if (count != taskCount) {
            throw new RuntimeException("Expected " + taskCount
                + " completed tasks but was " + count);
        }
    }

    static void countIsReadableAfterShutdown() throws InterruptedException {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
        ThreadPoolExecutor executor =
            new ThreadPoolExecutor(1, 1, 1L, TimeUnit.SECONDS, queue);
        executor.execute(new Runnable() {
            public void run() {
            }
        });
        executor.shutdown();
        if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
            executor.shutdownNow();
            throw new RuntimeException("Executor did not terminate in time");
        }
        long first = executor.getCompletedTaskCount();
        long second = executor.getCompletedTaskCount();
        if (first != 1L || second != 1L) {
            throw new RuntimeException("Expected stable count 1 after shutdown but was "
                + first + " and " + second);
        }
    }
}
