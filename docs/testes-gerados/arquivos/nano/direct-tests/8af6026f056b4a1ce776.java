/*
 * @test    @summary Test peek() method of ScheduledThreadPoolExecutor
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Delayed;
import java.util.concurrent.atomic.AtomicInteger;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testPeekReturnsFirstElement();
        testPeekOnEmptyQueueReturnsNull();
    }

    private static void testPeekReturnsFirstElement() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        RunnableScheduledFuture<?> task1 = new DummyScheduledFuture();
        RunnableScheduledFuture<?> task2 = new DummyScheduledFuture();
        executor.getQueue().add(task1);
        executor.getQueue().add(task2);
        RunnableScheduledFuture<?> peeked = executor.peek();
        assert peeked == task1 : "peek() should return the first scheduled task";
    }

    private static void testPeekOnEmptyQueueReturnsNull() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        RunnableScheduledFuture<?> peeked = executor.peek();
        assert peeked == null : "peek() on empty queue should return null";
    }

    private static class DummyScheduledFuture implements RunnableScheduledFuture<Object> {
        private static final AtomicInteger counter = new AtomicInteger(0);
        private final int id = counter.incrementAndGet();
        @Override
        public void run() {
            // no-op
        }
        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return false;
        }
        @Override
        public boolean isCancelled() {
            return false;
        }
        @Override
        public boolean isDone() {
            return false;
        }
        @Override
        public Object get() {
            return null;
        }
        @Override
        public Object get(long timeout, TimeUnit unit) {
            return null;
        }
        @Override
        public long getDelay(TimeUnit unit) {
            return 0;
        }
        @Override
        public int compareTo(Delayed o) {
            return 0;
        }
    }
}
