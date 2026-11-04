/*
 * @test
 * @summary Checks ScheduledThreadPoolExecutor.remove(Object) return values for queued and absent elements
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

    public static void main(String[] args) {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        try {
            Runnable task = new Runnable() {
                public void run() {}
            };
            ScheduledFuture<?> future = ex.schedule(task, 1, TimeUnit.HOURS);

            check(ex.getQueue().size() == 1, "queue should contain the scheduled task");
            check(ex.remove(future), "remove of a queued scheduled future should return true");
            check(ex.getQueue().isEmpty(), "queue should be empty after removal");
            check(!ex.remove(future), "second remove of the same future should return false");
            check(!ex.remove(new Object()), "remove of an unrelated object should return false");
            check(!ex.remove("not scheduled"), "remove of an unrelated String should return false");
            check(!ex.remove(null), "remove(null) should return false");
        } finally {
            ex.shutdownNow();
        }
    }
}
