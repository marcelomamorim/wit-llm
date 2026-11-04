/*
 * @test
 * @summary Test DelayQueue.offer(E e, long timeout, TimeUnit unit) delegates to offer(E e)
 * @run main DelayQueueWitupTest
 */

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
            long d = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
            return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
        }
    }

    public static void main(String[] args) {
        DelayQueue<SimpleDelayed> queue = new DelayQueue<>();

        SimpleDelayed item = new SimpleDelayed(100, TimeUnit.MILLISECONDS);

        // The offer with timeout should delegate to offer(E e) and return true
        boolean offered = queue.offer(item, 1, TimeUnit.SECONDS);
        if (!offered) {
            throw new AssertionError("offer with timeout returned false, expected true");
        }

        // The queue should contain the offered element
        if (queue.size() != 1) {
            throw new AssertionError("Queue size expected 1 but was " + queue.size());
        }

        // The element returned by poll without waiting should be null because delay not expired
        if (queue.poll() != null) {
            throw new AssertionError("poll returned element before delay expired");
        }

        // Wait for delay to expire
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError("Interrupted during sleep");
        }

        // Now poll should return the element
        SimpleDelayed polled = queue.poll();
        if (polled == null) {
            throw new AssertionError("poll returned null after delay expired");
        }

        // The queue should be empty now
        if (queue.size() != 0) {
            throw new AssertionError("Queue size expected 0 after poll but was " + queue.size());
        }
    }
}
