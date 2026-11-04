/*
 * @test
 * @summary Verify NoSuchElementException from ConcurrentLinkedDeque iterator next() when no element remains
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testEmptyIteratorNext();
        testEmptyDescendingIteratorNext();
        testExhaustedIteratorNextRepeated();
        testExhaustedDescendingIteratorNextRepeated();
        testIteratorOrderThenExhaustion();
        testDescendingIteratorOrderThenExhaustion();
    }

    static void testEmptyIteratorNext() {
        ConcurrentLinkedDeque<String> d = new ConcurrentLinkedDeque<>();
        Iterator<String> it = d.iterator();
        expectNoSuchElement(it);
    }

    static void testEmptyDescendingIteratorNext() {
        ConcurrentLinkedDeque<String> d = new ConcurrentLinkedDeque<>();
        Iterator<String> it = d.descendingIterator();
        expectNoSuchElement(it);
    }

    static void testExhaustedIteratorNextRepeated() {
        ConcurrentLinkedDeque<String> d = new ConcurrentLinkedDeque<>();
        d.add("a");
        Iterator<String> it = d.iterator();
        check("a".equals(it.next()), "first element");
        expectNoSuchElement(it);
        expectNoSuchElement(it);
    }

    static void testExhaustedDescendingIteratorNextRepeated() {
        ConcurrentLinkedDeque<String> d = new ConcurrentLinkedDeque<>();
        d.add("a");
        Iterator<String> it = d.descendingIterator();
        check("a".equals(it.next()), "first descending element");
        expectNoSuchElement(it);
        expectNoSuchElement(it);
    }

    static void testIteratorOrderThenExhaustion() {
        ConcurrentLinkedDeque<String> d = new ConcurrentLinkedDeque<>();
        d.add("a");
        d.add("b");
        Iterator<String> it = d.iterator();
        check("a".equals(it.next()), "iterator element 1");
        check("b".equals(it.next()), "iterator element 2");
        expectNoSuchElement(it);
    }

    static void testDescendingIteratorOrderThenExhaustion() {
        ConcurrentLinkedDeque<String> d = new ConcurrentLinkedDeque<>();
        d.add("a");
        d.add("b");
        Iterator<String> it = d.descendingIterator();
        check("b".equals(it.next()), "descending element 1");
        check("a".equals(it.next()), "descending element 2");
        expectNoSuchElement(it);
    }

    static void expectNoSuchElement(Iterator<?> it) {
        try {
            it.next();
        } catch (NoSuchElementException e) {
            return;
        }
        throw new RuntimeException("expected NoSuchElementException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
