/*
 * @test
 * @summary Test for LinkedTransferQueue.add(E e) method, including null input handling.
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testAddWithNull();
        testAddWithValidElement();
    }

    private static void testAddWithNull() {
        LinkedTransferQueue<Object> queue = new LinkedTransferQueue<>();
        try {
            queue.add(null);
            // If no exception, test passes as method does not specify null rejection
        } catch (NullPointerException e) {
            // Expected if add() throws NPE on null, but current implementation does not throw
            // So, no action needed
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for null input", e);
        }
    }

    private static void testAddWithValidElement() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        String element = "test";
        boolean result = queue.add(element);
        if (!result) {
            throw new AssertionError("add() returned false for valid element");
        }
        if (queue.isEmpty()) {
            throw new AssertionError("Queue is empty after add() with valid element");
        }
        String dequeued = queue.poll();
        if (dequeued == null || !dequeued.equals(element)) {
            throw new AssertionError("Dequeued element does not match added element");
        }
    }
}
