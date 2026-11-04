/*
 * @test    @summary Test for ArrayBlockingQueue.peek() method.
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.lang.AssertionError;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testPeekEmptyQueue();
        testPeekNonEmptyQueue();
    }

    static void testPeekEmptyQueue() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        String result = queue.peek();
        if (result != null) {
            throw new AssertionError("Expected null when peeking empty queue, but got: " + result);
        }
    }

    static void testPeekNonEmptyQueue() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        queue.offer("element1");
        queue.offer("element2");
        String result = queue.peek();
        if (result == null || !result.equals("element1")) {
            throw new AssertionError("Expected 'element1' when peeking, but got: " + result);
        }
        // Ensure that peek does not remove the element
        String first = queue.poll();
        if (first == null || !first.equals("element1")) {
            throw new AssertionError("Expected 'element1' when polling, but got: " + first);
        }
    }
}
