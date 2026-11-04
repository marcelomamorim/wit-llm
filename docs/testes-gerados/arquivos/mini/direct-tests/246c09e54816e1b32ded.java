/*
 * @test
 * @summary Test ArrayBlockingQueue.put(E) blocking and null rejection
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testPutNullThrowsNPE();
        testPutBlocksWhenFull();
    }

    static void testPutNullThrowsNPE() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(1);
        try {
            q.put(null);
            throw new Error("Expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        } catch (InterruptedException ie) {
            throw new Error("Unexpected InterruptedException", ie);
        }
    }

    static void testPutBlocksWhenFull() throws Exception {
        final ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(1);
        q.put("first"); // queue is now full

        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    q.put("second"); // should block until space available
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t.start();

        // Give thread time to block on put
        Thread.sleep(200);

        if (!t.isAlive()) {
            throw new Error("Put did not block on full queue");
        }

        // Remove one element to unblock put
        String removed = q.take();
        if (!"first".equals(removed)) {
            throw new Error("Unexpected element removed: " + removed);
        }

        // Wait for thread to finish
        t.join(1000);

        if (t.isAlive()) {
            throw new Error("Put thread did not finish after space was available");
        }

        // Now queue should contain "second"
        String head = q.take();
        if (!"second".equals(head)) {
            throw new Error("Expected 'second' after unblocking put, got: " + head);
        }
    }
}
