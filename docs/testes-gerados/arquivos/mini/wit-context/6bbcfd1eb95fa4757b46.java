/*
 * @test
 * @summary Test ArrayBlockingQueue.take() for interrupt and normal behavior
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testTakeBlocksAndReturnsElement();
        testTakeInterrupts();
    }

    static void testTakeBlocksAndReturnsElement() throws Exception {
        final ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);
        queue.put(42); // queue now has one element

        // take() should return the element immediately
        Integer val = queue.take();
        if (val == null || val != 42) {
            throw new AssertionError("take() did not return the expected element");
        }

        // After take, queue should be empty
        if (queue.size() != 0) {
            throw new AssertionError("Queue should be empty after take()");
        }
    }

    static void testTakeInterrupts() throws Exception {
        final ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);

        Thread taker = new Thread(new Runnable() {
            public void run() {
                try {
                    queue.take(); // will block because queue is empty
                    throw new AssertionError("take() should have been interrupted");
                } catch (InterruptedException e) {
                    // Expected interruption
                    return;
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }
            }
        });

        taker.start();
        Thread.sleep(100); // Give taker thread time to block
        taker.interrupt();
        taker.join(1000);

        if (taker.isAlive()) {
            throw new AssertionError("Thread did not respond to interrupt in take()");
        }
    }
}
