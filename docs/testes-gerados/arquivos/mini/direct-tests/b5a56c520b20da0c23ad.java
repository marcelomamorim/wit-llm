/*
 * @test
 * @summary Test ConcurrentLinkedDeque remove(Object) method behavior
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.NoSuchElementException;

public class ConcurrentLinkedDequeWitupTest {
    public static void main(String[] args) {
        testRemoveExistingElement();
        testRemoveNonExistingElement();
        testRemoveNullElement();
        testRemoveFromEmptyDeque();
    }

    private static void testRemoveExistingElement() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");
        boolean removed = deque.remove("b");
        if (!removed) {
            throw new AssertionError("Expected to remove existing element 'b'");
        }
        if (deque.contains("b")) {
            throw new AssertionError("Element 'b' should no longer be in deque");
        }
        if (deque.size() != 2) {
            throw new AssertionError("Deque size should be 2 after removal");
        }
    }

    private static void testRemoveNonExistingElement() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("x");
        deque.add("y");
        boolean removed = deque.remove("z");
        if (removed) {
            throw new AssertionError("Expected remove to return false for non-existing element 'z'");
        }
        if (deque.size() != 2) {
            throw new AssertionError("Deque size should remain 2 when removing non-existing element");
        }
    }

    private static void testRemoveNullElement() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("foo");
        try {
            deque.remove(null);
            throw new AssertionError("Expected NullPointerException when removing null");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testRemoveFromEmptyDeque() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        boolean removed = deque.remove("anything");
        if (removed) {
            throw new AssertionError("Expected remove to return false when removing from empty deque");
        }
    }
}
