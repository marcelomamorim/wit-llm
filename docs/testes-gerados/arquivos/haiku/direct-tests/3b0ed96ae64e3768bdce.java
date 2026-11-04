/*
 * @test
 * @summary Verifies DelayQueue iterator next() returns each element once and throws NoSuchElementException when exhausted
 * @run main DelayQueueWitupTest
 */

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.Delayed;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

public class DelayQueueWitupTest {

    static final class Item implements Delayed {
        final int id;

        Item(int id) {
            this.id = id;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(1, TimeUnit.HOURS);
        }

        @Override
        public int compareTo(Delayed o) {
            return Integer.compare(this.id, ((Item) o).id);
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyIteratorNextThrows() {
        DelayQueue<Item> q = new DelayQueue<>();
        Iterator<Item> it = q.iterator();
        check(!it.hasNext(), "empty iterator must not have next");
        try {
            it.next();
            throw new RuntimeException("expected NoSuchElementException");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testIteratorReturnsEachElementOnce() {
        DelayQueue<Item> q = new DelayQueue<>();
        q.add(new Item(1));
        q.add(new Item(2));
        q.add(new Item(3));
        Iterator<Item> it = q.iterator();
        boolean[] seen = new boolean[4];
        int count = 0;
        while (it.hasNext()) {
            Item item = it.next();
            check(item.id >= 1 && item.id <= 3, "unexpected element " + item.id);
            check(!seen[item.id], "element returned twice: " + item.id);
            seen[item.id] = true;
            count++;
        }
        check(count == 3, "expected 3 elements but got " + count);
        check(q.size() == 3, "iteration must not remove elements");
    }

    static void testNextAfterExhaustionThrows() {
        DelayQueue<Item> q = new DelayQueue<>();
        q.add(new Item(7));
        Iterator<Item> it = q.iterator();
        Item first = it.next();
        check(first.id == 7, "expected element 7 but got " + first.id);
        check(!it.hasNext(), "iterator must be exhausted");
        try {
            it.next();
            throw new RuntimeException("expected NoSuchElementException after exhaustion");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    public static void main(String[] args) {
        testEmptyIteratorNextThrows();
        testIteratorReturnsEachElementOnce();
        testNextAfterExhaustionThrows();
    }
}
