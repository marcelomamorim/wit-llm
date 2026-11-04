/*
 * @test
 * @summary Witup-derived behavioral tests for ArrayBlockingQueue.iterator()
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testIteratorNotNullOnEmptyQueue();
        testIteratorYieldsFifoOrder();
        testNextOnExhaustedIteratorThrowsNoSuchElement();
        testIteratorRemoveDeletesFromQueue();
        System.out.println("PASSED");
    }

    static void testIteratorNotNullOnEmptyQueue() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(4);
        Iterator<Integer> it = q.iterator();
        check(it != null, "iterator() must not return null");
        check(!it.hasNext(), "iterator of empty queue must have no elements");
    }

    static void testIteratorYieldsFifoOrder() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(4);
        q.offer(1);
        q.offer(2);
        q.offer(3);
        List<Integer> seen = new ArrayList<>();
        for (Iterator<Integer> it = q.iterator(); it.hasNext(); ) {
            seen.add(it.next());
        }
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        check(seen.equals(expected), "expected FIFO order " + expected + " but got " + seen);
    }

    static void testNextOnExhaustedIteratorThrowsNoSuchElement() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(2);
        q.offer(7);
        Iterator<Integer> it = q.iterator();
        it.next();
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "next() on exhausted iterator must throw NoSuchElementException");
    }

    static void testIteratorRemoveDeletesFromQueue() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(4);
        q.offer("a");
        q.offer("b");
        q.offer("c");
        Iterator<String> it = q.iterator();
        check("a".equals(it.next()), "first element should be a");
        it.remove();
        check(q.size() == 2, "size should be 2 after iterator remove but was " + q.size());
        check("b".equals(q.peek()), "head should be b after removing a");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
