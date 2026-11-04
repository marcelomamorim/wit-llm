/*
 * @test
 * @summary Test LinkedBlockingQueue.take() behavior including interrupt and normal take
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testTakeBlocksAndReturnsElement();
        testTakeInterruptsProperly();
    }

    static void testTakeBlocksAndReturnsElement() throws Exception {
        final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(1);
        queue.put("test");

        // take() should return the element immediately since queue is not empty
        String result = queue.take();
        if (!"test".equals(result)) {
            throw new AssertionError("Expected 'test' but got: " + result);
        }

        // After take, queue should be empty, so take() will block if called in another thread
        final Thread taker = new Thread(new Runnable() {
            public void run() {
                try {
                    // This will block until an element is put
                    String val = queue.take();
                    if (!"delayed".equals(val)) {
                        throw new AssertionError("Expected 'delayed' but got: " + val);
                    }
                } catch (InterruptedException e) {
                    throw new AssertionError("Unexpected InterruptedException in taker thread");
                }
            }
        });

        taker.start();

        // Sleep briefly to ensure taker thread is blocked on take()
        Thread.sleep(100);

        // Now put an element to unblock taker
        queue.put("delayed");

        // Wait for taker to finish
        taker.join(1000);
        if (taker.isAlive()) {
            throw new AssertionError("Taker thread did not finish as expected");
        }
    }

    static void testTakeInterruptsProperly() throws Exception {
        final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

        final Thread taker = new Thread(new Runnable() {
            public void run() {
                try {
                    queue.take(); // Will block since queue is empty
                    throw new AssertionError("Expected InterruptedException but take() returned");
                } catch (InterruptedException e) {
                    // Expected path
                    return;
                }
            }
        });

        taker.start();

        // Sleep briefly to ensure taker thread is blocked on take()
        Thread.sleep(100);

        // Interrupt the taker thread to cause InterruptedException
        taker.interrupt();

        // Wait for taker to finish
        taker.join(1000);
        if (taker.isAlive()) {
            throw new AssertionError("Taker thread did not finish after interrupt");
        }
    }
}
