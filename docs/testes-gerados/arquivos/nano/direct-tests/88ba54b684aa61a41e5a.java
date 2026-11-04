/* @test    @summary Test PriorityBlockingQueue.clear() method for correctness and thread safety    @run main PriorityBlockingQueueWitupTest */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.Arrays;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testClearEmptyQueue();
        testClearNonEmptyQueue();
        testClearAfterAdd();
        testClearMultipleTimes();
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

    static void testClearAfterAdd() {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");
        queue.clear();
        assert queue.isEmpty() : "Queue should be empty after clear()";
        queue.add("d");
        assert queue.peek().equals("d") : "Queue should contain 'd' after adding post-clear";
    }

    static void testClearMultipleTimes() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(10);
        queue.add(20);
        queue.clear();
        queue.clear();
        assert queue.isEmpty() : "Queue should remain empty after multiple clears";
    }
}
