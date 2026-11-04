/* @test    @summary Test DelayQueue.remove(Object o) method for correct removal behavior    @run main DelayQueueWitupTest */

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.List;

public class DelayQueueWitupTest {

    public static class DummyDelayed implements Delayed {
        private final long startTime;
        private final long delay;

        public DummyDelayed(long delay, TimeUnit unit) {
            this.startTime = System.nanoTime();
            this.delay = unit.toNanos(delay);
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long elapsed = System.nanoTime() - startTime;
            long remaining = delay - elapsed;
            return unit.convert(remaining, TimeUnit.NANOSECONDS);
        }

        @Override
        public int compareTo(Delayed other) {
            if (other == this) return 0;
            if (other instanceof DummyDelayed) {
                long diff = this.getDelay(TimeUnit.NANOSECONDS) - other.getDelay(TimeUnit.NANOSECONDS);
                return (diff < 0) ? -1 : (diff > 0) ? 1 : 0;
            }
            return 0;
        }
    }

    public static void main(String[] args) {
        DelayQueue<Delayed> dq = new DelayQueue<>();
        DummyDelayed item1 = new DummyDelayed(100, TimeUnit.MILLISECONDS);
        DummyDelayed item2 = new DummyDelayed(200, TimeUnit.MILLISECONDS);
        DummyDelayed item3 = new DummyDelayed(300, TimeUnit.MILLISECONDS);
        dq.add(item1);
        dq.add(item2);
        dq.add(item3);

        // Remove an existing element
        boolean removed = dq.remove(item2);
        assert removed : "Failed to remove existing element";

        // Verify that the element is no longer in the queue
        boolean containsItem2 = false;
        for (Delayed d : dq) {
            if (d == item2) {
                containsItem2 = true;
                break;
            }
        }
        assert !containsItem2 : "Element was not removed from the queue";

        // Attempt to remove an element not in the queue
        DummyDelayed itemNotInQueue = new DummyDelayed(400, TimeUnit.MILLISECONDS);
        boolean removedNotInQueue = dq.remove(itemNotInQueue);
        assert !removedNotInQueue : "Removing non-existent element should return false";

        // Remove remaining elements
        boolean removedItem1 = dq.remove(item1);
        boolean removedItem3 = dq.remove(item3);
        assert removedItem1 : "Failed to remove item1";
        assert removedItem3 : "Failed to remove item3";

        // Queue should be empty now
        assert dq.isEmpty() : "Queue should be empty after removals";
    }
}
