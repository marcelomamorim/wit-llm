/* @test    @summary Test peek() method of PriorityBlockingQueue for correct element retrieval without removal    @run main PriorityBlockingQueueWitupTest */

import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testPeekOnEmptyQueue();
        testPeekOnPopulatedQueue();
    }

    static void testPeekOnEmptyQueue() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        Integer result = queue.peek();
        if (result != null) {
            throw new AssertionError("Expected null when peeking empty queue, but got: " + result);
        }
    }

    static void testPeekOnPopulatedQueue() {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
        queue.add("element1");
        queue.add("element2");
        String peeked = queue.peek();
        if (peeked == null) {
            throw new AssertionError("Peek returned null on populated queue");
        }
        if (!peeked.equals("element1")) {
            throw new AssertionError("Expected 'element1', but got: " + peeked);
        }
        // Ensure the element is not removed
        if (queue.size() != 2) {
            throw new AssertionError("Queue size changed after peek");
        }
    }
}
