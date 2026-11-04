/*
 * @test
 * @summary Test for ArrayBlockingQueue.remove(Object o) method behavior.
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.NoSuchElementException;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testRemoveExistingElement();
        testRemoveNullElement();
        testRemoveNonExistingElement();
    }

    static void testRemoveExistingElement() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        queue.offer("element1");
        queue.offer("element2");
        boolean removed = queue.remove("element1");
        if (!removed) {
            throw new AssertionError("Expected to remove existing element, but did not.");
        }
        if (queue.contains("element1")) {
            throw new AssertionError("Element should be removed from queue.");
        }
    }

    static void testRemoveNullElement() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        queue.offer("element1");
        boolean removed = queue.remove(null);
        if (removed) {
            throw new AssertionError("Removing null should return false.");
        }
        if (!queue.contains("element1")) {
            throw new AssertionError("Existing element should remain after removing null.");
        }
    }

    static void testRemoveNonExistingElement() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        queue.offer("element1");
        boolean removed = queue.remove("nonexistent");
        if (removed) {
            throw new AssertionError("Removing non-existing element should return false.");
        }
        if (!queue.contains("element1")) {
            throw new AssertionError("Existing element should remain after removing non-existing element.");
        }
    }
}
