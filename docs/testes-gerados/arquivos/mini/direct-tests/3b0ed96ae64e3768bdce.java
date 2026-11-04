/*
 * @test
 * @summary Test DelayQueue.next() method behavior
 * @run main DelayQueueWitupTest
 */

import java.util.NoSuchElementException;
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

    public static void main(String[] args) throws Exception {
        DelayQueue<SimpleDelayed> dq = new DelayQueue<>();

        // Add one element with zero delay
        SimpleDelayed d1 = new SimpleDelayed(0, TimeUnit.NANOSECONDS);
        dq.add(d1);

        // Use iterator to get the next() method indirectly
        java.util.Iterator<SimpleDelayed> it = dq.iterator();

        // next() should return the first element
        if (!it.hasNext()) {
            throw new AssertionError("Iterator should have next element");
        }
        SimpleDelayed first = it.next();
        if (first != d1) {
            throw new AssertionError("Iterator.next() did not return the expected element");
        }

        // next() called again without hasNext() should throw NoSuchElementException
        boolean threw = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Iterator.next() should throw NoSuchElementException when no more elements");
        }

        // Add multiple elements and test iteration order is not guaranteed but next() returns elements
        DelayQueue<SimpleDelayed> dq2 = new DelayQueue<>();
        SimpleDelayed d2 = new SimpleDelayed(10, TimeUnit.MILLISECONDS);
        SimpleDelayed d3 = new SimpleDelayed(20, TimeUnit.MILLISECONDS);
        dq2.add(d2);
        dq2.add(d3);

        java.util.Iterator<SimpleDelayed> it2 = dq2.iterator();

        int count = 0;
        while (it2.hasNext()) {
            SimpleDelayed e = it2.next();
            if (e == null) {
                throw new AssertionError("Iterator.next() returned null element");
            }
            count++;
        }
        if (count != 2) {
            throw new AssertionError("Iterator should have iterated over 2 elements");
        }

        // next() called after iteration ends throws NoSuchElementException
        boolean threw2 = false;
        try {
            it2.next();
        } catch (NoSuchElementException e) {
            threw2 = true;
        }
        if (!threw2) {
            throw new AssertionError("Iterator.next() should throw NoSuchElementException when no more elements");
        }
    }
}
