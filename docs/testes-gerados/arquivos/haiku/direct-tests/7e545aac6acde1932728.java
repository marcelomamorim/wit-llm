/*
 * @test
 * @summary Verifies that put(Runnable) on the ScheduledThreadPoolExecutor work queue adds an element without blocking and is observable through the public BlockingQueue API
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        ScheduledThreadPoolExecutor ses = new ScheduledThreadPoolExecutor(1);
        try {
            BlockingQueue<Runnable> q = ses.getQueue();
            if (!q.isEmpty()) {
                throw new AssertionError("queue should start empty");
            }

            ScheduledFuture<?> future = ses.schedule(() -> { }, 1, TimeUnit.HOURS);
            Runnable task = (Runnable) future;

            if (q.size() != 1) {
                throw new AssertionError("expected size 1 after schedule, got " + q.size());
            }
            if (!q.contains(task)) {
                throw new AssertionError("scheduled task should be in queue");
            }

            q.put(task);

            if (q.size() != 2) {
                throw new AssertionError("expected size 2 after put, got " + q.size());
            }
            if (!q.contains(task)) {
                throw new AssertionError("put task should be observable in queue");
            }

            future.cancel(false);
        } finally {
            ses.shutdownNow();
        }
    }
}
