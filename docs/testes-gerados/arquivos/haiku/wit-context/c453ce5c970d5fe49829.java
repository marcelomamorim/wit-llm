/*
 * @test
 * @summary ScheduledThreadPoolExecutor work queue toArray returns a snapshot of scheduled tasks as Object[]
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        testEmptyQueueToArray();
        testScheduledTasksAreReturned();
        testArrayIsSnapshot();
    }

    static void testEmptyQueueToArray() {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        try {
            Object[] a = ex.getQueue().toArray();
            check(a != null, "toArray returned null");
            check(a.length == 0, "expected empty array but length was " + a.length);
            check(a.getClass() == Object[].class, "expected Object[] but was " + a.getClass());
        } finally {
            ex.shutdownNow();
        }
    }

    static void testScheduledTasksAreReturned() {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        try {
            ScheduledFuture<?> f1 = ex.schedule(() -> { }, 1, TimeUnit.HOURS);
            ScheduledFuture<?> f2 = ex.schedule(() -> { }, 2, TimeUnit.HOURS);
            Object[] a = ex.getQueue().toArray();
            check(a.length == 2, "expected 2 elements but was " + a.length);
            check(contains(a, f1), "first scheduled task missing from toArray");
            check(contains(a, f2), "second scheduled task missing from toArray");
        } finally {
            ex.shutdownNow();
        }
    }

    static void testArrayIsSnapshot() {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        try {
            Runnable r1 = () -> { };
            Runnable r2 = () -> { };
            ex.schedule(r1, 1, TimeUnit.HOURS);
            ex.schedule(r2, 2, TimeUnit.HOURS);
            Object[] before = ex.getQueue().toArray();
            check(before.length == 2, "expected 2 elements before remove but was " + before.length);
            check(ex.remove(r1), "remove should report success for scheduled task");
            check(before.length == 2, "previously returned array must not change");
            Object[] after = ex.getQueue().toArray();
            check(after.length == 1, "expected 1 element after remove but was " + after.length);
        } finally {
            ex.shutdownNow();
        }
    }

    static boolean contains(Object[] a, Object o) {
        for (Object x : a) {
            if (x == o) {
                return true;
            }
        }
        return false;
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
