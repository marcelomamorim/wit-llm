/* @test    @summary Test DelayQueue.put() method for basic functionality and exception handling    @run main DelayQueueWitupTest */

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.NoSuchElementException;

public class DelayQueueWitupTest {

    static class DummyDelayed implements Delayed {
        private final long delayTime;
        private final long startTime;

        DummyDelayed(long delay, TimeUnit unit) {
            this.delayTime = unit.toNanos(delay);
            this.startTime = System.nanoTime();
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long elapsed = System.nanoTime() - startTime;
            long remaining = delayTime - elapsed;
            return unit.convert(remaining, TimeUnit.NANOSECONDS);
        }

        @Override
        public int compareTo(Delayed other) {
            if (other == this) return 0;
            long diff = this.getDelay(TimeUnit.NANOSECONDS) - other.getDelay(TimeUnit.NANOSECONDS);
            return (diff < 0) ? -1 : ((diff > 0) ? 1 : 0);
        }
    }

    public static void main(String[] args) {
        DelayQueue<Delayed> dq = new DelayQueue<>();

        // Test 1: Adding a valid Delayed element
        Delayed d1 = new DummyDelayed(1, TimeUnit.MILLISECONDS);
        dq.put(d1);
        assert dq.size() == 1 : "Queue should contain 1 element after put";

        // Test 2: Adding multiple elements and verifying order
        Delayed d2 = new DummyDelayed(2, TimeUnit.MILLISECONDS);
        Delayed d3 = new DummyDelayed(0, TimeUnit.MILLISECONDS);
        dq.put(d2);
        dq.put(d3);
        assert dq.size() == 3 : "Queue should contain 3 elements";
        Delayed first = dq.peek();
        assert first != null : "Peek should not return null";
        assert first.compareTo(d3) == 0 : "First element should be the one with zero delay";

        // Test 3: Adding null should throw NullPointerException
        boolean exceptionThrown = false;
        try {
            dq.put(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Putting null should throw NullPointerException";

        // Test 4: Verify that put() adds element without removing existing
        int sizeBefore = dq.size();
        Delayed newDelayed = new DummyDelayed(5, TimeUnit.MILLISECONDS);
        dq.put(newDelayed);
        assert dq.size() == sizeBefore + 1 : "Queue size should increase by 1 after put";

        // Test 5: Verify that the element added is in the queue
        boolean found = false;
        for (Delayed d : dq) {
            if (d == newDelayed) {
                found = true;
                break;
            }
        }
        assert found : "Newly added element should be in the queue";
    }
}
