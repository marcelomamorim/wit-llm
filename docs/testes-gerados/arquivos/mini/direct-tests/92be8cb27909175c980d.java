/*
 * @test
 * @summary Test DelayQueue add(E e) method delegates to offer(E e)
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
            long diff = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
            return (diff == 0) ? 0 : ((diff < 0) ? -1 : 1);
        }
    }

    public static void main(String[] args) {
        DelayQueue<SimpleDelayed> queue = new DelayQueue<>();

        SimpleDelayed d1 = new SimpleDelayed(100, TimeUnit.MILLISECONDS);
        boolean added = queue.add(d1);
        if (!added) {
            throw new RuntimeException("add returned false unexpectedly");
        }

        // The queue should contain the element after add
        if (queue.size() != 1) {
            throw new RuntimeException("Queue size expected to be 1 after add, but was " + queue.size());
        }

        // add should behave like offer, so adding null should throw NullPointerException
        boolean npeThrown = false;
        try {
            queue.add(null);
        } catch (NullPointerException npe) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException when adding null");
        }
    }
}
