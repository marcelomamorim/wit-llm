/*
 * @test
 * @summary Test for ArrayDeque.addLast(E e) method, including null input.
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testAddLastWithValidElement();
        testAddLastWithNullElement();
    }

    static void testAddLastWithValidElement() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        String element = "test";
        deque.addLast(element);
        if (!deque.peekLast().equals(element)) {
            throw new AssertionError("addLast did not add element correctly");
        }
    }

    static void testAddLastWithNullElement() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        try {
            deque.addLast(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Test passes
        }
    }
}
