/*
 * @test
 * @summary Verifies the iterator over ScheduledThreadPoolExecutor's queue: next() returns queued tasks and throws NoSuchElementException when exhausted
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        emptyQueueIteratorThrowsNoSuchElement();
        iteratorReturnsAllScheduledTasksThenThrows();
    }

    static void emptyQueueIteratorThrowsNoSuchElement() {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        try {
            Iterator<Runnable> it = ex.getQueue().iterator();
            if (it.hasNext()) {
                throw new AssertionError("empty queue iterator reports hasNext");
            }
            boolean thrown = false;
            try {
                it.next();
            } catch (NoSuchElementException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new AssertionError("next() on empty iterator did not throw NoSuchElementException");
            }
        } finally {
            ex.shutdownNow();
        }
    }

    static void iteratorReturnsAllScheduledTasksThenThrows() {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        try {
            List<ScheduledFuture<?>> futures = new ArrayList<>();
            futures.add(ex.schedule(() -> {}, 1, TimeUnit.HOURS));
            futures.add(ex.schedule(() -> {}, 2, TimeUnit.HOURS));
            futures.add(ex.schedule(() -> {}, 3, TimeUnit.HOURS));

            Iterator<Runnable> it = ex.getQueue().iterator();
            List<Runnable> got = new ArrayList<>();
            while (it.hasNext()) {
                Runnable r = it.next();
                if (r == null) {
                    throw new AssertionError("next() returned null");
                }
                got.add(r);
            }

            if (got.size() != futures.size()) {
                throw new AssertionError("expected " + futures.size() + " elements but got " + got.size());
            }
            for (ScheduledFuture<?> f : futures) {
                if (!got.contains(f)) {
                    throw new AssertionError("scheduled task missing from iterator");
                }
            }

            if (it.hasNext()) {
                throw new AssertionError("iterator has next after exhaustion");
            }
            boolean thrown = false;
            try {
                it.next();
            } catch (NoSuchElementException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new AssertionError("next() past end did not throw NoSuchElementException");
            }
        } finally {
            ex.shutdownNow();
        }
    }
}
