/*
 * @test
 * @summary Test DelayQueue.put(E) throws UnsupportedOperationException as expected
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
            if (o == this) return 0;
            long diff = this.getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
            return (diff == 0) ? 0 : ((diff < 0) ? -1 : 1);
        }
    }

    public static void main(String[] args) {
        DelayQueue<SimpleDelayed> queue = new DelayQueue<>();
        SimpleDelayed element = new SimpleDelayed(10, TimeUnit.MILLISECONDS);

        boolean unsupportedThrown = false;
        try {
            queue.put(element);
        } catch (UnsupportedOperationException ex) {
            unsupportedThrown = true;
        } catch (Throwable t) {
            throw new RuntimeException("Unexpected exception type", t);
        }

        if (!unsupportedThrown) {
            throw new RuntimeException("Expected UnsupportedOperationException was not thrown by put()");
        }
    }
}
