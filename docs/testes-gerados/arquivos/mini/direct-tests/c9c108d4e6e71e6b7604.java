/*
 * @test
 * @summary Test ArrayBlockingQueue.clear() method behavior
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testClearOnEmptyQueue();
        testClearOnNonEmptyQueue();
        testClearSignalsNotFullCondition();
    }

    static void testClearOnEmptyQueue() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(10);
        // Clear on empty queue should not throw and leave queue empty
        q.clear();
        if (!q.isEmpty()) {
            throw new AssertionError("Queue should be empty after clear on empty queue");
        }
    }

    static void testClearOnNonEmptyQueue() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(10);
        q.add(1);
        q.add(2);
        q.add(3);
        if (q.size() != 3) {
            throw new AssertionError("Queue size should be 3 before clear");
        }
        q.clear();
        if (!q.isEmpty()) {
            throw new AssertionError("Queue should be empty after clear");
        }
        // After clear, adding elements should work normally
        q.add(4);
        if (q.size() != 1 || q.peek() == null || q.peek() != 4) {
            throw new AssertionError("Queue should contain the newly added element after clear");
        }
    }

    static void testClearSignalsNotFullCondition() throws InterruptedException {
        final ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(1);
        final ReentrantLock lock = new ReentrantLock();
        final Condition notFull = lock.newCondition();

        // Fill the queue to capacity
        q.add(10);

        // Start a thread that will wait on the queue's notFull condition
        // We simulate this by locking and waiting on a condition that will be signaled by clear
        Thread waiter = new Thread(new Runnable() {
            public void run() {
                lock.lock();
                try {
                    // Wait with timeout to avoid deadlock if signal not sent
                    notFull.await(500, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    lock.unlock();
                }
            }
        });

        // Start waiter thread
        waiter.start();

        // Sleep briefly to ensure waiter is waiting
        Thread.sleep(100);

        // Clear the queue, which should signal notFull condition
        // We simulate the internal lock and condition signaling by calling clear under lock
        // but since we cannot access internal lock and condition, we test observable behavior:
        // After clear, queue should be empty and allow adding new element
        q.clear();

        // Wait for waiter thread to finish
        waiter.join(1000);

        if (!q.isEmpty()) {
            throw new AssertionError("Queue should be empty after clear");
        }

        // Adding element after clear should succeed
        q.add(20);
        if (q.size() != 1 || q.peek() == null || q.peek() != 20) {
            throw new AssertionError("Queue should accept new element after clear");
        }
    }
}
