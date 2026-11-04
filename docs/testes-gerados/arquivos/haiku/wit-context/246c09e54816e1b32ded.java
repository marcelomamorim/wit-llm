/*
 * @test
 * @summary Witup-derived behavioral checks for ArrayBlockingQueue.put
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testNullElementThrowsNPE();
        testNullElementLeavesQueueUnchanged();
        testPutThenTakeFifo();
        testInterruptedThrowsInterruptedException();
        testNullCheckPrecedesInterruptCheck();
        testCapacityBoundObservedThroughOffer();
    }

    static void fail(String message) {
        throw new AssertionError(message);
    }

    static void testNullElementThrowsNPE() throws Exception {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(2);
        try {
            q.put(null);
            fail("expected NullPointerException for put(null)");
        } catch (NullPointerException expected) {
        }
    }

    static void testNullElementLeavesQueueUnchanged() throws Exception {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(2);
        try {
            q.put(null);
        } catch (NullPointerException expected) {
        }
        if (!q.isEmpty()) {
            fail("queue must remain empty after failed put(null)");
        }
        if (q.size() != 0) {
            fail("size must be 0 after failed put(null)");
        }
    }

    static void testPutThenTakeFifo() throws Exception {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        q.put(1);
        q.put(2);
        q.put(3);
        if (q.size() != 3) {
            fail("expected size 3, got " + q.size());
        }
        if (q.take().intValue() != 1) {
            fail("expected head 1");
        }
        if (q.take().intValue() != 2) {
            fail("expected head 2");
        }
        if (q.take().intValue() != 3) {
            fail("expected head 3");
        }
        if (!q.isEmpty()) {
            fail("queue should be empty at the end");
        }
    }

    static void testInterruptedThrowsInterruptedException() throws Exception {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(2);
        Thread.currentThread().interrupt();
        try {
            q.put(7);
            fail("expected InterruptedException when interrupt status is set");
        } catch (InterruptedException expected) {
        } finally {
            Thread.interrupted();
        }
        if (!q.isEmpty()) {
            fail("queue must remain empty after interrupted put");
        }
    }

    static void testNullCheckPrecedesInterruptCheck() throws Exception {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(2);
        Thread.currentThread().interrupt();
        try {
            q.put(null);
            fail("expected NullPointerException before interrupt handling");
        } catch (NullPointerException expected) {
        } finally {
            Thread.interrupted();
        }
    }

    static void testCapacityBoundObservedThroughOffer() throws Exception {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(2);
        q.put(10);
        q.put(20);
        if (q.offer(30)) {
            fail("offer must return false when queue is full");
        }
        if (q.size() != 2) {
            fail("size must remain 2 when full");
        }
        if (q.take().intValue() != 10) {
            fail("head must be 10");
        }
    }
}
