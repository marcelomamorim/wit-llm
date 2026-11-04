/* @test    @summary Test for PriorityBlockingQueue.contains() method    @run main PriorityBlockingQueueWitupTest */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.NoSuchElementException;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testContainsWithExistingElement();
        testContainsWithNonExistingElement();
        testContainsWithNull();
    }

    private static void testContainsWithExistingElement() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(10);
        queue.add(20);
        queue.add(30);
        boolean result = queue.contains(20);
        if (!result) {
            throw new AssertionError("Expected queue to contain 20");
        }
    }

    private static void testContainsWithNonExistingElement() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        boolean result = queue.contains(4);
        if (result) {
            throw new AssertionError("Expected queue not to contain 4");
        }
    }

    private static void testContainsWithNull() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(5);
        queue.add(6);
        try {
            boolean result = queue.contains(null);
            if (result) {
                throw new AssertionError("Expected queue not to contain null");
            }
        } catch (NullPointerException e) {
            // Expected, as contains() does not support null elements
        }
    }
}
