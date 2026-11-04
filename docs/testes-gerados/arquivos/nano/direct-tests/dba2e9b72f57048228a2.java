/*
 * @test    @summary Test ArrayBlockingQueue.contains() method for correctness
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.Arrays;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testContainsWithExistingElement();
        testContainsWithNonExistingElement();
        testContainsWithNull();
        testContainsInEmptyQueue();
    }

    static void testContainsWithExistingElement() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(5);
        queue.add("apple");
        queue.add("banana");
        queue.add("cherry");
        boolean result = queue.contains("banana");
        if (!result) {
            throw new AssertionError("Expected queue to contain 'banana'");
        }
    }

    static void testContainsWithNonExistingElement() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        queue.add("dog");
        queue.add("cat");
        boolean result = queue.contains("mouse");
        if (result) {
            throw new AssertionError("Did not expect queue to contain 'mouse'");
        }
    }

    static void testContainsWithNull() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
        queue.add("hello");
        boolean result = queue.contains(null);
        if (result) {
            throw new AssertionError("Did not expect queue to contain null");
        }
    }

    static void testContainsInEmptyQueue() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
        boolean result = queue.contains("anything");
        if (result) {
            throw new AssertionError("Did not expect queue to contain any element");
        }
    }
}
