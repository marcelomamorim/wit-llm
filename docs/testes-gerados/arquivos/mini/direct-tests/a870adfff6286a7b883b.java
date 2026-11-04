/*
 * @test
 * @summary Test DelayQueue.put(E) method behavior
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

    public static void main(String[] args) throws Exception {
        DelayQueue<SimpleDelayed> queue = new DelayQueue<>();

        // Test putting a single element
        SimpleDelayed d1 = new SimpleDelayed(100, TimeUnit.MILLISECONDS);
        queue.put(d1);
        if (queue.size() != 1) {
            throw new AssertionError("Queue size should be 1 after put");
        }

        // Test putting multiple elements
        SimpleDelayed d2 = new SimpleDelayed(200, TimeUnit.MILLISECONDS);
        SimpleDelayed d3 = new SimpleDelayed(50, TimeUnit.MILLISECONDS);
        queue.put(d2);
        queue.put(d3);
        if (queue.size() != 3) {
            throw new AssertionError("Queue size should be 3 after multiple puts");
        }

        // Test that elements are in the queue and can be taken after delay
        SimpleDelayed taken = queue.take(); // blocks until delay expired
        if (taken != d3) {
            throw new AssertionError("First taken element should be the one with shortest delay");
        }

        // Test that put does not accept null (DelayQueue does not permit null elements)
        boolean nullAccepted = false;
        try {
            queue.put(null);
            nullAccepted = true;
        } catch (NullPointerException expected) {
            // expected
        }
        if (nullAccepted) {
            throw new AssertionError("put(null) should throw NullPointerException");
        }
    }
}
