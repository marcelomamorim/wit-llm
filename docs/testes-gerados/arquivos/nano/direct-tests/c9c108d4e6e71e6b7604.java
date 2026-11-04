/* @test    @summary Test for ArrayBlockingQueue.clear() method behavior    @run main ArrayBlockingQueueWitupTest */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.NoSuchElementException;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testClearOnEmptyQueue();
        testClearOnNonEmptyQueue();
        testClearMultipleTimes();
    }

    static void testClearOnEmptyQueue() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        queue.clear();
        assert queue.isEmpty() : "Queue should be empty after clear()";
    }

    static void testClearOnNonEmptyQueue() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(5);
        queue.offer("a");
        queue.offer("b");
        queue.offer("c");
        assert queue.size() == 3 : "Queue should have 3 elements before clear()";
        queue.clear();
        assert queue.isEmpty() : "Queue should be empty after clear()";
        assert queue.size() == 0 : "Queue size should be 0 after clear()";
    }

    static void testClearMultipleTimes() {
        ArrayBlockingQueue<Double> queue = new ArrayBlockingQueue<>(3);
        queue.offer(1.1);
        queue.offer(2.2);
        queue.clear();
        queue.clear();
        assert queue.isEmpty() : "Queue should remain empty after multiple clears";
        queue.offer(3.3);
        assert queue.peek() == 3.3 : "Queue should contain 3.3 after offer()";
    }
}
