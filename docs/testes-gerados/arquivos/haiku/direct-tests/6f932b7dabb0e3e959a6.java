/*
 * @test
 * @summary Behavioral tests for SynchronousQueue.put(E): null rejection, interrupt handling and handoff to a taker
 * @run main SynchronousQueueWitupTest
 */

import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueWitupTest {

    public static void main(String[] args) throws Exception {
        for (boolean fair : new boolean[] { false, true }) {
            testPutNullThrowsNPE(fair);
            testPutWhenInterruptedThrowsIEAndClearsFlag(fair);
            testPutHandsOffToTaker(fair);
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testPutNullThrowsNPE(boolean fair) {
        SynchronousQueue<String> q = new SynchronousQueue<>(fair);
        try {
            q.put(null);
            throw new RuntimeException("put(null) did not throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        } catch (InterruptedException e) {
            throw new RuntimeException("unexpected InterruptedException", e);
        }
    }

    static void testPutWhenInterruptedThrowsIEAndClearsFlag(boolean fair) {
        SynchronousQueue<String> q = new SynchronousQueue<>(fair);
        Thread.currentThread().interrupt();
        try {
            q.put("x");
            throw new RuntimeException("put on interrupted thread did not throw InterruptedException");
        } catch (InterruptedException expected) {
            // expected
        }
        check(!Thread.currentThread().isInterrupted(),
              "interrupt flag should be cleared after put throws InterruptedException");
    }

    static void testPutHandsOffToTaker(boolean fair) throws Exception {
        final SynchronousQueue<String> q = new SynchronousQueue<>(fair);
        final String[] got = new String[1];
        Thread taker = new Thread(() -> {
            try {
                got[0] = q.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        taker.start();
        q.put("v");
        taker.join();
        check("v".equals(got[0]), "taker did not receive the element handed off by put");
    }
}
