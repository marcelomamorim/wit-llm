/*
 * @test
 * @summary Verifies observable boolean results of DelayQueue.remove(Object)
 * @run main DelayQueueWitupTest
 */

import java.util.concurrent.Delayed;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

public class DelayQueueWitupTest {

    static final class Item implements Delayed {
        private final long value;

        Item(long value) {
            this.value = value;
        }

        public long getDelay(TimeUnit unit) {
            return unit.convert(1, TimeUnit.HOURS);
        }

        public int compareTo(Delayed other) {
            return Long.compare(value, ((Item) other).value);
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRemoveOnEmptyQueueReturnsFalse() {
        DelayQueue<Item> queue = new DelayQueue<Item>();
        check(!queue.remove(new Item(1)), "remove on empty queue must return false");
        check(queue.isEmpty(), "queue must remain empty");
    }

    static void testRemovePresentElementReturnsTrueOnce() {
        DelayQueue<Item> queue = new DelayQueue<Item>();
        Item a = new Item(1);
        queue.add(a);
        check(queue.remove(a), "remove of present element must return true");
        check(queue.size() == 0, "size must be 0 after removing only element");
        check(!queue.remove(a), "second remove of same element must return false");
    }

    static void testRemoveAbsentElementKeepsOthers() {
        DelayQueue<Item> queue = new DelayQueue<Item>();
        Item a = new Item(1);
        Item b = new Item(2);
        queue.add(a);
        check(!queue.remove(b), "remove of absent element must return false");
        check(queue.size() == 1, "size must stay 1 after absent remove");
        check(queue.remove(a), "present element must still be removable");
    }

    static void testRemoveNullReturnsFalse() {
        DelayQueue<Item> queue = new DelayQueue<Item>();
        Item a = new Item(1);
        queue.add(a);
        check(!queue.remove(null), "remove(null) must return false");
        check(queue.size() == 1, "remove(null) must not alter the queue");
        check(queue.remove(a), "element must remain removable after remove(null)");
    }

    static void testRemoveOnlyMatchingElement() {
        DelayQueue<Item> queue = new DelayQueue<Item>();
        Item a = new Item(1);
        Item b = new Item(2);
        queue.add(a);
        queue.add(b);
        check(queue.remove(a), "remove of first element must return true");
        check(queue.size() == 1, "size must be 1 after removing one of two elements");
        check(queue.remove(b), "remaining element must be removable");
        check(queue.isEmpty(), "queue must be empty at the end");
    }

    public static void main(String[] args) {
        testRemoveOnEmptyQueueReturnsFalse();
        testRemovePresentElementReturnsTrueOnce();
        testRemoveAbsentElementKeepsOthers();
        testRemoveNullReturnsFalse();
        testRemoveOnlyMatchingElement();
    }
}
