/*
 * @test
 * @summary Tests for ArrayBlockingQueue.contains method
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.NoSuchElementException;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testContainsWithNullObject();
        testContainsWithExistingElement();
        testContainsWithNonExistingElement();
    }

    private static void testContainsWithNullObject() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        boolean result = queue.contains(null);
        if (result != false) {
            throw new AssertionError("Expected false when checking contains(null)");
        }
    }

    private static void testContainsWithExistingElement() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        String element = "test";
        queue.offer(element);
        boolean result = queue.contains(element);
        if (result != true) {
            throw new AssertionError("Expected true when element is in queue");
        }
    }

    private static void testContainsWithNonExistingElement() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        queue.offer("a");
        queue.offer("b");
        boolean result = queue.contains("c");
        if (result != false) {
            throw new AssertionError("Expected false when element is not in queue");
        }
    }
}
