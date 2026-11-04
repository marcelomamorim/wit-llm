/* @test    @summary Test DelayQueue constructor with a collection of elements    @run main DelayQueueWitupTest */

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.ArrayList;
import java.util.List;

public class DelayQueueWitupTest {

    public static void main(String[] args) {
        testConstructorWithCollection();
    }

    private static void testConstructorWithCollection() {
        List<Delayed> list = new ArrayList<>();
        list.add(new DummyDelayed(1000));
        list.add(new DummyDelayed(2000));
        list.add(new DummyDelayed(500));
        DelayQueue<Delayed> dq = new DelayQueue<>(list);
        int size = dq.size();
        if (size != 3) {
            throw new AssertionError("Expected size 3, but got " + size);
        }
        Delayed first = dq.peek();
        if (first == null || first.getDelay(java.util.concurrent.TimeUnit.MILLISECONDS) > 0) {
            throw new AssertionError("First element should be expired or about to expire");
        }
    }

    private static class DummyDelayed implements Delayed {
        private final long delayMillis;
        private final long startTime;

        DummyDelayed(long delayMillis) {
            this.delayMillis = delayMillis;
            this.startTime = System.currentTimeMillis();
        }

        @Override
        public long getDelay(java.util.concurrent.TimeUnit unit) {
            long elapsed = System.currentTimeMillis() - startTime;
            long remaining = delayMillis - elapsed;
            return unit.convert(remaining, java.util.concurrent.TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            if (o == this) return 0;
            long diff = this.getDelay(java.util.concurrent.TimeUnit.MILLISECONDS) - o.getDelay(java.util.concurrent.TimeUnit.MILLISECONDS);
            return (diff < 0) ? -1 : (diff > 0) ? 1 : 0;
        }
    }
}
