/*
 * @test    @summary Test hasNext() method of ArrayBlockingQueue
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testHasNextReturnsTrueWhenNextItemIsNotNull();
        testHasNextReturnsFalseWhenNextItemIsNull();
    }

    static void testHasNextReturnsTrueWhenNextItemIsNotNull() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        // Simulate internal state: nextItem != null
        // Since nextItem is private, we test behavior indirectly by subclassing or reflection
        // but as per constraints, we test observable behavior.
        // Here, we add an element and check hasNext() returns true.
        queue.add(1);
        boolean result = queue.hasNext();
        if (!result) {
            throw new AssertionError("Expected hasNext() to return true when nextItem is not null.");
        }
    }

    static void testHasNextReturnsFalseWhenNextItemIsNull() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        // When queue is empty, hasNext() should return false.
        boolean result = queue.hasNext();
        if (result) {
            throw new AssertionError("Expected hasNext() to return false when nextItem is null.");
        }
    }
}
