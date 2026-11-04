/*
 * @test
 * @summary Verify ArrayDeque iterator next() returns elements in order and throws NoSuchElementException when exhausted
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testIterationOrder();
        testEmptyDequeThrows();
        testExhaustedIteratorThrowsRepeatedly();
        testIterationAfterWrapAround();
    }

    static void testIterationOrder() {
        ArrayDeque<String> deque = new ArrayDeque<>(Arrays.asList("a", "b", "c"));
        Iterator<String> it = deque.iterator();
        check(it.hasNext(), "hasNext should be true initially");
        check("a".equals(it.next()), "first next() should return a");
        check("b".equals(it.next()), "second next() should return b");
        check("c".equals(it.next()), "third next() should return c");
        check(!it.hasNext(), "hasNext should be false after all elements");
    }

    static void testEmptyDequeThrows() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        Iterator<Integer> it = deque.iterator();
        expectNoSuchElement(it, "next() on empty deque iterator");
    }

    static void testExhaustedIteratorThrowsRepeatedly() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(1);
        Iterator<Integer> it = deque.iterator();
        check(it.next() == 1, "next() should return 1");
        expectNoSuchElement(it, "first next() past end");
        expectNoSuchElement(it, "second next() past end");
    }

    static void testIterationAfterWrapAround() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        for (int i = 1; i <= 10; i++) {
            deque.addLast(i);
        }
        for (int i = 0; i < 5; i++) {
            deque.pollFirst();
        }
        for (int i = 11; i <= 15; i++) {
            deque.addLast(i);
        }
        Iterator<Integer> it = deque.iterator();
        for (int expected = 6; expected <= 15; expected++) {
            Integer actual = it.next();
            check(actual == expected, "expected " + expected + " but got " + actual);
        }
        check(!it.hasNext(), "hasNext should be false after wrap-around iteration");
    }

    static void expectNoSuchElement(Iterator<?> it, String context) {
        try {
            it.next();
        } catch (NoSuchElementException expected) {
            return;
        }
        throw new AssertionError(context + " should throw NoSuchElementException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
