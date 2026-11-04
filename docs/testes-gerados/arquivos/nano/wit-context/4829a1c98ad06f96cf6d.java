/* @test    @summary Test for DelayQueue.remove(Object o) method to observe behavior and exception handling    @run main DelayQueueWitupTest */

import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Delayed;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Iterator;

public class DelayQueueWitupTest {

    public static class DummyDelayed implements Delayed {
        private final long startTime;
        public DummyDelayed(long delay, TimeUnit unit) {
            this.startTime = System.nanoTime() + unit.toNanos(delay);
        }
        @Override
        public long getDelay(TimeUnit unit) {
            long remaining = startTime - System.nanoTime();
            return unit.convert(remaining, TimeUnit.NANOSECONDS);
        }
        @Override
        public int compareTo(Delayed other) {
            if (other == null) return 1;
            long diff = this.getDelay(TimeUnit.NANOSECONDS) - other.getDelay(TimeUnit.NANOSECONDS);
            return (diff == 0) ? 0 : (diff < 0 ? -1 : 1);
        }
    }

    public static void main(String[] args) {
        DelayQueue<Delayed> dq = new DelayQueue<>();
        DummyDelayed d1 = new DummyDelayed(100, TimeUnit.MILLISECONDS);
        DummyDelayed d2 = new DummyDelayed(200, TimeUnit.MILLISECONDS);
        DummyDelayed d3 = new DummyDelayed(300, TimeUnit.MILLISECONDS);
        dq.add(d1);
        dq.add(d2);
        dq.add(d3);

        // Attempt to remove an element that exists
        boolean removedExisting = dq.remove(d2);
        System.out.println("Remove existing element: " + removedExisting);

        // Attempt to remove an element that does not exist
        DummyDelayed dNotInQueue = new DummyDelayed(400, TimeUnit.MILLISECONDS);
        boolean removedNonExisting = dq.remove(dNotInQueue);
        System.out.println("Remove non-existing element: " + removedNonExisting);

        // Attempt to remove null (should return false, no exception)
        boolean removedNull = dq.remove(null);
        System.out.println("Remove null element: " + removedNull);

        // Remove remaining elements
        dq.remove(d1);
        dq.remove(d3);

        // Queue should be empty now
        System.out.println("Queue size after removals: " + dq.size());
    }
}
