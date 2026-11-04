/*
 * @test
 * @summary Test PriorityQueue.Itr.next() for expected exceptions and normal behavior
 * @run main PriorityQueueWitupTest
 */

import java.util.PriorityQueue;
import java.util.Iterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

public class PriorityQueueWitupTest {
    public static void main(String[] args) {
        testNextNormalBehavior();
        testNextConcurrentModificationException();
        testNextNoSuchElementException();
    }

    static void testNextNormalBehavior() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        pq.add(2);
        Iterator<Integer> it = pq.iterator();
        // next() should return elements until exhausted
        int count = 0;
        while (it.hasNext()) {
            Integer val = it.next();
            if (val == null) {
                throw new AssertionError("next() returned null");
            }
            count++;
        }
        if (count != 2) {
            throw new AssertionError("Iterator did not return all elements");
        }
    }

    static void testNextConcurrentModificationException() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        Iterator<Integer> it = pq.iterator();
        pq.add(2); // structural modification after iterator creation
        try {
            it.next();
            throw new AssertionError("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }

    static void testNextNoSuchElementException() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        Iterator<Integer> it = pq.iterator();
        // Iterator is empty, next() should throw NoSuchElementException
        try {
            it.next();
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }
}
