/*
 * @test
 * @summary WITUP-derived behavioral tests for LinkedTransferQueue.transfer(E)
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testTransferNullThrowsNullPointerException();
        testTransferHandsOffToConsumer();
        testTransferInterruptedThrowsInterruptedException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testTransferNullThrowsNullPointerException() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        try {
            q.transfer(null);
            throw new RuntimeException("transfer(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        } catch (InterruptedException e) {
            throw new RuntimeException("unexpected InterruptedException", e);
        }
        check(q.isEmpty(), "queue should remain empty after rejected transfer(null)");
    }

    static void testTransferHandsOffToConsumer() throws Exception {
        final LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        final String[] got = new String[1];
        Thread consumer = new Thread(() -> {
            try {
                got[0] = q.take();
            } catch (InterruptedException e) {
                // leave got[0] null; checked below
            }
        });
        consumer.start();
        q.transfer("x");
        consumer.join(5000);
        check(!consumer.isAlive(), "consumer should terminate");
        check("x".equals(got[0]), "consumer should receive transferred element");
    }

    static void testTransferInterruptedThrowsInterruptedException() throws Exception {
        final LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        final boolean[] caught = new boolean[1];
        Thread t = new Thread(() -> {
            try {
                q.transfer("y");
            } catch (InterruptedException e) {
                caught[0] = true;
            }
        });
        t.start();
        Thread.sleep(100);
        t.interrupt();
        t.join(5000);
        check(!t.isAlive(), "transferring thread should terminate after interrupt");
        check(caught[0], "interrupted transfer should throw InterruptedException");
    }
}
