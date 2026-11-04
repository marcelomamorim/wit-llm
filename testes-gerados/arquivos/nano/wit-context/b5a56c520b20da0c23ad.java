/*
 * @test    @summary Test for ConcurrentLinkedDeque.remove(Object o) method.
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.NoSuchElementException;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testRemoveWithNull();
        testRemoveWithExistingElement();
        testRemoveWithNonExistingElement();
    }

    static void testRemoveWithNull() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        boolean result = false;
        try {
            result = deque.remove(null);
        } catch (NullPointerException e) {
            // Expected exception
        }
        if (result) {
            throw new AssertionError("Expected false when removing null, but got true.");
        }
    }

    static void testRemoveWithExistingElement() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("test");
        boolean result = deque.remove("test");
        if (!result) {
            throw new AssertionError("Failed to remove existing element.");
        }
        if (deque.contains("test")) {
            throw new AssertionError("Element still present after removal.");
        }
    }

    static void testRemoveWithNonExistingElement() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        boolean result = deque.remove("nonexistent");
        if (result) {
            throw new AssertionError("Removing non-existent element should return false.");
        }
    }
}
