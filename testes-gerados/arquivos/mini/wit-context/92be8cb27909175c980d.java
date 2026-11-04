/*
 * @test
 * @summary Test DelayQueue.add(E) throws UnsupportedOperationException as per current implementation
 * @run main DelayQueueWitupTest
 */

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueWitupTest {

    static class SimpleDelayed implements Delayed {
        private final long delayTimeNanos;
        private final long startTime;

        SimpleDelayed(long delay, TimeUnit unit) {
            this.delayTimeNanos = unit.toNanos(delay);
            this.startTime = System.nanoTime();
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long diff = (startTime + delayTimeNanos) - System.nanoTime();
            return unit.convert(diff, TimeUnit.NANOSECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            long diff = this.getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
            return (diff == 0) ? 0 : ((diff < 0) ? -1 : 1);
        }
    }

    public static void main(String[] args) {
        DelayQueue<SimpleDelayed> queue = new DelayQueue<>();
        SimpleDelayed element = new SimpleDelayed(10, TimeUnit.MILLISECONDS);

        boolean thrown = false;
        try {
            queue.add(element);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException from DelayQueue.add(E) but none was thrown");
        }

        // Also test that offer(E) returns false (since add calls offer)
        boolean offerResult = queue.offer(element);
        if (offerResult) {
            throw new RuntimeException("Expected offer(E) to return false as per DelayQueue.add fallback behavior");
        }

        System.out.println("DelayQueue.add(E) test passed: UnsupportedOperationException thrown as expected.");
    }
}
