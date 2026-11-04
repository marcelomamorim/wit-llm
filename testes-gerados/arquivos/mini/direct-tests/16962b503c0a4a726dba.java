/*
 * @test
 * @summary Test PriorityQueue add(E e) method delegates to offer(E e)
 * @run main PriorityQueueWitupTest
 */

import java.util.PriorityQueue;

public class PriorityQueueWitupTest {
    public static void main(String[] args) {
        PriorityQueue<String> pq = new PriorityQueue<>();

        // Test adding a single element
        boolean added = pq.add("test");
        if (!added) {
            throw new RuntimeException("add() should return true when adding an element");
        }
        if (!"test".equals(pq.peek())) {
            throw new RuntimeException("Element added should be at the head of the queue");
        }

        // Test adding multiple elements and ordering
        pq.add("abc");
        pq.add("xyz");

        // The head should be the least element according to natural ordering
        String head = pq.peek();
        if (!"abc".equals(head)) {
            throw new RuntimeException("Head of the queue should be 'abc', but was: " + head);
        }

        // Test adding null element throws NullPointerException
        try {
            pq.add(null);
            throw new RuntimeException("add(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test adding non-comparable element to natural ordering queue throws ClassCastException
        PriorityQueue rawQueue = new PriorityQueue();
        rawQueue.add("string");
        try {
            rawQueue.add(new Object());
            throw new RuntimeException("add(non-comparable) should throw ClassCastException");
        } catch (ClassCastException expected) {
            // expected
        }
    }
}
