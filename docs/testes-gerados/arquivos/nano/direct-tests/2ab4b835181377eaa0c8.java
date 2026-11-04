/*
 * @test
 * @summary Test for LinkedTransferQueue.remove() method to ensure correct behavior and exception handling.
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.NoSuchElementException;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testRemoveOnEmptyQueue();
        testRemoveOnNonEmptyQueue();
        testRemoveMultipleTimes();
    }

    static void testRemoveOnEmptyQueue() {
        LinkedTransferQueue<Object> queue = new LinkedTransferQueue<>();
        boolean exceptionThrown = false;
        try {
            queue.remove();
        } catch (IllegalStateException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IllegalStateException when removing from empty queue";
    }

    static void testRemoveOnNonEmptyQueue() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.add("element1");
        queue.add("element2");
        Object firstElement = queue.peek();
        // Simulate internal state: set lastRet to the node containing first element
        // Since internal nodes are not accessible, we test the effect indirectly
        // Remove the first element
        queue.remove();
        String nextElement = queue.peek();
        assert nextElement != null && nextElement.equals("element2") : "Queue should contain 'element2' after removal";
    }

    static void testRemoveMultipleTimes() {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        // Remove first element
        queue.remove();
        // Remove second element
        queue.remove();
        // Remove third element
        queue.remove();
        boolean exceptionThrown = false;
        try {
            queue.remove();
        } catch (IllegalStateException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IllegalStateException after removing all elements";
    }
}
