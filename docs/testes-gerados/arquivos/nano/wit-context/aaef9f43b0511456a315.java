/*
 * @test
 * @summary Test for LinkedBlockingQueue.clear() method behavior.
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.NoSuchElementException;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testClearEmptyQueue();
        testClearNonEmptyQueue();
        testClearAfterConcurrentModification();
    }

    private static void testClearEmptyQueue() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.clear();
        assert queue.isEmpty() : "Queue should be empty after clear()";
    }

    private static void testClearNonEmptyQueue() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.offer("a");
        queue.offer("b");
        queue.clear();
        assert queue.isEmpty() : "Queue should be empty after clear()";
        assert queue.size() == 0 : "Size should be zero after clear()";
    }

    private static void testClearAfterConcurrentModification() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        for (int i = 0; i < 5; i++) {
            queue.offer(i);
        }
        // Simulate concurrent modification by another thread (not actual threading here)
        queue.offer(99);
        queue.clear();
        assert queue.isEmpty() : "Queue should be empty after clear()";
    }
}
