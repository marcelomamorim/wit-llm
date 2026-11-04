/*
 * @test
 * @summary ScheduledThreadPoolExecutor delegate queue add(Runnable) behavior via getQueue()
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
            } catch (NullPointerException e) {
                npe = true;
            }
            if (!npe) {
                throw new RuntimeException("add(null) should throw NullPointerException");
            }
            if (q.size() != 0) {
                throw new RuntimeException("queue should be empty after failed add");
            }

            ScheduledFuture<?> f = stpe.schedule(() -> { }, 1, TimeUnit.HOURS);
            stpe.setRemoveOnCancelPolicy(true);
            f.cancel(false);
            if (q.size() != 0) {
                throw new RuntimeException("cancelled task should be removed from queue");
            }

            if (!q.add((Runnable) f)) {
                throw new RuntimeException("add should return true for a RunnableScheduledFuture");
            }
            if (q.size() != 1) {
                throw new RuntimeException("queue size should be 1 after add");
            }
            if (!q.contains(f)) {
                throw new RuntimeException("queue should contain the added task");
            }
        } finally {
            stpe.shutdownNow();
        }
    }
}
