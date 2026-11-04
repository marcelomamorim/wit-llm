/*
 * @test
 * @summary Verify DelayQueue.add(E) delegates to offer semantics: returns true, stores elements, and rejects null
 * @run main DelayQueueWitupTest
 */

import java.util.concurrent.Delayed;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

public class DelayQueueWitupTest {

    static final class Item implements Delayed {
        private final long delayNanos;

        Item(long delayNanos) {
            this.delayNanos = delayNanos;
        }

        public long getDelay(TimeUnit unit) {
            return unit.convert(delayNanos, TimeUnit.NANOSECONDS);
        }

        public int compareTo(Delayed other) {
            long d = getDelay(TimeUnit.NANOSECONDS) - other.getDelay(TimeUnit.NANOSECONDS);
            return d < 0 ? -1 : (d > 0 ? 1 : 0);
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        DelayQueue<Item> queue = new DelayQueue<>();

        Item expired = new Item(0L);
        check(queue.add(expired) == true, "add of expired element must return true");
        check(queue.size() == 1, "size must be 1 after first add");
        check(queue.peek() == expired, "expired element must be the head");

        Item later = new Item(3600L * 1_000_000_000L);
        check(queue.add(later) == true, "add of unexpired element must return true");
        check(queue.size() == 2, "size must be 2 after second add");
        check(queue.peek() == expired, "expired element must remain head");

        check(queue.poll() == expired, "poll must return the expired element");
        check(queue.size() == 1, "size must be 1 after poll");
        check(queue.poll() == null, "poll must return null when no element has expired");

        boolean npe = false;
        try {
            queue.add(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "add(null) must throw NullPointerException");
        check(queue.size() == 1, "failed add(null) must not change size");
    }
}
