/*
 * @test
 * @summary Checks DelayedWorkQueue.offer behavior through ScheduledThreadPoolExecutor.getQueue(): null rejection, non-RunnableScheduledFuture cast failure, and accepted scheduled futures
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        testNullRejected();
        testNullDoesNotChangeQueue();
        testNonScheduledFutureRejected();
        testScheduledFutureAccepted();
    }

    static void testNullRejected() {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        try {
            BlockingQueue<Runnable> q = ex.getQueue();
            try {
                q.offer(null);
                throw new AssertionError("expected NullPointerException");
            } catch (NullPointerException expected) {
            }
        } finally {
            ex.shutdownNow();
        }
    }

    static void testNullDoesNotChangeQueue() {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        try {
            BlockingQueue<Runnable> q = ex.getQueue();
            try {
                q.offer(null);
            } catch (NullPointerException expected) {
            }
            if (q.size() != 0) {
                throw new AssertionError("queue changed after rejected null: " + q.size());
            }
        } finally {
            ex.shutdownNow();
        }
    }

    static void testNonScheduledFutureRejected() {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        try {
            BlockingQueue<Runnable> q = ex.getQueue();
            try {
                q.offer(() -> { });
                throw new AssertionError("expected ClassCastException");
            } catch (ClassCastException expected) {
            }
            if (q.size() != 0) {
                throw new AssertionError("queue changed after rejected task: " + q.size());
            }
        } finally {
            ex.shutdownNow();
        }
    }

    static void testScheduledFutureAccepted() {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        try {
            BlockingQueue<Runnable> q = ex.getQueue();
            ScheduledFuture<?> f = ex.schedule(() -> { }, 1, TimeUnit.HOURS);
            if (q.size() != 1) {
                throw new AssertionError("expected size 1 but was " + q.size());
            }
            Runnable r = (Runnable) f;
            if (!q.offer(r)) {
                throw new AssertionError("offer of RunnableScheduledFuture returned false");
            }
            if (q.size() != 2) {
                throw new AssertionError("expected size 2 but was " + q.size());
            }
            if (!q.contains(r)) {
                throw new AssertionError("queue does not contain offered future");
            }
            f.cancel(false);
        } finally {
            ex.shutdownNow();
        }
    }
}
