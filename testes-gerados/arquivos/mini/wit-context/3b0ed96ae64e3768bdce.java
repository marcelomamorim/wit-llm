/*
 * @test
 * @summary Test DelayQueue.Itr.next() method for NoSuchElementException and normal iteration
 * @run main DelayQueueWitupTest
 */

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class DelayQueueWitupTest {

    static class SimpleDelayed implements Delayed {
        private final long delayTimeNanos;
        private final long startTime;

        SimpleDelayed(long delay, TimeUnit unit) {
            this.delayTimeNanos = unit.toNanos(delay);
            this.startTime = System.nanoTime();
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long diff = (startTime + delayTimeNanos) - System.nanoTime();
            return unit.convert(diff, TimeUnit.NANOSECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            long diff = this.getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
            return (diff == 0) ? 0 : ((diff < 0) ? -1 : 1);
        }
    }

    public static void main(String[] args) {
        DelayQueue<SimpleDelayed> dq = new DelayQueue<>();

        // Add two elements with zero delay to ensure they are expired
        SimpleDelayed d1 = new SimpleDelayed(0, TimeUnit.NANOSECONDS);
        SimpleDelayed d2 = new SimpleDelayed(0, TimeUnit.NANOSECONDS);
        dq.add(d1);
        dq.add(d2);

        Iterator<SimpleDelayed> it = dq.iterator();

        // Test normal iteration: next() returns elements until exhausted
        SimpleDelayed first = it.next();
        if (first == null) {
            throw new AssertionError("Iterator.next() returned null instead of element");
        }
        SimpleDelayed second = it.next();
        if (second == null) {
            throw new AssertionError("Iterator.next() returned null instead of element");
        }

        // After consuming all elements, next() should throw NoSuchElementException
        boolean threw = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Iterator.next() did not throw NoSuchElementException when exhausted");
        }
    }
}
