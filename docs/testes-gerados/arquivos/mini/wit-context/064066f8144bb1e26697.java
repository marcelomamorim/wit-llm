/*
 * @test
 * @summary Test ScheduledThreadPoolExecutor.offer(Runnable) for null input and normal behavior
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.locks.ReentrantLock;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testOfferNullThrowsNPE();
        testOfferReturnsTrueForValidRunnableScheduledFuture();
    }

    static void testOfferNullThrowsNPE() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        try {
            executor.offer(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            executor.shutdown();
        }
    }

    static void testOfferReturnsTrueForValidRunnableScheduledFuture() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        try {
            // Create a dummy RunnableScheduledFuture implementation
            RunnableScheduledFuture<?> task = new DummyRunnableScheduledFuture();
            boolean offered = executor.offer(task);
            if (!offered) {
                throw new Error("Expected offer to return true");
            }
        } finally {
            executor.shutdown();
        }
    }

    // Minimal dummy implementation to satisfy RunnableScheduledFuture interface
    static class DummyRunnableScheduledFuture implements RunnableScheduledFuture<Object> {
        private volatile boolean cancelled = false;

        @Override
        public long getDelay(java.util.concurrent.TimeUnit unit) {
            return 0;
        }

        @Override
        public int compareTo(java.util.concurrent.Delayed o) {
            return 0;
        }

        @Override
        public void run() {
            // no-op
        }

        @Override
        public boolean isPeriodic() {
            return false;
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            cancelled = true;
            return true;
        }

        @Override
        public boolean isCancelled() {
            return cancelled;
        }

        @Override
        public boolean isDone() {
            return false;
        }

        @Override
        public Object get() throws java.util.concurrent.ExecutionException, InterruptedException {
            return null;
        }

        @Override
        public Object get(long timeout, java.util.concurrent.TimeUnit unit) throws java.util.concurrent.ExecutionException, InterruptedException, java.util.concurrent.TimeoutException {
            return null;
        }
    }
}
