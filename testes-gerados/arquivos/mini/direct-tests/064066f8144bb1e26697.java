/*
 * @test
 * @summary Test ScheduledThreadPoolExecutor.offer(Runnable) method behavior
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Callable;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ScheduledThreadPoolExecutorWitupTest {

    // A minimal RunnableScheduledFuture implementation for testing
    static class DummyScheduledFuture<V> extends FutureTask<V> implements RunnableScheduledFuture<V> {
        private long seq = 0L;
        DummyScheduledFuture(Callable<V> c) {
            super(c);
        }
        DummyScheduledFuture(Runnable r, V result) {
            super(r, result);
        }
        public long getDelay(TimeUnit unit) {
            return 0;
        }
        public int compareTo(java.util.concurrent.Delayed o) {
            return 0;
        }
        public boolean isPeriodic() {
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        // Test 1: offer(null) throws NullPointerException
        try {
            executor.offer(null);
            throw new RuntimeException("Expected NullPointerException for offer(null)");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test 2: offer a valid RunnableScheduledFuture returns true
        DummyScheduledFuture<Void> task = new DummyScheduledFuture<Void>(() -> null);
        boolean offered = executor.offer(task);
        if (!offered) {
            throw new RuntimeException("Expected offer to return true for valid task");
        }

        // Test 3: offer multiple tasks and verify ordering and size increase
        DummyScheduledFuture<Void> task2 = new DummyScheduledFuture<Void>(() -> null);
        DummyScheduledFuture<Void> task3 = new DummyScheduledFuture<Void>(() -> null);

        boolean offered2 = executor.offer(task2);
        boolean offered3 = executor.offer(task3);

        if (!offered2 || !offered3) {
            throw new RuntimeException("Expected offer to return true for multiple tasks");
        }

        // Since internal queue is private, we cannot assert internal order,
        // but we can check that no exceptions were thrown and offer returns true.

        // Test 4: offer after shutdown still returns true (behavior of offer not restricted by shutdown)
        executor.shutdown();
        DummyScheduledFuture<Void> taskAfterShutdown = new DummyScheduledFuture<Void>(() -> null);
        boolean offeredAfterShutdown = executor.offer(taskAfterShutdown);
        if (!offeredAfterShutdown) {
            throw new RuntimeException("Expected offer to return true even after shutdown");
        }

        System.out.println("All ScheduledThreadPoolExecutor.offer tests passed.");
    }
}
