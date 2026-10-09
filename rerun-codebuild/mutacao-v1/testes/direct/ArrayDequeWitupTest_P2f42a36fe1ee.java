/* @test    @summary Test addFirst method of ArrayDeque for null and normal elements    @run main ArrayDequeWitupTest_P2f42a36fe1ee */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest_P2f42a36fe1ee {

    public static void main(String[] args) {
        testAddFirstWithNull();
        testAddFirstWithElement();
    }

    static void testAddFirstWithNull() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        try {
            deque.addFirst(null);
            throw new AssertionError("Expected NullPointerException for null element");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    static void testAddFirstWithElement() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addFirst(42);
        if (deque.isEmpty()) {
            throw new AssertionError("Deque should not be empty after addFirst");
        }
        Integer first = deque.peekFirst();
        if (first == null || first != 42) {
            throw new AssertionError("First element should be 42, but was: " + first);
        }
    }
}
