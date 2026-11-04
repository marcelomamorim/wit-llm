/*
 * @test
 * @summary DelayQueue(Collection) constructor copies elements, orders expired ones and rejects null elements
 * @run main DelayQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
            return Long.compare(getDelay(TimeUnit.NANOSECONDS), o.getDelay(TimeUnit.NANOSECONDS));
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyCollection() {
        DelayQueue<Item> q = new DelayQueue<Item>(new ArrayList<Item>());
        check(q.size() == 0, "empty source must give size 0");
        check(q.isEmpty(), "empty source must give isEmpty true");
        check(q.poll() == null, "poll on empty queue must return null");
    }

    static void testExpiredElementsOrdered() {
        List<Item> src = Arrays.asList(
                new Item("a", -10L),
                new Item("c", -30L),
                new Item("b", -20L));
        DelayQueue<Item> q = new DelayQueue<Item>(src);
        check(q.size() == 3, "size must equal source size, got " + q.size());
        Item first = q.poll();
        Item second = q.poll();
        Item third = q.poll();
        check(first != null && first.name.equals("c"), "first expired head must be c");
        check(second != null && second.name.equals("b"), "second must be b");
        check(third != null && third.name.equals("a"), "third must be a");
        check(q.poll() == null, "queue must be empty after draining");
    }

    static void testUnexpiredElementsCountedButNotPolled() {
        List<Item> src = Arrays.asList(new Item("future", 3600L * 1000000000L));
        DelayQueue<Item> q = new DelayQueue<Item>(src);
        check(q.size() == 1, "unexpired element must be counted in size");
        check(q.poll() == null, "unexpired element must not be returned by poll");
        check(q.size() == 1, "size must remain 1 after failed poll");
    }

    static void testMixedExpiredAndUnexpired() {
        List<Item> src = Arrays.asList(
                new Item("future", 3600L * 1000000000L),
                new Item("old", -5L));
        DelayQueue<Item> q = new DelayQueue<Item>(src);
        check(q.size() == 2, "size must count both expired and unexpired");
        Item head = q.poll();
        check(head != null && head.name.equals("old"), "only expired element must be polled");
        check(q.poll() == null, "unexpired element must remain unavailable");
        check(q.size() == 1, "size must be 1 after polling expired element");
    }

    static void testConstructorCopiesSource() {
        List<Item> src = new ArrayList<Item>();
        src.add(new Item("x", -1L));
        DelayQueue<Item> q = new DelayQueue<Item>(src);
        src.add(new Item("y", -2L));
        src.clear();
        check(q.size() == 1, "later changes to source must not affect queue");
        check(q.poll() != null, "copied element must still be present");
    }

    static void testNullElementRejected() {
        List<Item> src = Arrays.asList(new Item("x", -1L), null);
        boolean thrown = false;
        try {
            new DelayQueue<Item>(src);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null element in source collection must cause NullPointerException");
    }

    public static void main(String[] args) {
        testEmptyCollection();
        testExpiredElementsOrdered();
        testUnexpiredElementsCountedButNotPolled();
        testMixedExpiredAndUnexpired();
        testConstructorCopiesSource();
        testNullElementRejected();
    }
}
