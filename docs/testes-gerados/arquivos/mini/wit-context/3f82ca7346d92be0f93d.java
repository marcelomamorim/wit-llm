/*
 * @test
 * @summary Test DelayQueue(Collection<? extends E>) constructor for null argument and basic addAll behavior
 * @run main DelayQueueWitupTest
 */

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

public class DelayQueueWitupTest {

    static class DummyDelayed implements Delayed {
        private final long delayNanos;

        DummyDelayed(long delayNanos) {
            this.delayNanos = delayNanos;
        }

        @Override
        public long getDelay(java.util.concurrent.TimeUnit unit) {
            return unit.convert(delayNanos, java.util.concurrent.TimeUnit.NANOSECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            if (o == this) return 0;
            long diff = this.getDelay(java.util.concurrent.TimeUnit.NANOSECONDS) - o.getDelay(java.util.concurrent.TimeUnit.NANOSECONDS);
            return (diff == 0) ? 0 : ((diff < 0) ? -1 : 1);
        }
    }

    public static void main(String[] args) {
        testConstructorWithNullCollection();
        testConstructorWithNonEmptyCollection();
    }

    private static void testConstructorWithNullCollection() {
        try {
            new DelayQueue(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testConstructorWithNonEmptyCollection() {
        List<DummyDelayed> list = new ArrayList<>();
        DummyDelayed d1 = new DummyDelayed(1000L);
        DummyDelayed d2 = new DummyDelayed(2000L);
        list.add(d1);
        list.add(d2);

        DelayQueue<DummyDelayed> dq = new DelayQueue<>(list);

        // The size should be equal to the number of elements added
        if (dq.size() != 2) {
            throw new RuntimeException("DelayQueue size expected 2 but was " + dq.size());
        }

        // The queue should contain the elements added
        if (!dq.contains(d1) || !dq.contains(d2)) {
            throw new RuntimeException("DelayQueue does not contain expected elements");
        }
    }
}
