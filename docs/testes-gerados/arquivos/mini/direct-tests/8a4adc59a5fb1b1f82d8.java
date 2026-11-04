/*
 * @test
 * @summary Test LinkedBlockingQueue.put(E) method behavior
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testPutNullThrowsNPE();
        testPutBlocksWhenFullAndUnblocks();
        testPutInsertsElementAndSignals();
    }

    static void testPutNullThrowsNPE() {
        LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<>();
        try {
            queue.put(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        } catch (InterruptedException e) {
            throw new Error("Unexpected InterruptedException", e);
        }
    }

    static void testPutInsertsElementAndSignals() throws InterruptedException {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(2);
        queue.put("first");
        queue.put("second");
        // queue should be full now
        if (queue.size() != 2) {
            throw new Error("Queue size expected to be 2 but was " + queue.size());
        }
        // take one element and check it
        String first = queue.take();
        if (!"first".equals(first)) {
            throw new Error("Expected 'first' but got " + first);
        }
    }

    static void testPutBlocksWhenFullAndUnblocks() throws Exception {
        final LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>(1);
        queue.put(1); // fill the queue

        Thread putThread = new Thread(new Runnable() {
            public void run() {
                try {
                    queue.put(2); // should block until space available
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        putThread.start();

        // Sleep briefly to ensure putThread is blocked on put
        Thread.sleep(100);

        if (!putThread.isAlive()) {
            throw new Error("putThread should be blocked but is not alive");
        }

        // Remove one element to free space
        Integer val = queue.take();
        if (val == null || val != 1) {
            throw new Error("Expected to take 1 but got " + val);
        }

        // Wait for putThread to finish
        putThread.join(1000);

        if (putThread.isAlive()) {
            throw new Error("putThread did not finish after space was freed");
        }

        // Now queue should contain the element 2
        Integer next = queue.take();
        if (next == null || next != 2) {
            throw new Error("Expected to take 2 but got " + next);
        }
    }
}
