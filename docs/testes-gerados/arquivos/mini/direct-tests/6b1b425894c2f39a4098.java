/*
 * @test
 * @summary Test DelayQueue.drainTo(Collection) method behavior
 * @run main DelayQueueWitupTest
 */

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.Collection;
import java.util.ArrayList;

public class DelayQueueWitupTest {

    static class TestDelayed implements Delayed {
        private final long delayNanos;
        private final long startTime;
        private final String name;

        TestDelayed(long delay, TimeUnit unit, String name) {
            this.delayNanos = unit.toNanos(delay);
            this.startTime = System.nanoTime();
            this.name = name;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long diff = (startTime + delayNanos) - System.nanoTime();
            return unit.convert(diff, TimeUnit.NANOSECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            if (o == this) return 0;
            long d = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
            return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
        }

        @Override
        public String toString() {
            return "TestDelayed(" + name + ")";
        }
    }

    public static void main(String[] args) throws Exception {
        DelayQueue<TestDelayed> queue = new DelayQueue<>();

        // Add elements with expired and unexpired delays
        TestDelayed expired = new TestDelayed(0, TimeUnit.NANOSECONDS, "expired");
        TestDelayed unexpired = new TestDelayed(10, TimeUnit.SECONDS, "unexpired");

        queue.put(expired);
        queue.put(unexpired);

        // Drain to a collection
        Collection<TestDelayed> drained = new ArrayList<>();
        int count = queue.drainTo(drained);

        // The drainTo(Collection) should drain all available elements (both expired and unexpired)
        if (count != 2) {
            throw new AssertionError("Expected drainTo to remove 2 elements, but got " + count);
        }

        if (!drained.contains(expired) || !drained.contains(unexpired)) {
            throw new AssertionError("Drained collection does not contain expected elements");
        }

        // After drainTo, queue should be empty
        if (!queue.isEmpty()) {
            throw new AssertionError("Queue should be empty after drainTo");
        }

        // Test drainTo with empty queue returns 0
        int drainedEmpty = queue.drainTo(new ArrayList<>());
        if (drainedEmpty != 0) {
            throw new AssertionError("Expected drainTo on empty queue to return 0, got " + drainedEmpty);
        }

        System.out.println("DelayQueue.drainTo(Collection) tests passed.");
    }
}
