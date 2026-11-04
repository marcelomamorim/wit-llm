/*
 * @test
 * @summary Test ArrayDeque.push(E) for null argument and normal usage
 * @run main java.util.ArrayDequeWitupTest
 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {
    public static void main(String[] args) {
        testPushNull();
        testPushNormal();
    }

    private static void testPushNull() {
        ArrayDeque<Object> deque = new ArrayDeque<>();
        try {
            deque.push(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testPushNormal() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.push("first");
        deque.push("second");
        // The last pushed element should be at the front
        String first = deque.peekFirst();
        if (!"second".equals(first)) {
            throw new RuntimeException("push did not add element at front as expected");
        }
        // The size should be 2
        if (deque.size() != 2) {
            throw new RuntimeException("Unexpected size after pushes: " + deque.size());
        }
    }
}
