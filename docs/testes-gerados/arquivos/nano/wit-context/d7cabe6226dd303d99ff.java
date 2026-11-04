/*
 * @test
 * @summary Test for PriorityBlockingQueue.contains(Object o)
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.NoSuchElementException;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testContainsWithNull();
        testContainsWithExistingElement();
        testContainsWithNonExistingElement();
    }

    private static void testContainsWithNull() {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
        try {
            boolean result = queue.contains(null);
            // Expect false, as null elements are not added
            if (result != false) {
                throw new AssertionError("Expected false for contains(null)");
            }
        } catch (NullPointerException e) {
            // Depending on implementation, may throw NullPointerException
            // Acceptable if so; otherwise, test fails
        }
    }

    private static void testContainsWithExistingElement() {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
        String element = "test";
        queue.add(element);
        boolean result = queue.contains(element);
        if (result != true) {
            throw new AssertionError("Expected true for contains(existingElement)");
        }
    }

    private static void testContainsWithNonExistingElement() {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
        queue.add("a");
        boolean result = queue.contains("b");
        if (result != false) {
            throw new AssertionError("Expected false for contains(nonExistingElement)");
        }
    }
}
