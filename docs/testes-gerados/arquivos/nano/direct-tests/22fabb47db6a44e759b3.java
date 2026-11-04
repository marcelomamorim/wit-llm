/*
 * @test    @summary Test DelayQueue.peek() method for correct behavior.
 * @run main DelayQueueWitupTest
 */

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

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
        public int compareTo(Delayed o) {
            if (o == this) return 0;
            long diff = this.getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
            return (diff == 0) ? 0 : (diff < 0 ? -1 : 1);
        }
    }

    public static void main(String[] args) {
        DelayQueue<Delayed> dq = new DelayQueue<>();
        Delayed d1 = new DummyDelayed(100, TimeUnit.MILLISECONDS);
        Delayed d2 = new DummyDelayed(200, TimeUnit.MILLISECONDS);
        dq.offer(d1);
        dq.offer(d2);
        Delayed peeked = dq.peek();
        if (peeked == null) {
            throw new AssertionError("Expected non-null peeked element");
        }
        // Since d1 has less delay, it should be at the head
        if (peeked != d1) {
            throw new AssertionError("Peeked element is not the expected one");
        }
        System.out.println("Test passed: peek() returned the expected element.");
    }
}
