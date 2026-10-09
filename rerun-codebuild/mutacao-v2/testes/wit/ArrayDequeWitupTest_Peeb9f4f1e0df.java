/*
 * @test
 * @summary Test deterministic behavior of ArrayDeque.addFirst(E) including null rejection
 * @run main ArrayDequeWitupTest_Peeb9f4f1e0df
 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest_Peeb9f4f1e0df {
    public static void main(String[] args) {
        testAddFirstRejectsNull();
        testAddFirstAddsElement();
        testAddFirstTriggersGrow();
    }

    private static void testAddFirstRejectsNull() {
        ArrayDeque<Object> deque = new ArrayDeque<>();
        try {
            deque.addFirst(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testAddFirstAddsElement() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.addFirst("first");
        if (!"first".equals(deque.peekFirst())) {
            throw new RuntimeException("addFirst did not add element at front");
        }
    }

    private static void testAddFirstTriggersGrow() {
        // Create deque with capacity 1 to force grow on second addFirst
        ArrayDeque<Integer> deque = new ArrayDeque<>(1);
        deque.addFirst(1); // fills the deque
        deque.addFirst(2); // should trigger grow
        if (deque.size() != 2) {
            throw new RuntimeException("addFirst did not grow deque correctly");
        }
        Integer first = deque.pollFirst();
        Integer second = deque.pollFirst();
        if (first == null || second == null) {
            throw new RuntimeException("Deque elements missing after grow");
        }
        if (!(first == 2 && second == 1)) {
            throw new RuntimeException("Deque order incorrect after grow");
        }
    }
}
