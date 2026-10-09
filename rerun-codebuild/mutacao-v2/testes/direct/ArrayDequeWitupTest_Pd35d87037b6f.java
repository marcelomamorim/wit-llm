/*
 * @test
 * @summary Test java.util.ArrayDeque addLast(E e) method behavior
 * @run main ArrayDequeWitupTest_Pd35d87037b6f
 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest_Pd35d87037b6f {
    public static void main(String[] args) {
        testAddLastNullThrows();
        testAddLastIncreasesSize();
        testAddLastTriggersGrow();
    }

    private static void testAddLastNullThrows() {
        ArrayDeque<Object> deque = new ArrayDeque<>();
        try {
            deque.addLast(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testAddLastIncreasesSize() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        int initialSize = deque.size();
        deque.addLast("test");
        if (deque.size() != initialSize + 1) {
            throw new Error("Size did not increase after addLast");
        }
        String last = deque.removeLast();
        if (!"test".equals(last)) {
            throw new Error("Element added by addLast not found at last position");
        }
    }

    private static void testAddLastTriggersGrow() {
        // Create a small capacity deque to force grow
        ArrayDeque<Integer> deque = new ArrayDeque<>(1);
        deque.addLast(1);
        // Adding second element should trigger grow
        deque.addLast(2);
        if (deque.size() != 2) {
            throw new Error("Size incorrect after grow triggered by addLast");
        }
        int last = deque.removeLast();
        if (last != 2) {
            throw new Error("Last element incorrect after grow");
        }
        int first = deque.removeLast();
        if (first != 1) {
            throw new Error("First element incorrect after grow");
        }
    }
}
