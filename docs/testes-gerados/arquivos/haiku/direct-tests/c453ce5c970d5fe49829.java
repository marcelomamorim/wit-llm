/*
 * @test
 * @summary ScheduledThreadPoolExecutor.getQueue().toArray() returns a copy of the pending scheduled tasks
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static boolean containsIdentity(Object[] arr, Object o) {
        for (Object x : arr) {
            if (x == o) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        try {
            BlockingQueue<Runnable> q = ex.getQueue();

            Object[] empty = q.toArray();
            check(empty != null, "toArray returned null on empty queue");
            check(empty.length == 0, "empty queue toArray length should be 0");
            check(empty.getClass() == Object[].class, "toArray should return Object[]");

            ScheduledFuture<?> f1 = ex.schedule(() -> { }, 1, TimeUnit.HOURS);
            ScheduledFuture<?> f2 = ex.schedule(() -> { }, 2, TimeUnit.HOURS);
            ScheduledFuture<?> f3 = ex.schedule(() -> { }, 3, TimeUnit.HOURS);

            Object[] arr = q.toArray();
            check(arr.length == 3, "expected 3 elements, got " + arr.length);
            check(containsIdentity(arr, f1), "f1 missing from toArray");
            check(containsIdentity(arr, f2), "f2 missing from toArray");
            check(containsIdentity(arr, f3), "f3 missing from toArray");
            for (Object o : arr) {
                check(o instanceof Runnable, "element is not a Runnable");
            }

            // The returned array must be a copy: mutating it must not affect the queue.
            arr[0] = null;
            Object[] again = q.toArray();
            check(again.length == 3, "queue size changed after mutating copy");
            for (Object o : again) {
                check(o != null, "queue contents affected by mutating returned array");
            }

            // With remove-on-cancel policy, a cancelled task is removed immediately.
            ex.setRemoveOnCancelPolicy(true);
            f2.cancel(false);
            Object[] afterCancel = q.toArray();
            check(afterCancel.length == 2, "cancelled task should be removed, length=" + afterCancel.length);
            check(!containsIdentity(afterCancel, f2), "cancelled task still present");
            check(containsIdentity(afterCancel, f1), "f1 missing after cancel");
            check(containsIdentity(afterCancel, f3), "f3 missing after cancel");
        } finally {
            ex.shutdownNow();
        }
    }
}
