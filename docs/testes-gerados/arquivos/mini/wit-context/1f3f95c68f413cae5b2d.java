/*
 * @test
 * @summary Test LinkedBlockingDeque.addFirst(E) for null and capacity overflow
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        testAddFirstNull();
        testAddFirstCapacityOverflow();
    }

    private static void testAddFirstNull() {
        LinkedBlockingDeque<Object> deque = new LinkedBlockingDeque<>();
        try {
            deque.addFirst(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testAddFirstCapacityOverflow() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(1);
        deque.addFirst(1); // should succeed
        try {
            deque.addFirst(2); // should fail - capacity full
            throw new Error("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // expected
        }
    }
}
