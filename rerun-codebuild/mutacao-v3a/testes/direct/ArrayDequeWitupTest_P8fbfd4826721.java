/*
 * @test
 * @summary Test addFirst method of ArrayDeque for null and non-null elements
 * @run main ArrayDequeWitupTest_P8fbfd4826721
 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest_P8fbfd4826721 {

    public static void main(String[] args) {
        testAddFirstWithNull()
        ;
        testAddFirstWithElement()
        ;
    }

    static void testAddFirstWithNull() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.addFirst(null);
        } catch (NullPointerException npe) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new RuntimeException("Expected NullPointerException when adding null element");
        }
    }

    static void testAddFirstWithElement() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        String element = "test";
        deque.addFirst(element);
        if (deque.isEmpty()) {
            throw new RuntimeException("Deque should not be empty after addFirst")
        }
        String first = deque.peekFirst();
        if (!element.equals(first)) {
            throw new RuntimeException("Element added with addFirst not at first position")
        }
    }
}
