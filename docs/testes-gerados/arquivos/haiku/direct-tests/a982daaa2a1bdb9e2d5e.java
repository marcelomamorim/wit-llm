/*
 * @test
 * @summary Verifies ArrayDeque iterator next() ordering, wrap-around traversal and NoSuchElementException when exhausted
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testForwardIteration();
        testDescendingIteration();
        testEmptyDequeNextThrows();
        testExhaustedIteratorNextThrows();
        testWrapAroundIteration();
    }

    static void testForwardIteration() {
        ArrayDeque<String> d = new ArrayDeque<>();
        d.addLast("a");
        d.addLast("b");
        d.addLast("c");
        Iterator<String> it = d.iterator();
        check("a".equals(it.next()), "forward first");
        check("b".equals(it.next()), "forward second");
        check("c".equals(it.next()), "forward third");
        check(!it.hasNext(), "forward hasNext after end");
    }

    static void testDescendingIteration() {
        ArrayDeque<String> d = new ArrayDeque<>();
        d.addLast("a");
        d.addLast("b");
        d.addLast("c");
        Iterator<String> it = d.descendingIterator();
        check("c".equals(it.next()), "descending first");
        check("b".equals(it.next()), "descending second");
        check("a".equals(it.next()), "descending third");
        check(!it.hasNext(), "descending hasNext after end");
    }

    static void testEmptyDequeNextThrows() {
        ArrayDeque<String> d = new ArrayDeque<>();
        expectNoSuchElement(d.iterator(), "empty forward");
        expectNoSuchElement(d.descendingIterator(), "empty descending");
    }

    static void testExhaustedIteratorNextThrows() {
        ArrayDeque<Integer> d = new ArrayDeque<>();
        d.addLast(1);
        Iterator<Integer> it = d.iterator();
        check(it.next() == 1, "single element");
        expectNoSuchElement(it, "exhausted iterator");
    }

    static void testWrapAroundIteration() {
        ArrayDeque<Integer> d = new ArrayDeque<>();
        for (int i = 0; i < 20; i++) {
            d.addLast(i);
        }
        for (int i = 0; i < 10; i++) {
            d.pollFirst();
        }
        for (int i = 20; i < 30; i++) {
            d.addLast(i);
        }
        Iterator<Integer> it = d.iterator();
        for (int expected = 10; expected < 30; expected++) {
            check(it.hasNext(), "wrap hasNext at " + expected);
            check(it.next() == expected, "wrap order at " + expected);
        }
        check(!it.hasNext(), "wrap hasNext after end");
        expectNoSuchElement(it, "wrap exhausted");
    }

    static void expectNoSuchElement(Iterator<?> it, String msg) {
        try {
            it.next();
        } catch (NoSuchElementException e) {
            return;
        }
        throw new RuntimeException("Expected NoSuchElementException: " + msg);
    }

    static void check(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + msg);
        }
    }
}
