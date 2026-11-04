/*
 * @test
 * @summary Verifies observable behavior of ScheduledThreadPoolExecutor.shutdownNow()
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testShutdownNowOnIdleExecutorReturnsEmptyList() throws InterruptedException {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        List<Runnable> pending = ex.shutdownNow();
        check(pending != null, "shutdownNow must not return null");
        check(pending.isEmpty(), "idle executor should have no pending tasks");
        check(ex.isShutdown(), "executor should be shut down");
        check(ex.awaitTermination(10, TimeUnit.SECONDS), "executor should terminate");
    }

    static void testShutdownNowReturnsPendingDelayedTask() throws InterruptedException {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        ScheduledFuture<?> future = ex.schedule(() -> { }, 1, TimeUnit.HOURS);
        List<Runnable> pending = ex.shutdownNow();
        check(pending.size() == 1, "expected exactly one pending task but got " + pending.size());
        check(pending.contains(future), "returned list should contain the scheduled task");
        check(future.isCancelled() || !future.isDone(), "delayed task should not have completed");
        check(ex.isShutdown(), "executor should be shut down");
        check(ex.awaitTermination(10, TimeUnit.SECONDS), "executor should terminate");
    }

    static void testSecondShutdownNowReturnsEmptyList() throws InterruptedException {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        ex.schedule(() -> { }, 1, TimeUnit.HOURS);
        List<Runnable> first = ex.shutdownNow();
        check(first.size() == 1, "first shutdownNow should return the pending task");
        List<Runnable> second = ex.shutdownNow();
        check(second != null, "second shutdownNow must not return null");
        check(second.isEmpty(), "second shutdownNow should return no tasks");
        check(ex.awaitTermination(10, TimeUnit.SECONDS), "executor should terminate");
    }

    public static void main(String[] args) throws Exception {
        testShutdownNowOnIdleExecutorReturnsEmptyList();
        testShutdownNowReturnsPendingDelayedTask();
        testSecondShutdownNowReturnsEmptyList();
    }
}
