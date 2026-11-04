/*
 * @test
 * @summary WITUP-derived tests for LinkedTransferQueue.put(E)
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testPutNullThrowsNullPointerException();
        testPutNullLeavesQueueUnchanged();
        testPutNonNullThenPollReturnsElement();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testPutNullThrowsNullPointerException() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        try {
            queue.put(null);
            throw new RuntimeException("put(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testPutNullLeavesQueueUnchanged() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        try {
            queue.put(null);
        } catch (NullPointerException expected) {
            // expected
        }
        check(queue.isEmpty(), "queue should remain empty after put(null)");
        check(queue.size() == 0, "size should be 0 after put(null)");
        check(queue.poll() == null, "poll should return null after put(null) failed");
    }

    static void testPutNonNullThenPollReturnsElement() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.put("a");
        check(!queue.isEmpty(), "queue should not be empty after put(non-null)");
        check(queue.size() == 1, "size should be 1 after one put");
        String value = queue.poll();
        check("a".equals(value), "poll should return the element previously put");
        check(queue.isEmpty(), "queue should be empty after polling the only element");
    }
}
