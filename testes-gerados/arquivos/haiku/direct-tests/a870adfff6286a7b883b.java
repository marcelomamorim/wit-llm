/*
 * @test
 * @summary Verifies that DelayQueue.put(E) inserts elements that become visible to poll/peek only after their delay expires
 * @run main DelayQueueWitupTest
 */

import java.util.concurrent.Delayed;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

public class DelayQueueWitupTest {

    static final class Item implements Delayed {
        final String name;
        final long delayNanos;

        Item(String name, long delayNanos) {
            this.name = name;
            this.delayNanos = delayNanos;
        }

        public long getDelay(TimeUnit unit) {
            return unit.convert(delayNanos, TimeUnit.NANOSECONDS);
        }

        public int compareTo(Delayed o) {
            return Long.compare(delayNanos, ((Item) o).delayNanos);
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        DelayQueue<Item> q = new DelayQueue<>();

        Item expired = new Item("expired", 0L);
        q.put(expired);
        check(q.size() == 1, "size after first put should be 1");
        check(q.peek() == expired, "expired element put should be visible via peek");

        Item future = new Item("future", TimeUnit.HOURS.toNanos(1));
        q.put(future);
        check(q.size() == 2, "size after second put should be 2");
        check(q.contains(future), "unexpired element put should be contained");

        check(q.poll() == expired, "poll should return the expired element");
        check(q.poll() == null, "poll should not return an unexpired element");
        check(q.size() == 1, "unexpired element should remain in queue");

        Item earlier = new Item("earlier", -5L);
        q.put(earlier);
        check(q.poll() == earlier, "element with earliest expiration should be returned first");
        check(q.size() == 1, "only the unexpired element should remain");
        check(q.peek() == null, "peek should not return an unexpired element");
    }
}
