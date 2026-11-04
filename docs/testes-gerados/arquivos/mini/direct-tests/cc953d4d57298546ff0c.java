/*
 * @test
 * @summary Test PriorityBlockingQueue.take() blocking and retrieval behavior
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testTakeBlocksUntilElementAvailable();
        testTakeReturnsElementImmediatelyIfAvailable();
    }

    static void testTakeBlocksUntilElementAvailable() throws Exception {
        final PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        final AtomicBoolean tookElement = new AtomicBoolean(false);

        Thread taker = new Thread(new Runnable() {
            public void run() {
                try {
                    Integer val = queue.take();
                    if (val != null && val == 42) {
                        tookElement.set(true);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        taker.start();

        // Sleep briefly to ensure taker thread is waiting on take()
        Thread.sleep(200);

        // At this point, taker should be blocked waiting for element
        if (tookElement.get()) {
            throw new RuntimeException("take() returned element too early");
        }

        // Add element to unblock taker
        queue.put(42);

        // Wait for taker to finish
        taker.join(1000);

        if (!tookElement.get()) {
            throw new RuntimeException("take() did not return the expected element");
        }
    }

    static void testTakeReturnsElementImmediatelyIfAvailable() throws Exception {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
        queue.put("hello");

        String result = queue.take();

        if (!"hello".equals(result)) {
            throw new RuntimeException("take() did not return the expected element immediately");
        }
    }
}
