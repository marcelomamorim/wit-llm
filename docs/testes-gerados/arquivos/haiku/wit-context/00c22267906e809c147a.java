/*
 * @test
 * @summary WITUP-derived checks for LinkedTransferQueue.tryTransfer(E): null rejection and non-blocking behavior
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testNullThrowsNullPointerException();
        testNullOnNonEmptyQueueThrowsAndKeepsElements();
        testEmptyQueueReturnsFalse();
        testTryTransferDoesNotAddElement();
    }

    static void testNullThrowsNullPointerException() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        boolean thrown = false;
        try {
            q.tryTransfer(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("tryTransfer(null) did not throw NullPointerException");
        }
    }

    static void testNullOnNonEmptyQueueThrowsAndKeepsElements() throws InterruptedException {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        q.put("a");
        boolean thrown = false;
        try {
            q.tryTransfer(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("tryTransfer(null) on non-empty queue did not throw");
        }
        if (q.size() != 1 || !"a".equals(q.poll())) {
            throw new RuntimeException("queue state changed after rejected null transfer");
        }
    }

    static void testEmptyQueueReturnsFalse() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        if (q.tryTransfer("x")) {
            throw new RuntimeException("tryTransfer on empty queue without consumer returned true");
        }
    }

    static void testTryTransferDoesNotAddElement() throws InterruptedException {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        q.put("a");
        if (q.tryTransfer("b")) {
            throw new RuntimeException("tryTransfer returned true without a waiting consumer");
        }
        if (q.size() != 1) {
            throw new RuntimeException("unexpected size after failed tryTransfer: " + q.size());
        }
        if (!"a".equals(q.poll())) {
            throw new RuntimeException("unexpected head after failed tryTransfer");
        }
        if (q.poll() != null) {
            throw new RuntimeException("failed tryTransfer added an element");
        }
    }
}
