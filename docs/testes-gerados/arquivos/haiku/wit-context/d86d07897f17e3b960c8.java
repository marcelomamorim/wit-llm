/*
 * @test
 * @summary Verify observable behavior of ScheduledThreadPoolExecutor's queue poll(): null when empty and null while the head task is still delayed
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

            if (q.poll() != null) {
                throw new AssertionError("poll() on empty queue must return null");
            }

            ScheduledFuture<?> f = ex.schedule(() -> { }, 1, TimeUnit.HOURS);

            if (q.poll() != null) {
                throw new AssertionError("poll() must return null while head task is delayed");
            }
            if (q.size() != 1) {
                throw new AssertionError("delayed task must remain queued after poll(): size=" + q.size());
            }
            if (f.isDone()) {
                throw new AssertionError("delayed task must not be done");
            }
        } finally {
            ex.shutdownNow();
        }
    }
}
