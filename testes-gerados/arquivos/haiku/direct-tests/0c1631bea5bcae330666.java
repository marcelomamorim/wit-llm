/*
 * @test
 * @summary Verifies ScheduledThreadPoolExecutor.remove(Object) return values and queue effects
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        testRemoveScheduledTask();
        testRemoveTwiceReturnsFalse();
        testRemoveUnknownReturnsFalse();
        testRemoveMiddleTaskKeepsOthers();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRemoveScheduledTask() {
        ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(1);
        try {
            Runnable r = () -> { };
            ScheduledFuture<?> f = pool.schedule(r, 1, TimeUnit.HOURS);
            check(pool.getQueue().size() == 1, "queue should have one task");
            check(pool.remove(f), "remove of scheduled task should return true");
            check(pool.getQueue().isEmpty(), "queue should be empty after remove");
        } finally {
            pool.shutdownNow();
        }
    }

    static void testRemoveTwiceReturnsFalse() {
        ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(1);
        try {
            Runnable r = () -> { };
            ScheduledFuture<?> f = pool.schedule(r, 1, TimeUnit.HOURS);
            check(pool.remove(f), "first remove should return true");
            check(!pool.remove(f), "second remove should return false");
            check(pool.getQueue().isEmpty(), "queue should remain empty");
        } finally {
            pool.shutdownNow();
        }
    }

    static void testRemoveUnknownReturnsFalse() {
        ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(1);
        try {
            Runnable scheduled = () -> { };
            Runnable other = () -> { };
            pool.schedule(scheduled, 1, TimeUnit.HOURS);
            check(!pool.remove(other), "unscheduled runnable should not be removed");
            check(!pool.remove(new Object()), "unrelated object should not be removed");
            check(pool.getQueue().size() == 1, "scheduled task should remain queued");
        } finally {
            pool.shutdownNow();
        }
    }

    static void testRemoveMiddleTaskKeepsOthers() {
        ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(1);
        try {
            ScheduledFuture<?>[] futures = new ScheduledFuture<?>[5];
            for (int i = 0; i < futures.length; i++) {
                Runnable r = () -> { };
                futures[i] = pool.schedule(r, 100L * (i + 1), TimeUnit.HOURS);
            }
            check(pool.getQueue().size() == 5, "queue should have five tasks");
            check(pool.remove(futures[2]), "removing middle-delay task should return true");
            check(pool.getQueue().size() == 4, "queue should have four tasks");
            check(!pool.remove(futures[2]), "removed task should not be removed again");
            check(pool.remove(futures[0]), "removing first-delay task should return true");
            check(pool.remove(futures[4]), "removing last-delay task should return true");
            check(pool.remove(futures[1]), "removing second-delay task should return true");
            check(pool.remove(futures[3]), "removing fourth-delay task should return true");
            check(pool.getQueue().isEmpty(), "queue should be empty after removing all");
        } finally {
            pool.shutdownNow();
        }
    }
}
