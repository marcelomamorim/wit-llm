/*
 * @test
 * @summary Verify DelayQueue.drainTo(Collection) null handling and draining of expired elements
 * @run main DelayQueueWitupTest
 */

import java.util.ArrayList;
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
        DelayQueue<Item> q = new DelayQueue<Item>();
        q.add(new Item("expired", -1L));
        try {
            q.drainTo(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
        check(q.size() == 1, "queue must be unchanged after null argument");
    }

    static void testEmptyQueueReturnsZero() {
        DelayQueue<Item> q = new DelayQueue<Item>();
        List<Item> sink = new ArrayList<Item>();
        int n = q.drainTo(sink);
        check(n == 0, "expected 0 but was " + n);
        check(sink.isEmpty(), "sink must remain empty");
    }

    static void testOnlyUnexpiredElementsAreNotDrained() {
        DelayQueue<Item> q = new DelayQueue<Item>();
        q.add(new Item("future1", TimeUnit.HOURS.toNanos(1)));
        q.add(new Item("future2", TimeUnit.HOURS.toNanos(2)));
        List<Item> sink = new ArrayList<Item>();
        int n = q.drainTo(sink);
        check(n == 0, "expected 0 but was " + n);
        check(sink.isEmpty(), "unexpired elements must not be drained");
        check(q.size() == 2, "queue size must remain 2 but was " + q.size());
    }

    static void testExpiredElementsAreDrainedInOrder() {
        DelayQueue<Item> q = new DelayQueue<Item>();
        q.add(new Item("e-1", -1L));
        q.add(new Item("e-3", -3L));
        q.add(new Item("e-2", -2L));
        q.add(new Item("future", TimeUnit.HOURS.toNanos(1)));
        List<Item> sink = new ArrayList<Item>();
        int n = q.drainTo(sink);
        check(n == 3, "expected 3 drained but was " + n);
        check(sink.size() == 3, "sink size must be 3 but was " + sink.size());
        check(sink.get(0).name.equals("e-3"), "first drained must be e-3 but was " + sink.get(0));
        check(sink.get(1).name.equals("e-2"), "second drained must be e-2 but was " + sink.get(1));
        check(sink.get(2).name.equals("e-1"), "third drained must be e-1 but was " + sink.get(2));
        check(q.size() == 1, "queue must keep 1 unexpired element but size was " + q.size());
        check(q.peek() != null && q.peek().name.equals("future"), "remaining element must be future");
    }

    static void testDrainToAppendsToExistingCollection() {
        DelayQueue<Item> q = new DelayQueue<Item>();
        q.add(new Item("e", -5L));
        List<Item> sink = new ArrayList<Item>();
        sink.add(new Item("pre", TimeUnit.DAYS.toNanos(1)));
        int n = q.drainTo(sink);
        check(n == 1, "expected 1 drained but was " + n);
        check(sink.size() == 2, "sink size must be 2 but was " + sink.size());
        check(sink.get(0).name.equals("pre"), "existing element must be preserved");
        check(sink.get(1).name.equals("e"), "drained element must be appended");
        check(q.isEmpty(), "queue must be empty after draining");
    }

    public static void main(String[] args) {
        testNullCollectionThrowsNPE();
        testEmptyQueueReturnsZero();
        testOnlyUnexpiredElementsAreNotDrained();
        testExpiredElementsAreDrainedInOrder();
        testDrainToAppendsToExistingCollection();
    }
}
