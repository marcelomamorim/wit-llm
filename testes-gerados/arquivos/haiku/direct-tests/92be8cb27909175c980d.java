/*
 * @test
 * @summary Verifies observable behavior of DelayQueue.add(E), which delegates to offer(E)
 * @run main DelayQueueWitupTest
 */

import java.util.concurrent.Delayed;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

public class DelayQueueWitupTest {

    static final class Item implements Delayed {
        final String name;
        final long delay;

        Item(String name, long delay) {
            this.name = name;
            this.delay = delay;
        }

        public long getDelay(TimeUnit unit) {
            return unit.convert(delay, TimeUnit.NANOSECONDS);
        }

        public int compareTo(Delayed o) {
            return Long.compare(delay, ((Item) o).delay);
        }

        public String toString() {
            return name;
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testAddReturnsTrueAndIncrementsSize() {
        DelayQueue<Item> q = new DelayQueue<>();
        check(q.size() == 0, "new queue should be empty");
        check(q.add(new Item("a", -1)) == true, "add of expired item should return true");
        check(q.size() == 1, "size should be 1 after first add");
        check(q.add(new Item("b", 1000000000L)) == true, "add of unexpired item should return true");
        check(q.size() == 2, "size should be 2 after second add");
    }

    static void testUnexpiredItemNotPolled() {
        DelayQueue<Item> q = new DelayQueue<>();
        q.add(new Item("future", 1000000000L));
        check(q.poll() == null, "poll should return null when only unexpired items exist");
        check(q.size() == 1, "unexpired item should remain in queue");
    }

    static void testExpiredItemsPolledInDelayOrder() {
        DelayQueue<Item> q = new DelayQueue<>();
        q.add(new Item("b", -5));
        q.add(new Item("a", -10));
        Item first = q.poll();
        check(first != null && first.name.equals("a"), "expected a first, got " + first);
        Item second = q.poll();
        check(second != null && second.name.equals("b"), "expected b second, got " + second);
        check(q.poll() == null, "queue should be empty after polling both");
    }

    static void testExpiredItemPolledBeforeUnexpired() {
        DelayQueue<Item> q = new DelayQueue<>();
        q.add(new Item("future", 1000000000L));
        q.add(new Item("expired", -1));
        Item head = q.poll();
        check(head != null && head.name.equals("expired"), "expired item should be polled, got " + head);
        check(q.poll() == null, "unexpired item must not be polled");
        check(q.size() == 1, "size should be 1 with unexpired item remaining");
    }

    public static void main(String[] args) {
        testAddReturnsTrueAndIncrementsSize();
        testUnexpiredItemNotPolled();
        testExpiredItemsPolledInDelayOrder();
        testExpiredItemPolledBeforeUnexpired();
    }
}
