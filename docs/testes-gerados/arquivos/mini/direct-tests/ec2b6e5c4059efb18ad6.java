/*
 * @test
 * @summary Test java.util.ArrayDeque.push(E) method
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {
    public static void main(String[] args) {
        testPushAddsElementAtFront();
        testPushNullThrowsNPE();
    }

    private static void testPushAddsElementAtFront() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.push("first");
        deque.push("second");

        // push adds elements at the front, so "second" should be first element
        String first = deque.peekFirst();
        if (!"second".equals(first)) {
            throw new AssertionError("Expected 'second' at front but got: " + first);
        }

        // size should be 2
        if (deque.size() != 2) {
            throw new AssertionError("Expected size 2 but got: " + deque.size());
        }
    }

    private static void testPushNullThrowsNPE() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        try {
            deque.push(null);
            throw new AssertionError("Expected NullPointerException when pushing null");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
