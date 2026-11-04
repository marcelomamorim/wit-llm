/*
 * @test    @summary Test for PriorityBlockingQueue.poll() method
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.NoSuchElementException;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testPollEmptyQueue();
        testPollWithElements();
    }

    private static void testPollEmptyQueue() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        Object result = queue.poll();
        if (result != null) {
            throw new AssertionError("Expected null when polling empty queue, but got: " + result);
        }
    }

    private static void testPollWithElements() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(10);
        queue.add(5);
        queue.add(20);
        Integer polled = queue.poll();
        if (polled == null || !polled.equals(5)) {
            throw new AssertionError("Expected to poll the smallest element (5), but got: " + polled);
        }
        // Poll remaining elements to ensure order
        Integer second = queue.poll();
        Integer third = queue.poll();
        if (second == null || third == null) {
            throw new AssertionError("Queue should not be empty yet");
        }
        if (second.equals(10) && third.equals(20)) {
            // OK
        } else {
            throw new AssertionError("Unexpected order after polling: " + second + ", " + third);
        }
    }
}
