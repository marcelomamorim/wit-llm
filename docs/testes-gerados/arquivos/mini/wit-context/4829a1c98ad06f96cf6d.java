/*
 * @test
 * @summary Test DelayQueue.remove(Object) method behavior
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
            long d = this.getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
            return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof SimpleDelayed)) return false;
            SimpleDelayed other = (SimpleDelayed) obj;
            return delayNanos == other.delayNanos && startTime == other.startTime;
        }

        @Override
        public int hashCode() {
            return (int)(delayNanos ^ (delayNanos >>> 32)) + (int)(startTime ^ (startTime >>> 32));
        }
    }

    public static void main(String[] args) {
        DelayQueue<SimpleDelayed> queue = new DelayQueue<>();

        SimpleDelayed item1 = new SimpleDelayed(100, TimeUnit.MILLISECONDS);
        SimpleDelayed item2 = new SimpleDelayed(200, TimeUnit.MILLISECONDS);

        // Add items
        queue.offer(item1);
        queue.offer(item2);

        // Remove existing item returns true
        boolean removed = queue.remove(item1);
        if (!removed) {
            throw new RuntimeException("Failed to remove existing element");
        }

        // Remove non-existing item returns false
        SimpleDelayed item3 = new SimpleDelayed(300, TimeUnit.MILLISECONDS);
        boolean removedNonExisting = queue.remove(item3);
        if (removedNonExisting) {
            throw new RuntimeException("Removed non-existing element");
        }

        // Remove null returns false (no NullPointerException)
        boolean removedNull = queue.remove(null);
        if (removedNull) {
            throw new RuntimeException("Removed null element");
        }

        // Remove from empty queue returns false
        queue.clear();
        boolean removedFromEmpty = queue.remove(item2);
        if (removedFromEmpty) {
            throw new RuntimeException("Removed element from empty queue");
        }
    }
}
