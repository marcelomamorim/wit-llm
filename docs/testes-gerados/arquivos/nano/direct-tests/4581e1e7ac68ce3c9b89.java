/* @test    @summary Test the add method of ArrayDeque for basic functionality    @run main ArrayDequeWitupTest */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testAddWithSingleElement();
        testAddWithMultipleElements();
        testAddNullElement();
    }

    private static void testAddWithSingleElement() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        boolean result = deque.add(10);
        if (!result) {
            throw new AssertionError("add() should return true for successful addition");
        }
        if (deque.isEmpty()) {
            throw new AssertionError("Deque should not be empty after add()");
        }
        Integer first = deque.peekFirst();
        if (first == null || first != 10) {
            throw new AssertionError("First element should be 10 after add()");
        }
    }

    private static void testAddWithMultipleElements() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");
        if (deque.size() != 3) {
            throw new AssertionError("Deque size should be 3 after three adds");
        }
        String first = deque.peekFirst();
        String last = deque.peekLast();
        if (!"a".equals(first) || !"c".equals(last)) {
            throw new AssertionError("First should be 'a' and last should be 'c'");
        }
    }

    private static void testAddNullElement() {
        ArrayDeque<Object> deque = new ArrayDeque<>();
        try {
            deque.add(null);
        } catch (NullPointerException e) {
            return; // Expected, as null elements are prohibited
        }
        throw new AssertionError("Adding null should throw NullPointerException");
    }
}
