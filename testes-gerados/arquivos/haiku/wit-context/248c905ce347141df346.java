/*
 * @test
 * @summary Witup-derived checks for ArrayBlockingQueue iterator next(): NoSuchElementException when exhausted and ordered traversal
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testNextOnEmptyQueueThrows();
        testNextOnExhaustedIteratorThrows();
        testIterationReturnsElementsInFifoOrder();
        testIterationOnFairQueueReturnsElementsInFifoOrder();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNextOnEmptyQueueThrows() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        Iterator<String> it = queue.iterator();
        check(!it.hasNext(), "empty queue iterator should have no next");
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException expected) {
            thrown = true;
        }
        check(thrown, "next() on empty queue iterator must throw NoSuchElementException");
    }

    static void testNextOnExhaustedIteratorThrows() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);
        queue.add(1);
        Iterator<Integer> it = queue.iterator();
        check(it.next() == 1, "first element should be 1");
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException expected) {
            thrown = true;
        }
        check(thrown, "next() after exhaustion must throw NoSuchElementException");
    }

    static void testIterationReturnsElementsInFifoOrder() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(4);
        for (int i = 1; i <= 4; i++) {
            queue.add(i * 10);
        }
        Iterator<Integer> it = queue.iterator();
        for (int i = 1; i <= 4; i++) {
            check(it.hasNext(), "iterator should have element " + i);
            int value = it.next();
            check(value == i * 10, "expected " + (i * 10) + " but got " + value);
        }
        check(!it.hasNext(), "iterator should be exhausted");
    }

    static void testIterationOnFairQueueReturnsElementsInFifoOrder() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3, true);
        queue.add("a");
        queue.add("b");
        queue.add("c");
        Iterator<String> it = queue.iterator();
        check("a".equals(it.next()), "first element should be a");
        check("b".equals(it.next()), "second element should be b");
        check("c".equals(it.next()), "third element should be c");
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException expected) {
            thrown = true;
        }
        check(thrown, "next() after last element must throw NoSuchElementException");
    }
}
