/* @test    @summary Test addLast method of ArrayDeque for basic functionality and null handling    @run main ArrayDequeWitupTest */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testAddLastWithValidElement();
        testAddLastWithNullElement();
    }

    private static void testAddLastWithValidElement() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.addLast("element");
        if (deque.isEmpty()) {
            throw new AssertionError("Deque should not be empty after addLast");
        }
        String first = deque.peekFirst();
        if (!"element".equals(first)) {
            throw new AssertionError("Expected 'element' at first position, but got: " + first);
        }
    }

    private static void testAddLastWithNullElement() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.addLast(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("addLast(null) should throw NullPointerException");
        }
    }
}
