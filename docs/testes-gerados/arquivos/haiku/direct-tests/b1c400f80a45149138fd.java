/*
 * @test
 * @summary Verify LinkedBlockingQueue.iterator() traversal order, independence of iterators, remove and exhaustion behavior
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyQueueIterator() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        Iterator<Integer> it = q.iterator();
        check(it != null, "iterator() must not return null");
        check(!it.hasNext(), "iterator of empty queue must have no next element");
    }

    static void testIterationOrderIsFifo() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.add(1);
        q.add(2);
        q.add(3);
        Iterator<Integer> it = q.iterator();
        List<Integer> seen = new ArrayList<>();
        while (it.hasNext()) {
            seen.add(it.next());
        }
        check(seen.equals(List.of(1, 2, 3)), "expected FIFO order [1, 2, 3] but got " + seen);
    }

    static void testIteratorsAreIndependent() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        q.add("a");
        q.add("b");
        Iterator<String> first = q.iterator();
        Iterator<String> second = q.iterator();
        check(first != second, "iterator() must return a new iterator on each call");
        check("a".equals(first.next()), "first iterator should start at head");
        check("a".equals(second.next()), "second iterator should start at head independently");
        check("b".equals(first.next()), "first iterator should advance to b");
        check("b".equals(second.next()), "second iterator should advance to b independently");
    }

    static void testIteratorRemoveDeletesFromQueue() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.add(10);
        q.add(20);
        q.add(30);
        Iterator<Integer> it = q.iterator();
        check(it.next() == 10, "first element should be 10");
        it.remove();
        check(q.size() == 2, "size should be 2 after iterator remove but was " + q.size());
        check(q.peek() == 20, "head should be 20 after removing 10 but was " + q.peek());
        List<Integer> rest = new ArrayList<>();
        Iterator<Integer> it2 = q.iterator();
        while (it2.hasNext()) {
            rest.add(it2.next());
        }
        check(rest.equals(List.of(20, 30)), "remaining elements should be [20, 30] but were " + rest);
    }

    static void testNextPastEndThrows() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.add(5);
        Iterator<Integer> it = q.iterator();
        check(it.next() == 5, "first element should be 5");
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "next() past the last element must throw NoSuchElementException");
    }

    public static void main(String[] args) {
        testEmptyQueueIterator();
        testIterationOrderIsFifo();
        testIteratorsAreIndependent();
        testIteratorRemoveDeletesFromQueue();
        testNextPastEndThrows();
    }
}
