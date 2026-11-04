/*
 * @test
 * @summary Test ArrayBlockingQueue.put(E) for null input, interrupt, and normal behavior
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testPutNullThrowsNPE();
        testPutInterruptThrowsInterruptedException();
        testPutNormalBehavior();
    }

    static void testPutNullThrowsNPE() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(1);
        try {
            q.put(null);
            throw new Error("Expected NullPointerException for null element");
        } catch (NullPointerException expected) {
            // expected
        } catch (InterruptedException ie) {
            throw new Error("Unexpected InterruptedException", ie);
        }
    }

    static void testPutInterruptThrowsInterruptedException() throws Exception {
        final ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(1);
        // Fill queue to block put
        q.put("x");

        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    q.put("y"); // will block because queue is full
                    throw new Error("Expected InterruptedException not thrown");
                } catch (InterruptedException expected) {
                    // expected
                }
            }
        });

        t.start();
        Thread.sleep(100); // ensure thread is blocked on put
        t.interrupt();
        t.join();
    }

    static void testPutNormalBehavior() throws Exception {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(2);
        q.put("a");
        q.put("b");
        if (!"a".equals(q.take()))
            throw new Error("Expected first element 'a'");
        if (!"b".equals(q.take()))
            throw new Error("Expected second element 'b'");
    }

}
