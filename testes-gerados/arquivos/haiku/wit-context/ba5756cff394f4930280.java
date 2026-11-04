/*
 * @test
 * @summary Checks observable behavior of DelayQueue.offer(E, long, TimeUnit): returns true, enqueues unexpired elements, does not poll them, and rejects null.
 * @run main DelayQueueWitupTest
 */

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueWitupTest {

    static class Item implements Delayed {
        private final String name;
        private final long delayMs;

        Item(String name, long delayMs) {
            this.name = name;
            this.delayMs = delayMs;
        }

        public long getDelay(TimeUnit unit) {
            return unit.convert(delayMs, TimeUnit.MILLISECONDS);
        }

        public int compareTo(Delayed o) {
            long d = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
            return d < 0 ? -1 : (d > 0 ? 1 : 0);
        }

        public String toString() {
            return name;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        DelayQueue<Item> q = new DelayQueue<>();

        Item a = new Item("a", 100000);
        check(q.offer(a, 1, TimeUnit.MILLISECONDS), "offer with timeout should return true");
        check(q.size() == 1, "size should be 1 after first offer");
        check(q.contains(a), "queue should contain offered element");

        Item b = new Item("b", 200000);
        check(q.offer(b, 0, TimeUnit.SECONDS), "offer with zero timeout should return true");
        check(q.size() == 2, "size should be 2 after second offer");
        check(q.contains(b), "queue should contain second offered element");

        check(q.poll() == null, "poll must not return unexpired elements");
        check(q.size() == 2, "poll on unexpired elements must not remove them");

        boolean npe = false;
        try {
            q.offer(null, 1, TimeUnit.MILLISECONDS);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "offer(null, timeout, unit) should throw NullPointerException");
        check(q.size() == 2, "failed null offer must not change size");
    }
}
