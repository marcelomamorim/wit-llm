/*
 * @test    @summary Test clear() method of LinkedBlockingQueue
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.NoSuchElementException;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testClearOnEmptyQueue();
        testClearOnPopulatedQueue();
        testClearMultipleTimes();
    }

    static void testClearOnEmptyQueue() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.clear();
        assert queue.isEmpty() : "Queue should be empty after clear()";
    }

    static void testClearOnPopulatedQueue() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.offer("a");
        queue.offer("b");
        queue.offer("c");
        queue.clear();
        assert queue.isEmpty() : "Queue should be empty after clear()";
        try {
            queue.element();
            assert false : "element() should throw NoSuchElementException after clear()";
        } catch (NoSuchElementException e) {
            // expected
        }
    }

    static void testClearMultipleTimes() {
        LinkedBlockingQueue<Double> queue = new LinkedBlockingQueue<>();
        queue.offer(1.1);
        queue.offer(2.2);
        queue.clear();
        queue.clear();
        assert queue.isEmpty() : "Queue should remain empty after multiple clears";
    }
}
