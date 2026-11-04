/*
 * @test
 * @summary Test LinkedBlockingQueue.peek() behavior including empty queue and non-empty queue
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

        // Test peek on empty queue returns null
        if (queue.peek() != null) {
            throw new AssertionError("peek() on empty queue should return null");
        }

        // Add one element and test peek returns it without removing
        String element = "testElement";
        queue.put(element);
        String peeked = queue.peek();
        if (!element.equals(peeked)) {
            throw new AssertionError("peek() should return the head element");
        }

        // Test peek again returns same element (does not remove)
        String peekedAgain = queue.peek();
        if (!element.equals(peekedAgain)) {
            throw new AssertionError("peek() should not remove the element");
        }

        // Test queue size remains 1 after peek
        if (queue.size() != 1) {
            throw new AssertionError("queue size should remain 1 after peek");
        }

        System.out.println("All LinkedBlockingQueue.peek() tests passed.");
    }
}
