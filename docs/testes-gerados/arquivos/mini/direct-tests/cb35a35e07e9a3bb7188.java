/*
 * @test
 * @summary Test PriorityQueue.next() iterator method for expected behavior and exceptions
 * @run main PriorityQueueWitupTest
 */

import java.util.PriorityQueue;
import java.util.Iterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

public class PriorityQueueWitupTest {
    public static void main(String[] args) {
        testNextReturnsElementsInOrder();
        testNextThrowsNoSuchElementException();
        testNextThrowsConcurrentModificationException();
    }

    static void testNextReturnsElementsInOrder() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(10);
        pq.add(20);
        pq.add(15);

        Iterator<Integer> it = pq.iterator();

        // The iterator order is not guaranteed, but next() should return all elements without exception
        int count = 0;
        boolean[] found = new boolean[3];
        while (true) {
            try {
                Integer val = it.next();
                if (val == 10) found[0] = true;
                else if (val == 15) found[1] = true;
                else if (val == 20) found[2] = true;
                count++;
            } catch (NoSuchElementException e) {
                break;
            }
        }
        if (count != 3 || !found[0] || !found[1] || !found[2]) {
            throw new AssertionError("Iterator.next() did not return all elements correctly");
        }
    }

    static void testNextThrowsNoSuchElementException() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        Iterator<Integer> it = pq.iterator();
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NoSuchElementException when calling next() on empty iterator");
        }
    }

    static void testNextThrowsConcurrentModificationException() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        Iterator<Integer> it = pq.iterator();
        pq.add(2); // structural modification after iterator creation
        boolean thrown = false;
        try {
            it.next();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected ConcurrentModificationException when calling next() after modification");
        }
    }
}
