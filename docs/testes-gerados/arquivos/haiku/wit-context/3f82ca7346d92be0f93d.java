/*
 * @test
 * @summary Witup-generated tests for the DelayQueue(Collection) constructor
 * @run main DelayQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Delayed;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

public class DelayQueueWitupTest {

    static final class Item implements Delayed {
        private final String name;
        private final long delayNanos;

        Item(String name, long delayNanos) {
            this.name = name;
            this.delayNanos = delayNanos;
        }

        public long getDelay(TimeUnit unit) {
            return unit.convert(delayNanos, TimeUnit.NANOSECONDS);
        }

        public int compareTo(Delayed o) {
            return Long.compare(delayNanos, o.getDelay(TimeUnit.NANOSECONDS));
        }

        public String toString() {
            return name;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullCollectionThrowsNPE() {
        try {
            Collection<Item> nullCollection = null;
            new DelayQueue<Item>(nullCollection);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null collection");
    }

    static void testNullElementThrowsNPE() {
        List<Item> list = Arrays.asList(new Item("a", 1L), null);
        try {
            new DelayQueue<Item>(list);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null element");
    }

    static void testCopiesUnexpiredElements() {
        Item a = new Item("a", 3_000_000_000L);
        Item b = new Item("b", 1_000_000_000L);
        Item c = new Item("c", 2_000_000_000L);
        List<Item> list = new ArrayList<Item>(Arrays.asList(a, b, c));
        DelayQueue<Item> q = new DelayQueue<Item>(list);
        check(q.size() == 3, "size should be 3, was " + q.size());
        check(q.peek() == b, "peek should return the element with the smallest delay");
        check(q.poll() == null, "poll must return null when no delay has expired");
        list.clear();
        check(q.size() == 3, "queue must not depend on the source collection after construction");
    }

    static void testEmptyCollection() {
        DelayQueue<Item> q = new DelayQueue<Item>(new ArrayList<Item>());
        check(q.isEmpty(), "queue built from empty collection must be empty");
        check(q.size() == 0, "size must be 0");
        check(q.peek() == null, "peek must return null on empty queue");
    }

    public static void main(String[] args) {
        testNullCollectionThrowsNPE();
        testNullElementThrowsNPE();
        testCopiesUnexpiredElements();
        testEmptyCollection();
    }
}
