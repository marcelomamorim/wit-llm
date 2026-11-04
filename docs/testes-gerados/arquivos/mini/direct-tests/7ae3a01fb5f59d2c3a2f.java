/*
 * @test
 * @summary Test java.util.ArrayDeque addFirst method behavior
 * @run main java.util.ArrayDequeWitupTest
 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {
    public static void main(String[] args) {
        testAddFirstNull();
        testAddFirstBasic();
        testAddFirstGrow();
    }

    private static void testAddFirstNull() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        try {
            deque.addFirst(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testAddFirstBasic() {
        ArrayDeque<String> deque = new ArrayDeque<>(4);
        deque.addFirst("a");
        deque.addFirst("b");
        // The first element added last should be at head
        String first = deque.removeFirst();
        if (!"b".equals(first)) {
            throw new RuntimeException("Expected 'b' but got: " + first);
        }
        String second = deque.removeFirst();
        if (!"a".equals(second)) {
            throw new RuntimeException("Expected 'a' but got: " + second);
        }
    }

    private static void testAddFirstGrow() {
        // Create a small deque to force grow
        ArrayDeque<Integer> deque = new ArrayDeque<>(2);
        // Fill to capacity
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3); // This should trigger grow
        // Check order after grow
        int first = deque.removeFirst();
        int second = deque.removeFirst();
        int third = deque.removeFirst();
        if (first != 3 || second != 2 || third != 1) {
            throw new RuntimeException("Order after grow incorrect: " + first + ", " + second + ", " + third);
        }
    }
}
