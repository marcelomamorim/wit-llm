/*
 * @test
 * @summary DelayQueue iterator next() must throw NoSuchElementException when exhausted
 * @run main DelayQueueWitupTest
 */

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.Delayed;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

public class DelayQueueWitupTest {

    static final class Item implements Delayed {
        final long id;

        Item(long id) {
            this.id = id;
        }

        public long getDelay(TimeUnit unit) {
            return 0L;
        }

        public int compareTo(Delayed o) {
            return Long.compare(id, ((Item) o).id);
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void expectNoSuchElement(Iterator<?> it) {
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "next() did not throw NoSuchElementException");
    }

    public static void main(String[] args) {
        DelayQueue<Item> empty = new DelayQueue<>();
        Iterator<Item> it0 = empty.iterator();
        check(!it0.hasNext(), "empty queue iterator should have no next");
        expectNoSuchElement(it0);
        expectNoSuchElement(it0);

        DelayQueue<Item> one = new DelayQueue<>();
        Item a = new Item(1);
        one.add(a);
        Iterator<Item> it1 = one.iterator();
        check(it1.hasNext(), "single element iterator should have next");
        check(it1.next() == a, "next() should return the single element");
        check(!it1.hasNext(), "iterator should be exhausted after one element");
        expectNoSuchElement(it1);

        DelayQueue<Item> two = new DelayQueue<>();
        Item b = new Item(2);
        Item c = new Item(3);
        two.add(b);
        two.add(c);
        Iterator<Item> it2 = two.iterator();
        Item x = it2.next();
        Item y = it2.next();
        check(x != y, "iterator returned the same element twice");
        check((x == b && y == c) || (x == c && y == b),
              "iterator did not return both queued elements");
        check(!it2.hasNext(), "iterator should be exhausted after two elements");
        expectNoSuchElement(it2);
    }
}
