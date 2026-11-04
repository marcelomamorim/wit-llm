/*
 * @test
 * @summary Test ArrayDeque.add(E) for null element rejection and normal add
 * @run main java.util.ArrayDequeWitupTest
 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {
    public static void main(String[] args) {
        testAddNullThrowsNPE();
        testAddNonNullReturnsTrue();
    }

    private static void testAddNullThrowsNPE() {
        ArrayDeque<Object> deque = new ArrayDeque<>();
        try {
            deque.add(null);
            throw new AssertionError("Expected NullPointerException when adding null");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testAddNonNullReturnsTrue() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        boolean result = deque.add("test");
        if (!result) {
            throw new AssertionError("add should return true when adding non-null element");
        }
        if (!"test".equals(deque.removeLast())) {
            throw new AssertionError("Element added should be retrievable from the deque");
        }
    }
}
