/*
 * @test
 * @summary Test addLast method of ArrayDeque for null input and normal insertion
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {
    public static void main(String[] args) {
        testAddLastNull();
        testAddLastNormal();
    }

    private static void testAddLastNull() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        try {
            deque.addLast(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testAddLastNormal() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.addLast("first");
        deque.addLast("second");
        // Check size indirectly by polling elements
        String first = deque.pollFirst();
        String second = deque.pollFirst();
        if (!"first".equals(first) || !"second".equals(second)) {
            throw new RuntimeException("Elements not added or retrieved in correct order");
        }
        if (!deque.isEmpty()) {
            throw new RuntimeException("Deque should be empty after polling all elements");
        }
    }
}
