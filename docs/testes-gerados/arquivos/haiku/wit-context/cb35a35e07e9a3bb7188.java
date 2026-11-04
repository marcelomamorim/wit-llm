/*
 * @test
 * @summary Verifies PriorityQueue iterator next() behavior: ConcurrentModificationException after structural modification, NoSuchElementException when exhausted or empty, and normal traversal.
 * @run main PriorityQueueWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class PriorityQueueWitupTest {

    public static void main(String[] args) {
        testCmeAfterAdd();
        testCmeAfterRemove();
        testNoSuchElementOnEmptyQueue();
        testNoSuchElementOnExhaustedIterator();
        testNextReturnsAllElements();
    }

    static void testCmeAfterAdd() {
        PriorityQueue<Integer> q = new PriorityQueue<>();
        q.add(1);
        q.add(2);
        Iterator<Integer> it = q.iterator();
        q.add(3);
        boolean thrown = false;
        try {
            it.next();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected ConcurrentModificationException after add");
        }
    }

    static void testCmeAfterRemove() {
        PriorityQueue<Integer> q = new PriorityQueue<>();
        q.add(1);
        q.add(2);
        Iterator<Integer> it = q.iterator();
        q.remove(Integer.valueOf(2));
        boolean thrown = false;
        try {
            it.next();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected ConcurrentModificationException after remove");
        }
    }

    static void testNoSuchElementOnEmptyQueue() {
        PriorityQueue<Integer> q = new PriorityQueue<>();
        Iterator<Integer> it = q.iterator();
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NoSuchElementException on empty queue");
        }
    }

    static void testNoSuchElementOnExhaustedIterator() {
        PriorityQueue<Integer> q = new PriorityQueue<>();
        q.add(42);
        Iterator<Integer> it = q.iterator();
        Integer first = it.next();
        if (first.intValue() != 42) {
            throw new RuntimeException("Unexpected first element: " + first);
        }
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NoSuchElementException on exhausted iterator");
        }
    }

    static void testNextReturnsAllElements() {
        PriorityQueue<Integer> q = new PriorityQueue<>();
        q.add(5);
        q.add(1);
        q.add(3);
        Iterator<Integer> it = q.iterator();
        int count = 0;
        int sum = 0;
        while (it.hasNext()) {
            sum += it.next().intValue();
            count++;
        }
        if (count != 3 || sum != 9) {
            throw new RuntimeException("Unexpected traversal count=" + count + " sum=" + sum);
        }
    }
}
