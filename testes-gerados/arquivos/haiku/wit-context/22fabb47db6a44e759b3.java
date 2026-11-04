/*
 * @test
 * @summary Checks that DelayQueue.peek() returns null on an empty queue and returns the head element without removing it, regardless of whether the head is expired.
 * @run main DelayQueueWitupTest
 */

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
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

        public int compareTo(Delayed o) {
            return Long.compare(delayNanos, ((Item) o).delayNanos);
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        DelayQueue<Item> queue = new DelayQueue<>();

        check(queue.peek() == null, "peek() on empty queue must return null");
        check(queue.size() == 0, "size must be 0 after empty peek");

        Item unexpired = new Item(TimeUnit.HOURS.toNanos(1));
        queue.add(unexpired);
        check(queue.peek() == unexpired, "peek() must return the only unexpired element");
        check(queue.size() == 1, "peek() must not remove the element");

        Item expired = new Item(-1L);
        queue.add(expired);
        check(queue.peek() == expired, "peek() must return the head with the earliest expiration");
        check(queue.size() == 2, "peek() must not change the size");

        check(queue.peek() == expired, "repeated peek() must return the same head");
        check(queue.size() == 2, "repeated peek() must not remove elements");
    }
}
