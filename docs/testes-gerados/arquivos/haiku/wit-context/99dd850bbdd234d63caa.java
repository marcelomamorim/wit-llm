/*
 * @test
 * @summary Verify contains() on the ScheduledThreadPoolExecutor work queue via public getQueue()
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        ScheduledThreadPoolExecutor stpe = new ScheduledThreadPoolExecutor(1);
        try {
            Runnable r = () -> { };
            ScheduledFuture<?> f = stpe.schedule(r, 1, TimeUnit.HOURS);

            check(stpe.getQueue().contains(f), "scheduled task should be contained in queue");
            check(!stpe.getQueue().contains(null), "contains(null) should return false");
            check(!stpe.getQueue().contains(new Object()), "unrelated object should not be contained");
            check(!stpe.getQueue().contains(r), "raw runnable should not be contained");

            f.cancel(false);
            check(stpe.getQueue().contains(f),
                  "cancelled task should remain queued under default removeOnCancel policy");
        } finally {
            stpe.shutdownNow();
        }
    }
}
