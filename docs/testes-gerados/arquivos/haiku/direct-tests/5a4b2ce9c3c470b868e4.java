/*
 * @test
 * @summary Verifies the snapshot iterator returned by ScheduledThreadPoolExecutor.iterator()
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyPoolIterator() {
        ScheduledThreadPoolExecutor stpe = new ScheduledThreadPoolExecutor(1);
        try {
            Iterator<Runnable> it = stpe.iterator();
            check(it != null, "iterator() must not return null");
            check(!it.hasNext(), "empty pool iterator must have no elements");
            boolean thrown = false;
            try {
                it.next();
            } catch (NoSuchElementException e) {
                thrown = true;
            }
            check(thrown, "next() on exhausted iterator must throw NoSuchElementException");
        } finally {
            stpe.shutdownNow();
        }
    }

    static void testIteratorContainsScheduledTasks() {
        ScheduledThreadPoolExecutor stpe = new ScheduledThreadPoolExecutor(1);
        try {
            ScheduledFuture<?> f1 = stpe.schedule(() -> { }, 1, TimeUnit.HOURS);
            ScheduledFuture<?> f2 = stpe.schedule(() -> { }, 2, TimeUnit.HOURS);
            ScheduledFuture<?> f3 = stpe.schedule(() -> { }, 3, TimeUnit.HOURS);

            Set<Runnable> seen = new HashSet<>();
            int count = 0;
            for (Iterator<Runnable> it = stpe.iterator(); it.hasNext(); ) {
                seen.add(it.next());
                count++;
            }
            check(count == 3, "expected 3 queued tasks, got " + count);
            check(seen.contains(f1), "iterator must contain first scheduled task");
            check(seen.contains(f2), "iterator must contain second scheduled task");
            check(seen.contains(f3), "iterator must contain third scheduled task");
        } finally {
            stpe.shutdownNow();
        }
    }

    static void testIteratorIsSnapshot() {
        ScheduledThreadPoolExecutor stpe = new ScheduledThreadPoolExecutor(1);
        try {
            stpe.schedule(() -> { }, 1, TimeUnit.HOURS);
            stpe.schedule(() -> { }, 2, TimeUnit.HOURS);

            Iterator<Runnable> snapshot = stpe.iterator();

            stpe.schedule(() -> { }, 3, TimeUnit.HOURS);

            int count = 0;
            while (snapshot.hasNext()) {
                snapshot.next();
                count++;
            }
            check(count == 2, "iterator must be a snapshot of 2 tasks, got " + count);

            int fresh = 0;
            for (Iterator<Runnable> it = stpe.iterator(); it.hasNext(); it.next()) {
                fresh++;
            }
            check(fresh == 3, "new iterator must reflect 3 tasks, got " + fresh);
        } finally {
            stpe.shutdownNow();
        }
    }

    static void testIteratorReflectsCancellationWithRemoveOnCancel() {
        ScheduledThreadPoolExecutor stpe = new ScheduledThreadPoolExecutor(1);
        try {
            stpe.setRemoveOnCancelPolicy(true);
            ScheduledFuture<?> f1 = stpe.schedule(() -> { }, 1, TimeUnit.HOURS);
            stpe.schedule(() -> { }, 2, TimeUnit.HOURS);
            f1.cancel(false);

            List<Runnable> list = new ArrayList<>();
            for (Iterator<Runnable> it = stpe.iterator(); it.hasNext(); ) {
                list.add(it.next());
            }
            check(list.size() == 1, "cancelled task must be removed with remove-on-cancel policy, size=" + list.size());
            check(!list.contains(f1), "cancelled task must not appear in iterator");
        } finally {
            stpe.shutdownNow();
        }
    }

    public static void main(String[] args) {
        testEmptyPoolIterator();
        testIteratorContainsScheduledTasks();
        testIteratorIsSnapshot();
        testIteratorReflectsCancellationWithRemoveOnCancel();
    }
}
