/*
 * @test
 * @summary Verifies DelayQueue.drainTo(Collection) transfers only expired elements, in expiration order, and returns the transferred count
 * @run main DelayQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Delayed;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

public class DelayQueueWitupTest {

    static class TestDelayed implements Delayed {
        final long delayNanos;
        final String name;

        TestDelayed(String name, long delayNanos) {
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

    static final long UNEXPIRED = TimeUnit.DAYS.toNanos(1);

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyQueue() {
        DelayQueue<TestDelayed> q = new DelayQueue<>();
        Collection<Delayed> c = new ArrayList<>();
        int n = q.drainTo(c);
        check(n == 0, "expected 0 but was " + n);
        check(c.isEmpty(), "target should remain empty");
        check(q.isEmpty(), "queue should remain empty");
    }

    static void testAllExpiredInExpirationOrder() {
        DelayQueue<TestDelayed> q = new DelayQueue<>();
        q.add(new TestDelayed("b", -2L));
        q.add(new TestDelayed("c", -1L));
        q.add(new TestDelayed("a", -3L));
        Collection<Delayed> c = new ArrayList<>();
        int n = q.drainTo(c);
        check(n == 3, "expected 3 but was " + n);
        check(c.size() == 3, "target size expected 3 but was " + c.size());
        check(q.isEmpty(), "queue should be empty after draining expired elements");
        Object[] arr = c.toArray();
        check(((TestDelayed) arr[0]).name.equals("a"), "first element should be a");
        check(((TestDelayed) arr[1]).name.equals("b"), "second element should be b");
        check(((TestDelayed) arr[2]).name.equals("c"), "third element should be c");
    }

    static void testMixedExpiredAndUnexpired() {
        DelayQueue<TestDelayed> q = new DelayQueue<>();
        q.add(new TestDelayed("expired1", -5L));
        q.add(new TestDelayed("future", UNEXPIRED));
        q.add(new TestDelayed("expired2", 0L));
        Collection<Delayed> c = new ArrayList<>();
        int n = q.drainTo(c);
        check(n == 2, "expected 2 but was " + n);
        check(c.size() == 2, "target size expected 2 but was " + c.size());
        check(q.size() == 1, "unexpired element should remain, size was " + q.size());
        for (Delayed d : c) {
            check(d.getDelay(TimeUnit.NANOSECONDS) <= 0,
                  "drained element must be expired");
        }
    }

    static void testNoExpiredElements() {
        DelayQueue<TestDelayed> q = new DelayQueue<>();
        q.add(new TestDelayed("f1", UNEXPIRED));
        q.add(new TestDelayed("f2", UNEXPIRED));
        Collection<Delayed> c = new ArrayList<>();
        int n = q.drainTo(c);
        check(n == 0, "expected 0 but was " + n);
        check(c.isEmpty(), "target should remain empty");
        check(q.size() == 2, "queue size expected 2 but was " + q.size());
    }

    static void testAppendsToExistingCollection() {
        DelayQueue<TestDelayed> q = new DelayQueue<>();
        q.add(new TestDelayed("x", -1L));
        Collection<Delayed> c = new ArrayList<>();
        TestDelayed pre = new TestDelayed("pre", UNEXPIRED);
        c.add(pre);
        int n = q.drainTo(c);
        check(n == 1, "expected 1 but was " + n);
        check(c.size() == 2, "target size expected 2 but was " + c.size());
        check(c.iterator().next() == pre, "pre-existing element should stay first");
    }

    public static void main(String[] args) throws Exception {
        testEmptyQueue();
        testAllExpiredInExpirationOrder();
        testMixedExpiredAndUnexpired();
        testNoExpiredElements();
        testAppendsToExistingCollection();
    }
}
