/*
 * @test    @summary Test DelayQueue add method for basic functionality
 * @run main DelayQueueWitupTest
 */

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueWitupTest {

    static class DummyDelayed implements Delayed {
        private final long startTime;
        private final long delay;

        DummyDelayed(long delay, TimeUnit unit) {
            this.startTime = System.nanoTime();
            this.delay = unit.toNanos(delay);
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long elapsed = System.nanoTime() - startTime;
            long remaining = delay - elapsed;
            return unit.convert(remaining, TimeUnit.NANOSECONDS);
        }

        @Override
        public int compareTo(Delayed other) {
            if (other == this) return 0;
            if (other instanceof DummyDelayed) {
                return Long.compare(this.getDelay(TimeUnit.NANOSECONDS),
                                    other.getDelay(TimeUnit.NANOSECONDS));
            }
            return 0;
        }
    }

    public static void main(String[] args) {
        DelayQueue<Delayed> dq = new DelayQueue<>();
        DummyDelayed item = new DummyDelayed(100, TimeUnit.MILLISECONDS);
        boolean added = dq.add(item);
        assert added : "Item should be added successfully";
        assert dq.size() == 1 : "Queue size should be 1 after adding an element";
        Delayed polled = dq.poll();
        assert polled == item : "Polled item should be the same as added";
    }
}
