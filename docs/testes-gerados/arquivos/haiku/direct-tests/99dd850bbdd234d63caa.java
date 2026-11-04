/*
 * @test
 * @summary Verifies ScheduledThreadPoolExecutor.contains for queued, cancelled and non-matching objects
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        try {
            Runnable r1 = () -> { };
            Runnable r2 = () -> { };
            ScheduledFuture<?> f1 = ex.schedule(r1, 1, TimeUnit.HOURS);
            ScheduledFuture<?> f2 = ex.schedule(r2, 1, TimeUnit.HOURS);

            check(ex.contains(f1), "scheduled future f1 should be contained");
            check(ex.contains(f2), "scheduled future f2 should be contained");
            check(!ex.contains(null), "null should not be contained");
            check(!ex.contains("not a task"), "unrelated object should not be contained");

            ex.setRemoveOnCancelPolicy(true);
            f1.cancel(false);

            check(!ex.contains(f1), "cancelled future removed on cancel should not be contained");
            check(ex.contains(f2), "non-cancelled future should still be contained");
        } finally {
            ex.shutdownNow();
        }
    }
}
