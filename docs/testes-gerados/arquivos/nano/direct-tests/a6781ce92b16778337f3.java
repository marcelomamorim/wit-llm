/*
 * @test
 * @summary Test the put method of LinkedTransferQueue for correct behavior.
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testPutWithNormalElement();
        testPutWithNullElement();
    }

    static void testPutWithNormalElement() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        String element = "test";
        queue.put(element);
        String retrieved = queue.poll();
        if (!"test".equals(retrieved)) {
            throw new AssertionError("Expected 'test', but got: " + retrieved);
        }
    }

    static void testPutWithNullElement() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        try {
            queue.put(null);
            String retrieved = queue.poll();
            if (retrieved != null) {
                throw new AssertionError("Expected null, but got: " + retrieved);
            }
        } catch (NullPointerException e) {
            // Expected behavior if nulls are not allowed
        }
    }
}
