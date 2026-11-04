/*
 * @test
 * @summary Verifies DelayQueue.remove(Object) removal semantics, including null, absent, and unexpired elements
 * @run main DelayQueueWitupTest
 */

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueWitupTest {

    static final long HOUR = TimeUnit.HOURS.toNanos(1);

    static final class Item implements Delayed {
        private final long delayNanos;
        private final String name;

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

        public String toString() {
            return name;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        DelayQueue<Item> q = new DelayQueue<>();
        Item a = new Item("a", HOUR);
        Item b = new Item("b", 2 * HOUR);
        Item c = new Item("c", 3 * HOUR);
        q.add(a);
        q.add(b);
        q.add(c);

        check(q.size() == 3, "initial size should be 3");
        check(q.remove(b), "remove of present unexpired element should return true");
        check(q.size() == 2, "size should decrease after successful remove");
        check(!q.contains(b), "removed element should no longer be contained");
        check(q.contains(a) && q.contains(c), "other elements should remain");

        check(!q.remove(b), "second remove of same element should return false");
        check(q.size() == 2, "failed remove should not change size");

        check(!q.remove(new Item("a", HOUR)), "remove should use identity-based equals for Item");
        check(q.size() == 2, "remove of non-member should not change size");

        check(!q.remove(null), "remove(null) should return false");
        check(q.size() == 2, "remove(null) should not change size");

        check(q.remove(a), "remove of a should return true");
        check(q.remove(c), "remove of c should return true");
        check(q.isEmpty(), "queue should be empty after removing all elements");
        check(!q.remove(c), "remove on empty queue should return false");

        Item x = new Item("x", HOUR);
        q.add(x);
        check(q.poll() == null, "unexpired element should not be polled");
        check(q.remove(x), "unexpired element should still be removable");
        check(q.isEmpty(), "queue should be empty at end");
    }
}
