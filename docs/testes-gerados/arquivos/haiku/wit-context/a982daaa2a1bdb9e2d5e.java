/*
 * @test
 * @summary Verifies that DescendingIterator.next() reached through the public descendingIterator() API returns elements in reverse order and throws NoSuchElementException when exhausted
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testEmptyDequeThrows();
        testReverseOrderAndExhaustion();
        testRepeatedNextAfterExhaustionThrows();
    }

    static void testEmptyDequeThrows() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        Iterator<Integer> it = deque.descendingIterator();
        if (it.hasNext()) {
            throw new AssertionError("hasNext() should be false for empty deque");
        }
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("next() on empty descending iterator must throw NoSuchElementException");
        }
    }

    static void testReverseOrderAndExhaustion() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        Iterator<Integer> it = deque.descendingIterator();
        check(it.next(), 3);
        check(it.next(), 2);
        check(it.next(), 1);
        if (it.hasNext()) {
            throw new AssertionError("hasNext() should be false after all elements consumed");
        }
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("next() after exhaustion must throw NoSuchElementException");
        }
    }

    static void testRepeatedNextAfterExhaustionThrows() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.addFirst("a");
        Iterator<String> it = deque.descendingIterator();
        check(it.next(), "a");
        for (int i = 0; i < 2; i++) {
            boolean thrown = false;
            try {
                it.next();
            } catch (NoSuchElementException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new AssertionError("repeated next() after exhaustion must throw NoSuchElementException");
            }
        }
    }

    static void check(Object actual, Object expected) {
        if (!expected.equals(actual)) {
            throw new AssertionError("expected " + expected + " but was " + actual);
        }
    }
}
