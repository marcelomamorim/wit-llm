/*
 * @test
 * @summary Test SynchronousQueue.put(E) behavior including null and interruption
 * @run main SynchronousQueueWitupTest
 */

import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testPutNullThrowsNPE();
        testPutBlocksAndSucceeds();
        testPutInterruptedThrowsInterruptedException();
    }

    static void testPutNullThrowsNPE() {
        SynchronousQueue<Object> q = new SynchronousQueue<>();
        try {
            q.put(null);
            throw new Error("Expected NullPointerException");
        } catch (NullPointerException success) {
            // expected
        } catch (InterruptedException ie) {
            throw new Error("Unexpected InterruptedException", ie);
        }
    }

    static void testPutBlocksAndSucceeds() throws Exception {
        final SynchronousQueue<String> q = new SynchronousQueue<>();
        Thread taker = new Thread(new Runnable() {
            public void run() {
                try {
                    String item = q.take();
                    if (!"test".equals(item))
                        throw new Error("Expected 'test', got: " + item);
                } catch (InterruptedException e) {
                    throw new Error("Unexpected interruption", e);
                }
            }
        });
        taker.start();
        // Give taker thread time to start and block on take
        Thread.sleep(100);
        q.put("test"); // should unblock taker
        taker.join();
    }

    static void testPutInterruptedThrowsInterruptedException() throws Exception {
        final SynchronousQueue<String> q = new SynchronousQueue<>();
        Thread putter = new Thread(new Runnable() {
            public void run() {
                try {
                    q.put("x");
                    throw new Error("Expected InterruptedException");
                } catch (InterruptedException expected) {
                    // expected
                }
            }
        });
        putter.start();
        // Interrupt the putter thread while it is blocked
        Thread.sleep(100);
        putter.interrupt();
        putter.join();
    }
}
