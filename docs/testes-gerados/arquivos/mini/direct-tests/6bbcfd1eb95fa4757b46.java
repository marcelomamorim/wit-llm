/*
 * @test
 * @summary Test ArrayBlockingQueue.take() blocking and retrieval behavior
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testTakeBlocksUntilElementAvailable();
        testTakeReturnsElement();
    }

    static void testTakeBlocksUntilElementAvailable() throws Exception {
        final ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(1);
        final AtomicBoolean took = new AtomicBoolean(false);

        Thread taker = new Thread(new Runnable() {
            public void run() {
                try {
                    String item = queue.take();
                    if ("test".equals(item)) {
                        took.set(true);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        taker.start();

        // Sleep briefly to ensure taker thread is waiting on take()
        Thread.sleep(100);

        // At this point, taker should be blocked, so took should be false
        if (took.get()) {
            throw new AssertionError("take() returned prematurely before element was available");
        }

        // Now put an element to unblock taker
        queue.put("test");

        // Wait for taker to finish
        taker.join(1000);

        if (!took.get()) {
            throw new AssertionError("take() did not return the expected element after it was put");
        }
    }

    static void testTakeReturnsElement() throws Exception {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);
        queue.put(42);
        int val = queue.take();
        if (val != 42) {
            throw new AssertionError("take() returned wrong element: expected 42 but got " + val);
        }
    }
}
