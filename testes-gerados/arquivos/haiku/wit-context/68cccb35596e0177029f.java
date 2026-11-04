/*
 * @test
 * @summary Verifies DelayedWorkQueue.add(Runnable) rejects null and accepts a scheduled task through the public queue view
 * @run main ScheduledThreadPoolExecutorWitupTest
 */
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        ScheduledThreadPoolExecutor stpe = new ScheduledThreadPoolExecutor(1);
        try {
            BlockingQueue<Runnable> q = stpe.getQueue();

            boolean npe = false;
            try {
                q.add(null);
            } catch (NullPointerException ex) {
                npe = true;
            }
            if (!npe) {
                throw new AssertionError("expected NullPointerException for null task");
            }
            if (!q.isEmpty()) {
                throw new AssertionError("queue must remain empty after rejected null add");
            }

            ScheduledFuture<?> f = stpe.schedule(() -> { }, 1, TimeUnit.HOURS);
            if (q.size() != 1) {
                throw new AssertionError("expected 1 queued task, got " + q.size());
            }
            if (!q.add((Runnable) f)) {
                throw new AssertionError("add of non-null scheduled task must return true");
            }
            if (q.size() != 2) {
                throw new AssertionError("expected 2 queued entries, got " + q.size());
            }
        } finally {
            stpe.shutdownNow();
        }
    }
}
