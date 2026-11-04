/*
 * @test
 * @summary Verify that peek() on the ScheduledThreadPoolExecutor work queue returns the head element without removing it and returns null when empty
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        try {
            BlockingQueue<Runnable> q = ex.getQueue();
            if (q.peek() != null) {
                throw new RuntimeException("peek() on empty queue should return null");
            }
            Runnable noop = () -> { };
            ScheduledFuture<?> late = ex.schedule(noop, 2, TimeUnit.HOURS);
            ScheduledFuture<?> early = ex.schedule(noop, 1, TimeUnit.HOURS);
            Object head = q.peek();
            if (head != early) {
                throw new RuntimeException("peek() should return the earliest-enabled task");
            }
            if (q.size() != 2) {
                throw new RuntimeException("peek() must not remove elements, size=" + q.size());
            }
            if (q.peek() != early) {
                throw new RuntimeException("peek() should be idempotent");
            }
            if (late.isDone() || early.isDone()) {
                throw new RuntimeException("delayed tasks should not have run");
            }
        } finally {
            ex.shutdownNow();
        }
    }
}
