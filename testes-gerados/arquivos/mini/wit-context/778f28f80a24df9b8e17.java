/*
 * @test
 * @summary Deterministic tests for java.util.concurrent.DelayQueue.poll()
 * @run main DelayQueueWitupTest
 */

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueWitupTest {

    static class TestDelayed implements Delayed {
        private final long delayNanos;
        private final long startTime;

        TestDelayed(long delay, TimeUnit unit) {
            this.delayNanos = unit.toNanos(delay);
            this.startTime = System.nanoTime();
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long diff = (startTime + delayNanos) - System.nanoTime();
            return unit.convert(diff, TimeUnit.NANOSECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            if (o == this) return 0;
            long diff = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
            return (diff == 0) ? 0 : ((diff < 0) ? -1 : 1);
        }
    }

    public static void main(String[] args) throws Exception {
        testPollReturnsNullWhenEmpty();
        testPollReturnsNullWhenHeadNotExpired();
        testPollReturnsElementWhenExpired();
    }

    private static void testPollReturnsNullWhenEmpty() {
        DelayQueue<TestDelayed> dq = new DelayQueue<>();
        // Queue is empty, poll() must return null
        TestDelayed result = dq.poll();
        if (result != null) {
            throw new AssertionError("poll() on empty queue should return null");
        }
    }

    private static void testPollReturnsNullWhenHeadNotExpired() {
        DelayQueue<TestDelayed> dq = new DelayQueue<>();
        // Add element with positive delay (not expired)
        TestDelayed delayed = new TestDelayed(10, TimeUnit.SECONDS);
        dq.offer(delayed);
        // poll() should return null because head delay > 0
        TestDelayed result = dq.poll();
        if (result != null) {
            throw new AssertionError("poll() should return null when head delay > 0");
        }
    }

    private static void testPollReturnsElementWhenExpired() throws InterruptedException {
        DelayQueue<TestDelayed> dq = new DelayQueue<>();
        // Add element with zero delay (already expired)
        TestDelayed delayed = new TestDelayed(0, TimeUnit.NANOSECONDS);
        dq.offer(delayed);
        // Wait a short time to ensure delay is expired
        Thread.sleep(1);
        TestDelayed result = dq.poll();
        if (result == null) {
            throw new AssertionError("poll() should return element when delay <= 0");
        }
        if (result != delayed) {
            throw new AssertionError("poll() returned unexpected element");
        }
    }
}
