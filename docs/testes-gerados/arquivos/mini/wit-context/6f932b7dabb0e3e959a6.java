/*
 * @test
 * @summary Test SynchronousQueue.put(E) for null argument and interruption behavior
 * @run main SynchronousQueueWitupTest
 */

import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testPutNullThrowsNPE();
        testPutInterruptedThrowsInterruptedException();
    }

    private static void testPutNullThrowsNPE() {
        SynchronousQueue<Object> queue = new SynchronousQueue<>();
        try {
            queue.put(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (InterruptedException ie) {
            throw new Error("Unexpected InterruptedException", ie);
        }
    }

    private static void testPutInterruptedThrowsInterruptedException() throws Exception {
        final SynchronousQueue<String> queue = new SynchronousQueue<>();
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    queue.put("test");
                    throw new Error("Expected InterruptedException not thrown");
                } catch (InterruptedException expected) {
                    // Expected
                }
            }
        });
        t.start();
        // Give thread time to start and block on put
        Thread.sleep(100);
        t.interrupt();
        t.join();
    }
}
