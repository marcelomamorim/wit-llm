/*
 * @test
 * @summary Tests for java.util.ArrayDeque.addLast(E e)
 * @run main ArrayDequeWitupTest_P4c3dc95c8757
 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest_P4c3dc95c8757 {

    public static void main(String[] args) {
        testAddLastWithNull();
        testAddLastWithValidElement();
    }

    public static void testAddLastWithNull() {
        ArrayDeque<Object> deque = new ArrayDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.addLast(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when adding null element");
        }
    }

    public static void testAddLastWithValidElement() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        String element = "test";
        deque.addLast(element);
        if (deque.isEmpty()) {
            throw new AssertionError("Deque should not be empty after addLast");
        }
        String firstElement = deque.peekFirst();
        if (!element.equals(firstElement)) {
            throw new AssertionError("Element added via addLast should be at the deque's front");
        }
    }
}
