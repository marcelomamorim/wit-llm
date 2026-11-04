/*
 * @test
 * @summary Verify LinkedTransferQueue.offer rejects null and accepts non-null elements in FIFO order
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testOfferNullThrowsNullPointerException();
        testOfferNullDoesNotModifyQueue();
        testOfferNonNullReturnsTrueAndKeepsElement();
        testOfferPreservesFifoOrder();
    }

    static void testOfferNullThrowsNullPointerException() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        boolean thrown = false;
        try {
            q.offer(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("offer(null) should throw NullPointerException");
        }
    }

    static void testOfferNullDoesNotModifyQueue() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        try {
            q.offer(null);
        } catch (NullPointerException expected) {
            // expected
        }
        if (!q.isEmpty()) {
            throw new AssertionError("queue should remain empty after offer(null)");
        }
        if (q.size() != 0) {
            throw new AssertionError("size should be 0 after offer(null), was " + q.size());
        }
    }

    static void testOfferNonNullReturnsTrueAndKeepsElement() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        if (!q.offer("a")) {
            throw new AssertionError("offer(non-null) should return true");
        }
        if (q.size() != 1) {
            throw new AssertionError("size should be 1, was " + q.size());
        }
        String head = q.poll();
        if (!"a".equals(head)) {
            throw new AssertionError("poll should return \"a\", was " + head);
        }
        if (!q.isEmpty()) {
            throw new AssertionError("queue should be empty after poll");
        }
    }

    static void testOfferPreservesFifoOrder() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        q.offer("first");
        q.offer("second");
        q.offer("third");
        if (q.size() != 3) {
            throw new AssertionError("size should be 3, was " + q.size());
        }
        check("first", q.poll());
        check("second", q.poll());
        check("third", q.poll());
        if (q.poll() != null) {
            throw new AssertionError("queue should be empty");
        }
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new AssertionError("expected " + expected + " but was " + actual);
        }
    }
}
