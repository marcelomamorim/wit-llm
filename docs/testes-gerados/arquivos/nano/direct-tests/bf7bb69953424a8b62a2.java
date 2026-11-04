/*
 * @test    @summary Test peek() method of ArrayBlockingQueue for correct behavior
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.NoSuchElementException;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testPeekOnEmptyQueue();
        testPeekOnNonEmptyQueue();
    }

    static void testPeekOnEmptyQueue() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        String result = queue.peek();
        if (result != null) {
            throw new AssertionError("Expected null when peeking empty queue, but got: " + result);
        }
    }

    static void testPeekOnNonEmptyQueue() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        queue.offer("first");
        queue.offer("second");
        String result = queue.peek();
        if (!"first".equals(result)) {
            throw new AssertionError("Expected 'first' when peeking, but got: " + result);
        }
        // Ensure the queue's head remains unchanged after peek
        String head = queue.peek();
        if (!"first".equals(head)) {
            throw new AssertionError("Queue head changed after peek, expected 'first', got: " + head);
        }
    }
}
