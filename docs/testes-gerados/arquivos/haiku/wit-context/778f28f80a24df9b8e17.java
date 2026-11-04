/*
 * @test
 * @summary Behavioral tests for DelayQueue.poll() based on the current source (WIT expath discarded)
 * @run main DelayQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueWitupTest {

    static final class Item implements Delayed {
        final String name;
        long delayNanos;

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

    static void testPollEmptyReturnsNull() {
        DelayQueue<Item> q = new DelayQueue<Item>();
        check(q.poll() == null, "poll on empty queue must return null");
        check(q.size() == 0, "size must remain 0");
    }

    static void testPollExpiredReturnsElement() {
        DelayQueue<Item> q = new DelayQueue<Item>();
        Item expired = new Item("expired", 0L);
        q.add(expired);
        Item polled = q.poll();
        check(polled == expired, "expired element must be returned by poll");
        check(q.isEmpty(), "queue must be empty after removing expired element");
    }

    static void testPollUnexpiredReturnsNullAndKeepsSize() {
        DelayQueue<Item> q = new DelayQueue<Item>();
        q.add(new Item("future", 3600L * 1000000000L));
        check(q.poll() == null, "unexpired element must not be returned by poll");
        check(q.size() == 1, "unexpired element must remain in queue");
    }

    static void testPollReturnsMostOverdueFirst() {
        DelayQueue<Item> q = new DelayQueue<Item>();
        Item a = new Item("a", -100L);
        Item b = new Item("b", -300L);
        Item c = new Item("c", -200L);
        q.add(a);
        q.add(b);
        q.add(c);
        check(q.poll() == b, "most overdue element must be polled first");
        check(q.poll() == c, "second most overdue element must be polled next");
        check(q.poll() == a, "least overdue element must be polled last");
        check(q.poll() == null, "poll on drained queue must return null");
    }

    static void testPollAfterDelayExpires() {
        DelayQueue<Item> q = new DelayQueue<Item>();
        Item item = new Item("late", 3600L * 1000000000L);
        q.add(item);
        check(q.poll() == null, "element must not be available before expiration");
        item.delayNanos = 0L;
        check(q.poll() == item, "element must be available once its delay has expired");
        check(q.size() == 0, "queue must be empty after polling expired element");
    }

    static void testPollOnCollectionConstructedQueue() {
        List<Item> initial = new ArrayList<Item>(Arrays.asList(
                new Item("x", -10L),
                new Item("y", -50L),
                new Item("z", 3600L * 1000000000L)));
        DelayQueue<Item> q = new DelayQueue<Item>(initial);
        check(q.size() == 3, "size must count expired and unexpired elements");
        Item first = q.poll();
        check(first != null && first.name.equals("y"), "most overdue element must be polled from collection-constructed queue");
        check(q.poll() != null && q.poll() == null, "only unexpired element must remain");
        check(q.size() == 1, "unexpired element must remain after polls");
    }

    public static void main(String[] args) {
        testPollEmptyReturnsNull();
        testPollExpiredReturnsElement();
        testPollUnexpiredReturnsNullAndKeepsSize();
        testPollReturnsMostOverdueFirst();
        testPollAfterDelayExpires();
        testPollOnCollectionConstructedQueue();
    }
}
