/*
 * @test
 * @summary Test the peek() method of LinkedBlockingQueue for correct behavior.
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.NoSuchElementException;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testPeekOnEmptyQueue();
        testPeekOnNonEmptyQueue();
        testPeekAfterRemove();
    }

    private static void testPeekOnEmptyQueue() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        String result = queue.peek();
        if (result != null) {
            throw new AssertionError("Expected null when peeking empty queue, but got: " + result);
        }
    }

    private static void testPeekOnNonEmptyQueue() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.offer("first");
        queue.offer("second");
        String result = queue.peek();
        if (result == null || !result.equals("first")) {
            throw new AssertionError("Expected 'first' when peeking, but got: " + result);
        }
    }

    private static void testPeekAfterRemove() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.offer("element");
        String peeked = queue.peek();
        if (peeked == null || !peeked.equals("element")) {
            throw new AssertionError("Expected 'element' when peeking, but got: " + peeked);
        }
        queue.poll();
        String resultAfterPoll = queue.peek();
        if (resultAfterPoll != null) {
            throw new AssertionError("Expected null after poll on queue, but got: " + resultAfterPoll);
        }
    }
}
