/*
 * @test
 * @summary Verifies ConcurrentLinkedDeque iterator next() ordering and NoSuchElementException on exhaustion
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testEmptyIteratorNextThrows();
        testIterationOrder();
        testNextAfterExhaustionThrows();
        testFirstNextReturnsHeadWithoutSkipping();
        testConstructorFromCollectionIteration();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static List<String> drain(Iterator<String> it) {
        List<String> out = new ArrayList<>();
        while (it.hasNext()) {
            out.add(it.next());
        }
        return out;
    }

    static void expectNoSuchElement(Iterator<String> it, String message) {
        try {
            it.next();
        } catch (NoSuchElementException e) {
            return;
        }
        throw new AssertionError(message);
    }

    static void testEmptyIteratorNextThrows() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        Iterator<String> it = deque.iterator();
        check(!it.hasNext(), "empty deque iterator must have no next");
        expectNoSuchElement(it, "next() on empty iterator must throw NoSuchElementException");
    }

    static void testIterationOrder() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.addLast("a");
        deque.addLast("b");
        deque.addLast("c");
        List<String> seen = drain(deque.iterator());
        check(seen.equals(Arrays.asList("a", "b", "c")),
              "iteration order mismatch: " + seen);
    }

    static void testNextAfterExhaustionThrows() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.addLast("only");
        Iterator<String> it = deque.iterator();
        check("only".equals(it.next()), "first next() should return the element");
        check(!it.hasNext(), "iterator should be exhausted");
        expectNoSuchElement(it, "second next() after exhaustion must throw NoSuchElementException");
        expectNoSuchElement(it, "repeated next() after exhaustion must keep throwing");
    }

    static void testFirstNextReturnsHeadWithoutSkipping() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.addLast("b");
        deque.addFirst("a");
        deque.addLast("c");
        Iterator<String> it = deque.iterator();
        check("a".equals(it.next()), "first next() must return head element");
        check("b".equals(it.next()), "second next() must return next element without skipping");
        check("c".equals(it.next()), "third next() must return last element");
        check(!it.hasNext(), "no elements should remain");
    }

    static void testConstructorFromCollectionIteration() {
        ConcurrentLinkedDeque<String> deque =
            new ConcurrentLinkedDeque<>(Arrays.asList("x", "y"));
        List<String> seen = drain(deque.iterator());
        check(seen.equals(Arrays.asList("x", "y")),
              "iteration over collection-constructed deque mismatch: " + seen);
        expectNoSuchElement(deque.iterator(), "fresh iterator after drain must still be valid");
    }
}
