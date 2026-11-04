/*
 * @test
 * @summary Checks observable behavior of ArrayBlockingQueue.iterator(): empty iteration, FIFO order, remove via iterator, and NoSuchElementException when exhausted
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testEmptyQueueIteratorHasNoNext();
        testIterationFollowsFifoOrder();
        testIteratorRemoveRemovesLastReturnedElement();
        testNextOnExhaustedIteratorThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyQueueIteratorHasNoNext() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        Iterator<String> it = queue.iterator();
        check(!it.hasNext(), "iterator of empty queue must not have next");
    }

    static void testIterationFollowsFifoOrder() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(4);
        queue.add(1);
        queue.add(2);
        queue.add(3);
        Iterator<Integer> it = queue.iterator();
        List<Integer> seen = new ArrayList<>();
        while (it.hasNext()) {
            seen.add(it.next());
        }
        check(seen.size() == 3, "expected 3 elements, got " + seen.size());
        check(seen.get(0).intValue() == 1, "first element must be 1");
        check(seen.get(1).intValue() == 2, "second element must be 2");
        check(seen.get(2).intValue() == 3, "third element must be 3");
    }

    static void testIteratorRemoveRemovesLastReturnedElement() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        queue.add("a");
        queue.add("b");
        queue.add("c");
        Iterator<String> it = queue.iterator();
        it.next();
        String removed = it.next();
        check("b".equals(removed), "second element must be b");
        it.remove();
        check(queue.size() == 2, "size must be 2 after iterator remove");
        check(!queue.contains("b"), "queue must not contain removed element");
        check(queue.contains("a") && queue.contains("c"), "other elements must remain");
        Iterator<String> again = queue.iterator();
        check("a".equals(again.next()), "after removal first must be a");
        check("c".equals(again.next()), "after removal second must be c");
        check(!again.hasNext(), "no more elements expected");
    }

    static void testNextOnExhaustedIteratorThrows() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
        queue.add("x");
        Iterator<String> it = queue.iterator();
        check("x".equals(it.next()), "first element must be x");
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "next() on exhausted iterator must throw NoSuchElementException");
    }
}
