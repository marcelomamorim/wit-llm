/*
 * @test
 * @summary Behavioral tests for LinkedBlockingQueue.put
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testPutNullThrowsNullPointerException();
        testPutPreservesFifoOrder();
        testPutBlocksWhenFullUntilTake();
        testPutInterruptedOnFullQueueThrowsInterruptedException();
        testPutFillsBoundedCapacity();
    }

    static void failTest(String msg) {
        throw new RuntimeException(msg);
    }

    static void testPutNullThrowsNullPointerException() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        try {
            q.put(null);
            failTest("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        } catch (InterruptedException e) {
            failTest("unexpected InterruptedException");
        }
        if (q.size() != 0) {
            failTest("queue should remain empty after failed put(null)");
        }
    }

    static void testPutPreservesFifoOrder() throws InterruptedException {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>(10);
        q.put("a");
        q.put("b");
        q.put("c");
        if (q.size() != 3) {
            failTest("expected size 3 but was " + q.size());
        }
        if (!"a".equals(q.take())) failTest("expected a first");
        if (!"b".equals(q.take())) failTest("expected b second");
        if (!"c".equals(q.take())) failTest("expected c third");
        if (q.size() != 0) {
            failTest("expected empty queue");
        }
    }

    static void testPutBlocksWhenFullUntilTake() throws Exception {
        final LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(1);
        q.put(1);
        Thread t = new Thread(() -> {
            try {
                q.put(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
        long deadline = System.currentTimeMillis() + 10000;
        while (t.getState() != Thread.State.WAITING
                && System.currentTimeMillis() < deadline) {
            Thread.sleep(10);
        }
        if (t.getState() != Thread.State.WAITING) {
            failTest("putter thread did not block on full queue");
        }
        if (q.size() != 1) {
            failTest("queue should still hold only 1 element while blocked");
        }
        if (q.take().intValue() != 1) {
            failTest("expected first element 1");
        }
        t.join(10000);
        if (t.isAlive()) {
            failTest("putter thread did not complete after take");
        }
        if (q.take().intValue() != 2) {
            failTest("expected unblocked put element 2");
        }
    }

    static void testPutInterruptedOnFullQueueThrowsInterruptedException() throws Exception {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>(1);
        q.put("x");
        Thread.currentThread().interrupt();
        try {
            q.put("y");
            failTest("expected InterruptedException");
        } catch (InterruptedException expected) {
            // expected
        }
        if (q.size() != 1) {
            failTest("queue size should remain 1");
        }
        if (!"x".equals(q.peek())) {
            failTest("existing element should be unchanged");
        }
    }

    static void testPutFillsBoundedCapacity() throws InterruptedException {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(2);
        q.put(1);
        q.put(2);
        if (q.size() != 2) {
            failTest("expected size 2");
        }
        if (q.remainingCapacity() != 0) {
            failTest("expected remaining capacity 0");
        }
    }
}
