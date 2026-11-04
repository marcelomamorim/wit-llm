/*
 * @test
 * @summary Test DelayQueue.remove(Object) method behavior
 * @run main DelayQueueWitupTest
 */

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.RejectedExecutionException;

public class DelayQueueWitupTest {

    static class SimpleDelayed implements Delayed {
        private final long delayNanos;
        private final long startTime;
        private final String name;

        SimpleDelayed(long delay, TimeUnit unit, String name) {
            this.delayNanos = unit.toNanos(delay);
            this.startTime = System.nanoTime();
            this.name = name;
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

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof SimpleDelayed)) return false;
            SimpleDelayed other = (SimpleDelayed) obj;
            return name.equals(other.name);
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }

        @Override
        public String toString() {
            return "SimpleDelayed[" + name + "]";
        }
    }

    public static void main(String[] args) {
        DelayQueue<SimpleDelayed> queue = new DelayQueue<>();

        SimpleDelayed item1 = new SimpleDelayed(1000, TimeUnit.MILLISECONDS, "item1");
        SimpleDelayed item2 = new SimpleDelayed(2000, TimeUnit.MILLISECONDS, "item2");
        SimpleDelayed item3 = new SimpleDelayed(3000, TimeUnit.MILLISECONDS, "item3");

        // Add items
        boolean added1 = queue.offer(item1);
        boolean added2 = queue.offer(item2);
        boolean added3 = queue.offer(item3);

        if (!added1 || !added2 || !added3) {
            throw new RuntimeException("Failed to add items to DelayQueue");
        }

        // Remove existing item
        boolean removed = queue.remove(item2);
        if (!removed) {
            throw new RuntimeException("Failed to remove existing item from DelayQueue");
        }

        // Remove non-existing item
        SimpleDelayed nonExisting = new SimpleDelayed(1000, TimeUnit.MILLISECONDS, "nonExisting");
        boolean removedNonExisting = queue.remove(nonExisting);
        if (removedNonExisting) {
            throw new RuntimeException("remove returned true for non-existing item");
        }

        // Remove null (should return false, no exception)
        boolean removedNull = queue.remove(null);
        if (removedNull) {
            throw new RuntimeException("remove returned true for null");
        }

        // Remove from empty queue
        queue.clear();
        boolean removedFromEmpty = queue.remove(item1);
        if (removedFromEmpty) {
            throw new RuntimeException("remove returned true for item in empty queue");
        }

        // Final check: queue should be empty
        if (!queue.isEmpty()) {
            throw new RuntimeException("Queue not empty after removals");
        }

        System.out.println("All DelayQueue.remove tests passed.");
    }
}
