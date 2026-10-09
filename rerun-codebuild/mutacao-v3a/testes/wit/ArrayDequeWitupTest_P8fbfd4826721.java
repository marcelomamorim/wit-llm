/*
 * @test
 * @summary Tests for ArrayDeque's addFirst method.
 * @run main ArrayDequeWitupTest_P8fbfd4826721
 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest_P8fbfd4826721 {

    public static void main(String[] args) {
        testAddFirstWithNull();
        testAddFirstWithValidElement();
    }

    private static void testAddFirstWithNull() {
        ArrayDeque<Object> deque = new ArrayDeque<>();
        try {
            deque.addFirst(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Test passes
        }
    }

    private static void testAddFirstWithValidElement() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        String element = "test";
        deque.addFirst(element);
        if (deque.isEmpty()) {
            throw new AssertionError("Deque should not be empty after addFirst");
        }
        String first = deque.peekFirst();
        if (!element.equals(first)) {
            throw new AssertionError("First element mismatch: expected '" + element + "', got '" + first + "'");
        }
    }
}
