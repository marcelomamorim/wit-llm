/* @test    @summary Test DelayQueue.poll() method for various scenarios    @run main DelayQueueWitupTest */

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.NoSuchElementException;

public class DelayQueueWitupTest {

    static class DummyDelayed implements Delayed {
        private final long delayTime;
        private final long startTime;

        public DummyDelayed(long delay, TimeUnit unit) {
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
            if (other == null) {
                throw new NullPointerException();
            }
            if (this == other) {
                return 0;
            }
            long diff = this.getDelay(TimeUnit.NANOSECONDS) - other.getDelay(TimeUnit.NANOSECONDS);
            return (diff < 0) ? -1 : (diff > 0) ? 1 : 0;
        }
    }

    public static void main(String[] args) {
        testPollEmptyQueue();
        testPollWithExpiredElement();
        testPollWithNonExpiredElement();
        testPollMultipleElements();
        System.out.println("All tests passed.");
    }

    static void testPollEmptyQueue() {
        DelayQueue<Delayed> dq = new DelayQueue<>();
        assert dq.poll() == null : "Expected null when polling empty queue";
    }

    static void testPollWithExpiredElement() {
        DelayQueue<Delayed> dq = new DelayQueue<>();
        DummyDelayed expiredElement = new DummyDelayed(0, TimeUnit.NANOSECONDS);
        dq.offer(expiredElement);
        Delayed result = dq.poll();
        assert result == expiredElement : "Expected to poll the expired element";
        assert dq.size() == 0 : "Queue should be empty after poll";
    }

    static void testPollWithNonExpiredElement() {
        DelayQueue<Delayed> dq = new DelayQueue<>();
        DummyDelayed nonExpired = new DummyDelayed(10, TimeUnit.MILLISECONDS);
        dq.offer(nonExpired);
        assert dq.poll() == null : "Expected null when polling non-expired element";
        assert dq.size() == 1 : "Queue should still contain the non-expired element";
    }

    static void testPollMultipleElements() {
        DelayQueue<Delayed> dq = new DelayQueue<>();
        DummyDelayed expired1 = new DummyDelayed(0, TimeUnit.NANOSECONDS);
        DummyDelayed expired2 = new DummyDelayed(0, TimeUnit.NANOSECONDS);
        DummyDelayed nonExpired = new DummyDelayed(10, TimeUnit.MILLISECONDS);
        dq.offer(nonExpired);
        dq.offer(expired1);
        dq.offer(expired2);
        Delayed polled1 = dq.poll();
        assert (polled1 == expired1 || polled1 == expired2) : "Expected to poll an expired element";
        Delayed polled2 = dq.poll();
        assert (polled2 == expired1 || polled2 == expired2) && polled2 != polled1 : "Expected to poll the other expired element";
        Delayed result = dq.poll();
        assert result == null : "No more expired elements, should return null";
        assert dq.size() == 1 : "One element (non-expired) should remain";
    }
}
