/*
 * @test
 * @summary Behavioral checks for DelayQueue.offer(E, long, TimeUnit), which delegates to offer(E) and ignores timeout and unit
 * @run main DelayQueueWitupTest
 */

import java.util.concurrent.Delayed;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

public class DelayQueueWitupTest {

    static final class Item implements Delayed {
        final long delayNanos;
        final String name;

        Item(String name, long delayNanos) {
            this.name = name;
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

    static void testTimedOfferReturnsTrueAndAddsElement() {
        DelayQueue<Item> q = new DelayQueue<>();
        Item a = new Item("a", -1L);
        boolean r = q.offer(a, 0L, TimeUnit.NANOSECONDS);
        check(r, "offer(e, timeout, unit) should return true");
        check(q.size() == 1, "size should be 1 after timed offer, was " + q.size());
    }

    static void testExpiredElementOfferedWithTimeoutIsPollable() {
        DelayQueue<Item> q = new DelayQueue<>();
        Item a = new Item("a", -1L);
        q.offer(a, 5L, TimeUnit.SECONDS);
        Item polled = q.poll();
        check(polled == a, "expired element offered with timeout should be returned by poll()");
        check(q.isEmpty(), "queue should be empty after polling its only element");
    }

    static void testUnexpiredElementNotPollable() {
        DelayQueue<Item> q = new DelayQueue<>();
        Item a = new Item("a", TimeUnit.HOURS.toNanos(1));
        boolean r = q.offer(a, 1L, TimeUnit.SECONDS);
        check(r, "offer should return true for unexpired element");
        check(q.poll() == null, "poll() must return null when no element has expired");
        check(q.size() == 1, "unexpired element should still be counted in size");
    }

    static void testTimeoutAndUnitAreIgnored() {
        DelayQueue<Item> q = new DelayQueue<>();
        Item a = new Item("a", -1L);
        Item b = new Item("b", -2L);
        check(q.offer(a, -100L, null), "negative timeout and null unit should not prevent offer");
        check(q.offer(b, Long.MAX_VALUE, TimeUnit.NANOSECONDS), "large timeout should not prevent offer");
        check(q.size() == 2, "both elements should be added, size was " + q.size());
    }

    static void testExpiredElementsAreReturnedInDelayOrder() {
        DelayQueue<Item> q = new DelayQueue<>();
        Item later = new Item("later", -1L);
        Item earlier = new Item("earlier", -3L);
        q.offer(later, 0L, TimeUnit.NANOSECONDS);
        q.offer(earlier, 0L, TimeUnit.NANOSECONDS);
        check(q.poll() == earlier, "element whose delay expired furthest in the past should be the head");
        check(q.poll() == later, "remaining expired element should be returned next");
        check(q.poll() == null, "queue should now be empty");
    }

    public static void main(String[] args) {
        testTimedOfferReturnsTrueAndAddsElement();
        testExpiredElementOfferedWithTimeoutIsPollable();
        testUnexpiredElementNotPollable();
        testTimeoutAndUnitAreIgnored();
        testExpiredElementsAreReturnedInDelayOrder();
    }
}
