/*
 * @test
 * @summary Deterministic tests for java.util.concurrent.DelayQueue.peek()
 * @run main DelayQueueWitupTest
 */

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueWitupTest {

    static class SimpleDelayed implements Delayed {
        private final long delayNanos;
        private final long startTime;

        SimpleDelayed(long delay, TimeUnit unit) {
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
            if (o == this) return 0;
            long d = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
            return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
        }
    }

    public static void main(String[] args) {
        DelayQueue<SimpleDelayed> dq = new DelayQueue<>();

        // Test peek on empty queue returns null
        if (dq.peek() != null) {
            throw new AssertionError("peek() on empty queue should return null");
        }

        // Add one element with positive delay
        SimpleDelayed delayed = new SimpleDelayed(1000, TimeUnit.MILLISECONDS);
        dq.add(delayed);

        // peek should return the element just added
        SimpleDelayed peeked = dq.peek();
        if (peeked == null) {
            throw new AssertionError("peek() should not return null after adding element");
        }
        if (peeked != delayed) {
            throw new AssertionError("peek() should return the head element");
        }

        // Add another element with shorter delay (already expired)
        SimpleDelayed expired = new SimpleDelayed(-1, TimeUnit.MILLISECONDS);
        dq.add(expired);

        // peek should now return the expired element (head of queue)
        SimpleDelayed peeked2 = dq.peek();
        if (peeked2 != expired) {
            throw new AssertionError("peek() should return the element with earliest expiration");
        }

        // Remove all elements
        dq.clear();

        // peek again on empty queue returns null
        if (dq.peek() != null) {
            throw new AssertionError("peek() on empty queue after clear should return null");
        }
    }
}
