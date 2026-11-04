/*
 * @test
 * @summary Test LinkedBlockingQueue.put(E) for null input and basic put behavior
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testPutNullThrowsNPE();
        testPutBasicBehavior();
    }

    private static void testPutNullThrowsNPE() {
        LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<>();
        try {
            queue.put(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (InterruptedException ie) {
            throw new RuntimeException("Unexpected InterruptedException", ie);
        }
    }

    private static void testPutBasicBehavior() throws InterruptedException {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(2);
        queue.put("first");
        queue.put("second");
        // The queue capacity is 2, so it should be full now
        // Start a thread to put a third element, which will block until space is available
        Thread putThread = new Thread(new Runnable() {
            public void run() {
                try {
                    queue.put("third");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        putThread.start();

        // Sleep briefly to ensure putThread is blocked on put
        Thread.sleep(100);

        // The queue size should be 2 (full)
        if (queue.size() != 2) {
            throw new RuntimeException("Queue size expected to be 2 but was " + queue.size());
        }

        // Remove one element to free space
        String removed = queue.take();
        if (!"first".equals(removed) && !"second".equals(removed)) {
            throw new RuntimeException("Unexpected element removed: " + removed);
        }

        // Wait for putThread to finish
        putThread.join(1000);
        if (putThread.isAlive()) {
            throw new RuntimeException("putThread did not finish as expected");
        }

        // Now the queue size should be 2 again
        if (queue.size() != 2) {
            throw new RuntimeException("Queue size expected to be 2 after putThread finished but was " + queue.size());
        }
    }
}
