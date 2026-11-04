/*
 * @test
 * @summary Test DelayQueue(Collection<? extends E>) constructor behavior
 * @run main DelayQueueWitupTest
 */

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;

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
            long diff = this.getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
            return (diff == 0) ? 0 : ((diff < 0) ? -1 : 1);
        }

        @Override
        public String toString() {
            return "SimpleDelayed[delayNanos=" + delayNanos + "]";
        }
    }

    public static void main(String[] args) {
        // Test 1: Construct DelayQueue with empty collection
        Collection<SimpleDelayed> empty = new ArrayList<>();
        DelayQueue<SimpleDelayed> dqEmpty = new DelayQueue<>(empty);
        if (dqEmpty.size() != 0) {
            throw new RuntimeException("Expected empty DelayQueue size 0, got " + dqEmpty.size());
        }

        // Test 2: Construct DelayQueue with collection of delayed elements
        Collection<SimpleDelayed> coll = new ArrayList<>();
        SimpleDelayed d1 = new SimpleDelayed(100, TimeUnit.MILLISECONDS);
        SimpleDelayed d2 = new SimpleDelayed(200, TimeUnit.MILLISECONDS);
        SimpleDelayed d3 = new SimpleDelayed(50, TimeUnit.MILLISECONDS);
        coll.add(d1);
        coll.add(d2);
        coll.add(d3);

        DelayQueue<SimpleDelayed> dq = new DelayQueue<>(coll);

        // The size must be 3
        if (dq.size() != 3) {
            throw new RuntimeException("Expected DelayQueue size 3, got " + dq.size());
        }

        // The queue should contain all elements
        for (SimpleDelayed d : coll) {
            if (!dq.contains(d)) {
                throw new RuntimeException("DelayQueue missing element " + d);
            }
        }

        // Test 3: Iterator returns all elements (order not guaranteed)
        int count = 0;
        for (SimpleDelayed d : dq) {
            if (d == null) {
                throw new RuntimeException("Iterator returned null element");
            }
            count++;
        }
        if (count != 3) {
            throw new RuntimeException("Iterator count mismatch, expected 3, got " + count);
        }

        // Test 4: Construct DelayQueue with null collection should throw NullPointerException
        boolean npeThrown = false;
        try {
            new DelayQueue<SimpleDelayed>(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException when constructing DelayQueue with null collection");
        }

        System.out.println("All tests passed.");
    }
}
