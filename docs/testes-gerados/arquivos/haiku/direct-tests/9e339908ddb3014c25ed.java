/*
 * @test
 * @summary ScheduledThreadPoolExecutor.shutdownNow returns pending delayed tasks and empties the queue
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        testEmptyQueueReturnsEmptyList();
        testPendingDelayedTaskIsReturned();
        testSecondShutdownNowReturnsEmptyList();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyQueueReturnsEmptyList() throws InterruptedException {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        List<Runnable> remaining = executor.shutdownNow();
        check(remaining.isEmpty(), "expected empty list for empty queue");
        check(executor.isShutdown(), "executor should be shut down");
        check(executor.awaitTermination(10, TimeUnit.SECONDS), "executor should terminate");
    }

    static void testPendingDelayedTaskIsReturned() throws InterruptedException {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        Runnable task = () -> { throw new AssertionError("task must not run"); };
        Future<?> future = executor.schedule(task, 1, TimeUnit.HOURS);
        List<Runnable> remaining = executor.shutdownNow();
        check(remaining.size() == 1, "expected one pending task, got " + remaining.size());
        check(remaining.get(0) instanceof Future, "returned element should be a Future task");
        check(executor.isShutdown(), "executor should be shut down");
        check(executor.awaitTermination(10, TimeUnit.SECONDS), "executor should terminate");
        check(!future.isDone(), "delayed task must not complete");
    }

    static void testSecondShutdownNowReturnsEmptyList() throws InterruptedException {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        Runnable task = () -> { throw new AssertionError("task must not run"); };
        executor.schedule(task, 1, TimeUnit.HOURS);
        List<Runnable> first = executor.shutdownNow();
        check(first.size() == 1, "first call should return one task");
        List<Runnable> second = executor.shutdownNow();
        check(second.isEmpty(), "second call should return empty list");
        check(executor.awaitTermination(10, TimeUnit.SECONDS), "executor should terminate");
    }
}
