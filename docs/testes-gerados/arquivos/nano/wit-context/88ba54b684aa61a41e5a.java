/* @test    @summary Test for PriorityBlockingQueue.clear() method to verify normal behavior and exception handling    @run main PriorityBlockingQueueWitupTest */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.NoSuchElementException;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testClearEmptyQueue();
        testClearNonEmptyQueue();
        testClearAfterConcurrentModification();
    }

    static void testClearEmptyQueue() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.clear();
        assert queue.isEmpty() : "Queue should be empty after clear()";
    }

    static void testClearNonEmptyQueue() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.clear();
        assert queue.isEmpty() : "Queue should be empty after clear()";
    }

    static void testClearAfterConcurrentModification() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(1);
        queue.add(2);
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(10);
                queue.remove(1);
            } catch (InterruptedException e) {
                // ignore
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            // ignore
        }
        queue.clear();
        assert queue.isEmpty() : "Queue should be empty after clear()";
    }
}
