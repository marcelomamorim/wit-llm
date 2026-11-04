/* @test    @summary Test for ArrayDeque.push method, including null input handling    @run main ArrayDequeWitupTest */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testPushWithValidElement();
        testPushWithNullElement();
    }

    private static void testPushWithValidElement() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        String element = "test";
        deque.push(element);
        if (deque.isEmpty()) {
            throw new AssertionError("Deque should not be empty after push with valid element");
        }
        String first = deque.peekFirst();
        if (first == null || !first.equals(element)) {
            throw new AssertionError("First element should be the pushed element");
        }
    }

    private static void testPushWithNullElement() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        try {
            deque.push(null);
            throw new AssertionError("Expected NullPointerException when pushing null");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
