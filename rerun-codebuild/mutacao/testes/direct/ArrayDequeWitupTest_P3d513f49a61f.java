/*
 * @test
 * @summary Test addFirst method of ArrayDeque for null and normal elements
 * @run main ArrayDequeWitupTest_P3d513f49a61f
 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest_P3d513f49a61f {

    public static void main(String[] args) {
        testAddFirstWithNull();
        testAddFirstWithElement();
    }

    static void testAddFirstWithNull() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.addFirst(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when adding null with addFirst") ;
        }
    }

    static void testAddFirstWithElement() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        String element = "test";
        deque.addFirst(element);
        if (deque.isEmpty()) {
            throw new AssertionError("Deque should not be empty after addFirst") ;
        }
        String first = deque.peekFirst();
        if (!element.equals(first)) {
            throw new AssertionError("First element should be 'test', but was: " + first);
        }
    }
}
