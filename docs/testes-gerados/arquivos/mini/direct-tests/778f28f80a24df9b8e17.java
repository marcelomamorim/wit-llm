/*
 * @test
 * @summary Test DelayQueue.poll() behavior for expired and unexpired elements
 * @run main DelayQueueWitupTest
 */

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueWitupTest {

    static class TestDelayed implements Delayed {
        private final long delayNanos;
        private final long startTime;

        TestDelayed(long delay, TimeUnit unit) {
            this.delayNanos = unit.toNanos(delay);
            this.startTime = System.nanoTime();
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long diff = (startTime + delayNanos) - System.nanoTime();
            return unit.convert(diff, TimeUnit.NANOSECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            long d = this.getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
            return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
        }
    }

    public static void main(String[] args) throws Exception {
        DelayQueue<TestDelayed> dq = new DelayQueue<>();

        // Test poll on empty queue returns null
        if (dq.poll() != null) {
            throw new AssertionError("poll() on empty queue should return null");
        }

        // Add element with positive delay (not expired)
        TestDelayed delayedFuture = new TestDelayed(10, TimeUnit.SECONDS);
        dq.add(delayedFuture);

        // poll should return null because element delay > 0
        if (dq.poll() != null) {
            throw new AssertionError("poll() should return null for unexpired element");
        }

        // Add element with zero delay (expired)
        TestDelayed delayedExpired = new TestDelayed(0, TimeUnit.NANOSECONDS);
        dq.add(delayedExpired);

        // poll should return the expired element
        TestDelayed polled = dq.poll();
        if (polled == null) {
            throw new AssertionError("poll() should return expired element");
        }

        // The polled element should be the expired one (delay <= 0)
        if (polled.getDelay(TimeUnit.NANOSECONDS) > 0) {
            throw new AssertionError("polled element delay should be <= 0");
        }

        // After polling expired element, poll again should return null (only unexpired left)
        if (dq.poll() != null) {
            throw new AssertionError("poll() should return null after expired element removed");
        }
    }
}
