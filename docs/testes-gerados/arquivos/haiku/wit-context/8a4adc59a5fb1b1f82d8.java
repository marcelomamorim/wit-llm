/*
 * @test
 * @summary Witup-derived checks for LinkedBlockingQueue.put
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testNullThrowsNPE();
        testPutThenTake();
        testPutFillsCapacity();
        testInterruptedThrows();
        testPutUnbounded();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullThrowsNPE() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        try {
            q.put(null);
            check(false, "put(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        } catch (InterruptedException e) {
            throw new AssertionError("unexpected InterruptedException", e);
        }
        check(q.isEmpty(), "queue must remain empty after put(null)");
    }

    static void testPutThenTake() throws InterruptedException {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        q.put("a");
        check(q.size() == 1, "size after one put should be 1");
        check("a".equals(q.take()), "take should return the element put");
        check(q.isEmpty(), "queue should be empty after take");
    }

    static void testPutFillsCapacity() throws InterruptedException {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>(2);
        q.put("a");
        q.put("b");
        check(q.size() == 2, "size should be 2 at capacity");
        check(q.remainingCapacity() == 0, "remainingCapacity should be 0 at capacity");
        check("a".equals(q.take()), "FIFO order: first take should be a");
        check("b".equals(q.take()), "FIFO order: second take should be b");
    }

    static void testInterruptedThrows() throws InterruptedException {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        Thread.currentThread().interrupt();
        try {
            q.put("x");
            check(false, "put on interrupted thread should throw InterruptedException");
        } catch (InterruptedException expected) {
            // expected
        }
        check(!Thread.currentThread().isInterrupted(), "interrupt status should be cleared");
        check(q.isEmpty(), "queue must remain empty when put is interrupted");
    }

    static void testPutUnbounded() throws InterruptedException {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        for (int i = 0; i < 100; i++) {
            q.put(i);
        }
        check(q.size() == 100, "size should be 100 after 100 puts");
        for (int i = 0; i < 100; i++) {
            check(q.take() == i, "FIFO order violated at " + i);
        }
    }
}
