/*
 * @test
 * @summary Test PriorityBlockingQueue add(E e) method delegates to offer(E e)
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();

        // Test adding a single element
        boolean added = queue.add(10);
        if (!added) {
            throw new RuntimeException("add returned false unexpectedly");
        }

        // Test that the element is present
        Integer polled = queue.poll();
        if (polled == null || polled.intValue() != 10) {
            throw new RuntimeException("Element added not found or incorrect");
        }

        // Test adding multiple elements and ordering
        queue.add(20);
        queue.add(5);
        queue.add(15);

        int first = queue.poll();
        int second = queue.poll();
        int third = queue.poll();

        if (first != 5 || second != 15 || third != 20) {
            throw new RuntimeException("Elements not returned in priority order");
        }

        // Test adding null element throws NullPointerException
        try {
            queue.add(null);
            throw new RuntimeException("Expected NullPointerException when adding null");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test adding non-comparable element to natural ordering queue throws ClassCastException
        PriorityBlockingQueue<Object> objQueue = new PriorityBlockingQueue<>();
        try {
            objQueue.add(new Object());
            objQueue.add(new Object()); // second add to trigger ordering
            throw new RuntimeException("Expected ClassCastException when adding non-comparable elements");
        } catch (ClassCastException expected) {
            // expected
        }
    }
}
