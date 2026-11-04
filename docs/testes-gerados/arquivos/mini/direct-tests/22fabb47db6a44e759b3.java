/*
 * @test
 * @summary Test DelayQueue.peek() method behavior
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
        DelayQueue<SimpleDelayed> queue = new DelayQueue<>();

        // Test peek on empty queue returns null
        if (queue.peek() != null) {
            throw new AssertionError("peek() on empty queue should return null");
        }

        // Add one element with positive delay
        SimpleDelayed delayed1 = new SimpleDelayed(1000, TimeUnit.MILLISECONDS);
        queue.add(delayed1);

        // peek should return the element just added
        SimpleDelayed peeked = queue.peek();
        if (peeked == null) {
            throw new AssertionError("peek() should not return null after adding element");
        }
        if (peeked != delayed1) {
            throw new AssertionError("peek() should return the first element added");
        }

        // Add another element with shorter delay
        SimpleDelayed delayed2 = new SimpleDelayed(10, TimeUnit.MILLISECONDS);
        queue.add(delayed2);

        // peek should now return the element with the shortest delay (delayed2)
        peeked = queue.peek();
        if (peeked != delayed2) {
            throw new AssertionError("peek() should return the element with shortest delay");
        }

        // Remove all elements
        queue.clear();

        // peek again on empty queue returns null
        if (queue.peek() != null) {
            throw new AssertionError("peek() on empty queue after clear should return null");
        }
    }
}
