/*
 * @test
 * @summary Verifies DelayQueue.peek() returns the head element without removing it, or null when empty
 * @run main DelayQueueWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Delayed;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

public class DelayQueueWitupTest {

    static final class Item implements Delayed {
        private final long delayNanos;
        final String name;

        Item(String name, long delayNanos) {
            this.name = name;
            this.delayNanos = delayNanos;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(delayNanos, TimeUnit.NANOSECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            return Long.compare(getDelay(TimeUnit.NANOSECONDS),
                                o.getDelay(TimeUnit.NANOSECONDS));
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyQueuePeekReturnsNull() {
        DelayQueue<Item> q = new DelayQueue<>();
        check(q.peek() == null, "peek on empty queue must return null");
        check(q.size() == 0, "size must remain 0 after peek on empty queue");
    }

    static void testPeekDoesNotRemove() {
        DelayQueue<Item> q = new DelayQueue<>();
        Item a = new Item("a", TimeUnit.HOURS.toNanos(1));
        q.add(a);
        check(q.peek() == a, "peek must return the single unexpired element");
        check(q.size() == 1, "peek must not remove the element");
        check(q.peek() == a, "repeated peek must return the same element");
    }

    static void testPeekReturnsSmallestDelay() {
        DelayQueue<Item> q = new DelayQueue<>();
        Item h3 = new Item("3h", TimeUnit.HOURS.toNanos(3));
        Item h1 = new Item("1h", TimeUnit.HOURS.toNanos(1));
        Item h2 = new Item("2h", TimeUnit.HOURS.toNanos(2));
        q.add(h3);
        q.add(h1);
        q.add(h2);
        check(q.peek() == h1, "peek must return the element with the smallest delay");
        check(q.size() == 3, "size must be unchanged by peek");
    }

    static void testPeekReturnsMostExpiredHead() {
        DelayQueue<Item> q = new DelayQueue<>();
        Item minus1 = new Item("-1", -1L);
        Item minus2 = new Item("-2", -2L);
        q.add(minus1);
        q.add(minus2);
        check(q.peek() == minus2, "peek must return the expired element that expired furthest in the past");
    }

    static void testPeekAfterClearReturnsNull() {
        DelayQueue<Item> q = new DelayQueue<>();
        q.add(new Item("x", TimeUnit.HOURS.toNanos(1)));
        q.clear();
        check(q.peek() == null, "peek must return null after clear");
    }

    static void testPeekFromCollectionConstructor() {
        List<Item> src = new ArrayList<>();
        Item far = new Item("far", TimeUnit.HOURS.toNanos(5));
        Item near = new Item("near", TimeUnit.HOURS.toNanos(1));
        src.add(far);
        src.add(near);
        DelayQueue<Item> q = new DelayQueue<>(src);
        check(q.peek() == near, "peek on queue built from collection must return smallest delay");
        check(q.size() == 2, "size must match collection size");
    }

    public static void main(String[] args) {
        testEmptyQueuePeekReturnsNull();
        testPeekDoesNotRemove();
        testPeekReturnsSmallestDelay();
        testPeekReturnsMostExpiredHead();
        testPeekAfterClearReturnsNull();
        testPeekFromCollectionConstructor();
    }
}
