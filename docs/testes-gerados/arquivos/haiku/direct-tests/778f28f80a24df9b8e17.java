/*
 * @test
 * @summary Behavioral checks for DelayQueue.poll(): empty queue, unexpired head, expired head, and expiration order
 * @run main DelayQueueWitupTest
 */
import java.util.concurrent.Delayed;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

public class DelayQueueWitupTest {

    static final class TestDelayed implements Delayed {
        private final long nanos;

        TestDelayed(long nanos) {
            this.nanos = nanos;
        }

        public long getDelay(TimeUnit unit) {
            return unit.convert(nanos, TimeUnit.NANOSECONDS);
        }

        public int compareTo(Delayed o) {
            return Long.compare(nanos, o.getDelay(TimeUnit.NANOSECONDS));
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        DelayQueue<TestDelayed> empty = new DelayQueue<TestDelayed>();
        check(empty.poll() == null, "poll on empty queue must return null");

        DelayQueue<TestDelayed> future = new DelayQueue<TestDelayed>();
        TestDelayed unexpired = new TestDelayed(3600L * 1000000000L);
        future.add(unexpired);
        check(future.poll() == null, "poll must return null when head delay is positive");
        check(future.size() == 1, "unexpired element must remain in queue");

        DelayQueue<TestDelayed> zeroHead = new DelayQueue<TestDelayed>();
        TestDelayed zero = new TestDelayed(0L);
        zeroHead.add(unexpired);
        zeroHead.add(zero);
        check(zeroHead.poll() == zero, "poll must return expired head with delay 0");
        check(zeroHead.poll() == null, "poll must return null when remaining head is unexpired");
        check(zeroHead.size() == 1, "unexpired element must remain after poll");

        DelayQueue<TestDelayed> ordered = new DelayQueue<TestDelayed>();
        TestDelayed e5 = new TestDelayed(-5L);
        TestDelayed e10 = new TestDelayed(-10L);
        TestDelayed e0 = new TestDelayed(0L);
        ordered.add(e5);
        ordered.add(e0);
        ordered.add(e10);
        check(ordered.poll() == e10, "expired element with furthest past expiry must be polled first");
        check(ordered.poll() == e5, "second most expired element must be polled next");
        check(ordered.poll() == e0, "element with delay 0 must be polled next");
        check(ordered.poll() == null, "poll must return null when queue is drained");
        check(ordered.isEmpty(), "queue must be empty after all expired elements are polled");
    }
}
