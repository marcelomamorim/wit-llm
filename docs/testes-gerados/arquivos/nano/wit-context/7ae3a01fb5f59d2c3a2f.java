/*
 * @test
 * @summary Tests for ArrayDeque.addFirst(E e)
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testAddFirstWithNull();
        testAddFirstWithValidElement();
    }

    public static void testAddFirstWithNull() {
        ArrayDeque<Object> deque = new ArrayDeque<>();
        try {
            deque.addFirst(null);
            // If no exception, test fails
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Test passes
        }
    }

    public static void testAddFirstWithValidElement() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        String element = "test";
        deque.addFirst(element);
        if (deque.isEmpty()) {
            throw new AssertionError("Deque should not be empty after addFirst")
        }
        String first = deque.peekFirst();
        if (!element.equals(first)) {
            throw new AssertionError("First element does not match added element")
        }
    }
}
