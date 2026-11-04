/*
 * @test
 * @summary Test LinkedBlockingQueue.clear() method behavior
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testClearOnEmptyQueue();
        testClearOnNonEmptyQueue();
        testClearSignalsNotFullWhenFull();
    }

    static void testClearOnEmptyQueue() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        // Clear on empty queue should keep it empty and not throw
        queue.clear();
        if (queue.size() != 0) {
            throw new AssertionError("Queue should be empty after clear on empty queue");
        }
    }

    static void testClearOnNonEmptyQueue() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");
        queue.clear();
        if (queue.size() != 0) {
            throw new AssertionError("Queue should be empty after clear on non-empty queue");
        }
        // After clear, queue should not contain any element
        if (queue.poll() != null) {
            throw new AssertionError("Queue.poll() should return null after clear");
        }
    }

    static void testClearSignalsNotFullWhenFull() throws InterruptedException {
        final int capacity = 2;
        final LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>(capacity);
        // Fill the queue to capacity
        queue.put(1);
        queue.put(2);
        // Start a thread that tries to put and will block until clear signals
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    queue.put(3); // should block until clear is called
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        t.start();
        // Sleep briefly to ensure thread is blocked on put
        Thread.sleep(100);
        if (queue.remainingCapacity() != 0) {
            throw new AssertionError("Queue should be full before clear");
        }
        // Clear the queue, which should signal notFull and unblock the thread
        queue.clear();
        // Wait for thread to finish
        t.join(1000);
        if (t.isAlive()) {
            throw new AssertionError("Thread did not unblock after clear signaled notFull");
        }
        // After clear and put by thread, size should be 1
        if (queue.size() != 1) {
            throw new AssertionError("Queue size should be 1 after clear and put by thread");
        }
    }
}
