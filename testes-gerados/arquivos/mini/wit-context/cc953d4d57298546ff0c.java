/*
 * @test
 * @summary Test PriorityBlockingQueue.take() blocking and interrupt behavior
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testTakeBlocksAndReturnsElement();
        testTakeInterruptsThread();
    }

    static void testTakeBlocksAndReturnsElement() throws Exception {
        final PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        final Thread taker = new Thread(new Runnable() {
            public void run() {
                try {
                    Integer val = queue.take();
                    if (val == null || val != 42) {
                        throw new AssertionError("Expected 42 but got " + val);
                    }
                } catch (InterruptedException e) {
                    throw new AssertionError("Unexpected InterruptedException", e);
                }
            }
        });

        taker.start();

        // Sleep briefly to ensure taker thread is waiting on take()
        Thread.sleep(100);

        // Add element to unblock taker
        queue.put(42);

        // Wait for taker to finish
        taker.join(1000);

        if (taker.isAlive()) {
            throw new AssertionError("take() did not return after element was added");
        }
    }

    static void testTakeInterruptsThread() throws Exception {
        final PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        final Thread taker = new Thread(new Runnable() {
            public void run() {
                try {
                    queue.take();
                    throw new AssertionError("Expected InterruptedException not thrown");
                } catch (InterruptedException expected) {
                    // Expected path
                }
            }
        });

        taker.start();

        // Sleep briefly to ensure taker thread is waiting on take()
        Thread.sleep(100);

        // Interrupt the taker thread to cause InterruptedException
        taker.interrupt();

        // Wait for taker to finish
        taker.join(1000);

        if (taker.isAlive()) {
            throw new AssertionError("take() did not respond to interrupt");
        }
    }
}
