/*
 * @test
 * @summary ScheduledThreadPoolExecutor.getQueue().clear() empties the delayed work queue and leaves it reusable
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        testClearEmptiesQueue();
        testClearOnEmptyQueueIsNoop();
        testClearThenReuseQueue();
        testClearedFutureCanStillBeCancelled();
    }

    static void testClearEmptiesQueue() {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        try {
            for (int i = 0; i < 3; i++) {
                ex.schedule(() -> {}, 1, TimeUnit.HOURS);
            }
            BlockingQueue<Runnable> q = ex.getQueue();
            check(q.size() == 3, "expected 3 queued tasks before clear, got " + q.size());
            q.clear();
            check(q.isEmpty(), "queue should be empty after clear");
            check(q.size() == 0, "size should be 0 after clear, got " + q.size());
        } finally {
            ex.shutdownNow();
        }
    }

    static void testClearOnEmptyQueueIsNoop() {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        try {
            BlockingQueue<Runnable> q = ex.getQueue();
            check(q.isEmpty(), "new queue should be empty");
            q.clear();
            check(q.isEmpty(), "queue should remain empty after clear");
            check(q.size() == 0, "size should remain 0");
        } finally {
            ex.shutdownNow();
        }
    }

    static void testClearThenReuseQueue() {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        try {
            ex.schedule(() -> {}, 1, TimeUnit.HOURS);
            ex.schedule(() -> {}, 1, TimeUnit.HOURS);
            BlockingQueue<Runnable> q = ex.getQueue();
            check(q.size() == 2, "expected 2 tasks before clear");
            q.clear();
            check(q.isEmpty(), "queue should be empty after clear");
            ex.schedule(() -> {}, 1, TimeUnit.HOURS);
            check(q.size() == 1, "expected 1 task after rescheduling, got " + q.size());
            check(!q.isEmpty(), "queue should not be empty after new schedule");
        } finally {
            ex.shutdownNow();
        }
    }

    static void testClearedFutureCanStillBeCancelled() {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        try {
            ScheduledFuture<?> f = ex.schedule(() -> {}, 1, TimeUnit.HOURS);
            BlockingQueue<Runnable> q = ex.getQueue();
            q.clear();
            check(q.isEmpty(), "queue should be empty after clear");
            check(!f.isDone(), "future should not be done after clear");
            check(f.cancel(false), "cancel of a pending cleared future should return true");
            check(f.isCancelled(), "future should be cancelled");
        } finally {
            ex.shutdownNow();
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
