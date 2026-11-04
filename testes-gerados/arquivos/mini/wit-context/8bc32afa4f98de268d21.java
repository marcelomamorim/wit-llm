/*
 * @test
 * @summary Test PriorityBlockingQueue.put(E) method behavior including null input and normal insertion
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        testPutNullThrowsNPE();
        testPutNormalElement();
    }

    private static void testPutNullThrowsNPE() {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
        try {
            queue.put(null);
            throw new AssertionError("Expected NullPointerException when putting null");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testPutNormalElement() {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
        String element = "test";
        queue.put(element);
        String polled = queue.poll();
        if (!element.equals(polled)) {
            throw new AssertionError("Element put and polled do not match: expected " + element + " but got " + polled);
        }
    }
}
