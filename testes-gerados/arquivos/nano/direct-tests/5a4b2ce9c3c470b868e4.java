/*
 * @test    @summary Test iterator() method of ScheduledThreadPoolExecutor
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.Iterator;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testIteratorReturnsNonNullIterator();
        testIteratorLockingBehavior();
    }

    public static void testIteratorReturnsNonNullIterator() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        Iterator<Runnable> it = executor.iterator();
        if (it == null) {
            throw new AssertionError("iterator() returned null");
        }
        executor.shutdown();
    }

    public static void testIteratorLockingBehavior() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        // Submit a dummy task to ensure queue is not empty
        executor.execute(() -> {});
        Iterator<Runnable> it = executor.iterator();
        // The iterator should be non-null and usable
        if (it.hasNext()) {
            Runnable r = it.next();
            if (r == null) {
                throw new AssertionError("Iterator.next() returned null");
            }
        }
        executor.shutdown();
    }
}
