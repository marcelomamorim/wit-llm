/*
 * @test
 * @summary Behavioral checks for DelayQueue.put: expired elements become available via poll, unexpired ones do not, and null is rejected.
 * @run main DelayQueueWitupTest
 */

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
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
            long d = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
            return d < 0 ? -1 : (d > 0 ? 1 : 0);
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

    static void testExpiredElementIsPolled() {
        DelayQueue<Item> q = new DelayQueue<>();
        q.put(new Item("expired", -1000000000L));
        check(q.size() == 1, "size after put expected 1 but was " + q.size());
        Item got = q.poll();
        check(got != null, "poll should return expired element");
        check("expired".equals(got.toString()), "unexpected element: " + got);
        check(q.size() == 0, "queue should be empty after poll");
    }

    static void testUnexpiredElementNotAvailable() {
        DelayQueue<Item> q = new DelayQueue<>();
        q.put(new Item("later", 60000000000L));
        check(q.size() == 1, "size after put expected 1 but was " + q.size());
        check(q.poll() == null, "poll should return null for unexpired element");
        check(q.size() == 1, "unexpired element should remain in queue");
    }

    static void testMostExpiredReturnedFirst() {
        DelayQueue<Item> q = new DelayQueue<>();
        q.put(new Item("newer", -1000000000L));
        q.put(new Item("older", -2000000000L));
        check(q.size() == 2, "size expected 2 but was " + q.size());
        Item first = q.poll();
        check(first != null && "older".equals(first.toString()),
              "expected most expired element first but was " + first);
    }

    static void testNullRejected() {
        DelayQueue<Item> q = new DelayQueue<>();
        boolean thrown = false;
        try {
            q.put(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "put(null) should throw NullPointerException");
        check(q.size() == 0, "queue should remain empty after rejected null");
    }

    public static void main(String[] args) {
        testExpiredElementIsPolled();
        testUnexpiredElementNotAvailable();
        testMostExpiredReturnedFirst();
        testNullRejected();
    }
}
