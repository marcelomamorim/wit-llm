/*
 * @test
 * @summary Checks observable getTaskCount() values for ThreadPoolExecutor: zero when idle, running plus queued tasks while busy, and completed tasks after termination.
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        testIdleExecutorHasZeroTaskCount();
        testRunningAndQueuedTasksAreCounted();
        testCompletedTasksAfterTermination();
    }

    static void testIdleExecutorHasZeroTaskCount() throws InterruptedException {
        ThreadPoolExecutor e = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        try {
            check(e.getTaskCount() == 0L, "idle getTaskCount expected 0");
        } finally {
            e.shutdown();
            check(e.awaitTermination(10, TimeUnit.SECONDS), "executor did not terminate");
        }
    }

    static void testRunningAndQueuedTasksAreCounted() throws InterruptedException {
        ThreadPoolExecutor e = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        final CountDownLatch started = new CountDownLatch(1);
        final CountDownLatch gate = new CountDownLatch(1);
        try {
            e.execute(() -> {
                started.countDown();
                try {
                    gate.await(10, TimeUnit.SECONDS);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            });
            check(started.await(10, TimeUnit.SECONDS), "blocking task did not start");
            e.execute(() -> { });
            e.execute(() -> { });
            long count = e.getTaskCount();
            check(count == 3L, "expected 3 (1 running + 2 queued) but was " + count);
        } finally {
            gate.countDown();
            e.shutdown();
            check(e.awaitTermination(10, TimeUnit.SECONDS), "executor did not terminate");
        }
        check(e.getTaskCount() == 3L, "after termination expected 3 but was " + e.getTaskCount());
    }

    static void testCompletedTasksAfterTermination() throws InterruptedException {
        ThreadPoolExecutor e = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        e.execute(() -> { });
        e.execute(() -> { });
        e.execute(() -> { });
        e.shutdown();
        check(e.awaitTermination(10, TimeUnit.SECONDS), "executor did not terminate");
        long count = e.getTaskCount();
        check(count == 3L, "expected 3 completed tasks but was " + count);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
