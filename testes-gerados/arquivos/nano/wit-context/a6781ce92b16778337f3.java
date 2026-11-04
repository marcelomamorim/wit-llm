/*
 * @test
 * @summary Test for LinkedTransferQueue.put method, including null input handling.
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testPutWithNull();
        testPutWithValidElement();
    }

    static void testPutWithNull() {
        LinkedTransferQueue<Object> queue = new LinkedTransferQueue<>();
        try {
            queue.put(null);
            // Expecting no exception, as put(null) is valid in LinkedTransferQueue
        } catch (NullPointerException e) {
            // If a NullPointerException is thrown, the test should fail, as per current implementation
            throw new AssertionError("put(null) threw NullPointerException", e);
        }
        // Verify that the queue contains null element
        if (!queue.contains(null)) {
            throw new AssertionError("Queue does not contain null after put(null)");
        }
    }

    static void testPutWithValidElement() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        String element = "test";
        queue.put(element);
        if (!queue.contains(element)) {
            throw new AssertionError("Queue does not contain the element after put") ;
        }
    }
}
