/*
 * @test
 * @summary Test DelayQueue.drainTo(Collection) for NPE on null and basic behavior
 * @run main DelayQueueWitupTest
 */

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

public class DelayQueueWitupTest {

    static class SimpleDelayed implements Delayed {
        private final long delayTimeNanos;
        private final long startTime;

        SimpleDelayed(long delay, TimeUnit unit) {
            this.startTime = System.nanoTime();
            this.delayTimeNanos = unit.toNanos(delay);
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long diff = (startTime + delayTimeNanos) - System.nanoTime();
            return unit.convert(diff, TimeUnit.NANOSECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            if (o == this) return 0;
            long d = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
            return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
        }
    }

    public static void main(String[] args) {
        testDrainToNullCollection();
        testDrainToBasicBehavior();
    }

    private static void testDrainToNullCollection() {
        DelayQueue<SimpleDelayed> dq = new DelayQueue<>();
        try {
            dq.drainTo(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testDrainToBasicBehavior() {
        DelayQueue<SimpleDelayed> dq = new DelayQueue<>();
        List<SimpleDelayed> target = new ArrayList<>();

        SimpleDelayed expired = new SimpleDelayed(0, TimeUnit.NANOSECONDS); // expired immediately
        SimpleDelayed notExpired = new SimpleDelayed(10, TimeUnit.SECONDS); // not expired

        dq.offer(expired);
        dq.offer(notExpired);

        int drained = dq.drainTo(target);

        if (drained != 2) {
            throw new RuntimeException("drainTo should drain all elements regardless of delay, expected 2 but got " + drained);
        }

        if (target.size() != 2) {
            throw new RuntimeException("Target collection size mismatch after drainTo");
        }

        if (!target.contains(expired) || !target.contains(notExpired)) {
            throw new RuntimeException("Target collection missing expected elements after drainTo");
        }

        if (!dq.isEmpty()) {
            throw new RuntimeException("DelayQueue should be empty after drainTo");
        }
    }
}
