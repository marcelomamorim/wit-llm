/*
 * @test
 * @summary Checks observable behavior of ScheduledThreadPoolExecutor's queue poll() via getQueue()
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    // Factory threads finish immediately, so scheduled tasks stay in the queue
    // and are not consumed by a worker, keeping the test deterministic.
    static ScheduledThreadPoolExecutor newIdleExecutor() {
        ThreadFactory tf = r -> new Thread(() -> { });
        return new ScheduledThreadPoolExecutor(1, tf);
    }

    static void testEmptyQueuePollReturnsNull() {
        ScheduledThreadPoolExecutor ex = newIdleExecutor();
        try {
            BlockingQueue<Runnable> q = ex.getQueue();
            check(q.poll() == null, "poll on empty queue should return null");
        } finally {
            ex.shutdownNow();
        }
    }

    static void testFutureDelayedTaskPollReturnsNull() {
        ScheduledThreadPoolExecutor ex = newIdleExecutor();
        try {
            ScheduledFuture<?> f = ex.schedule(() -> { }, 1, TimeUnit.HOURS);
            BlockingQueue<Runnable> q = ex.getQueue();
            check(q.poll() == null, "poll should return null when head delay is positive");
            check(q.size() == 1, "task should remain in queue after null poll");
            check(!f.isDone(), "delayed task should not be done");
        } finally {
            ex.shutdownNow();
        }
    }

    static void testReadyTaskIsPolledAndRemoved() {
        ScheduledThreadPoolExecutor ex = newIdleExecutor();
        try {
            ScheduledFuture<?> f = ex.schedule(() -> { }, 0, TimeUnit.MILLISECONDS);
            BlockingQueue<Runnable> q = ex.getQueue();
            Runnable polled = q.poll();
            check(polled != null, "poll should return the ready task");
            check(polled == f, "poll should return the scheduled future itself");
            check(q.size() == 0, "queue should be empty after polling the ready task");
            check(q.poll() == null, "second poll on empty queue should return null");
        } finally {
            ex.shutdownNow();
        }
    }

    public static void main(String[] args) {
        testEmptyQueuePollReturnsNull();
        testFutureDelayedTaskPollReturnsNull();
        testReadyTaskIsPolledAndRemoved();
    }
}
