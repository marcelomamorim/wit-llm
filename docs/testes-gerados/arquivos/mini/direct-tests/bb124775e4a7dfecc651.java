/*
 * @test
 * @summary Test LinkedBlockingDeque constructor with capacity argument
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        testValidCapacity();
        testZeroCapacity();
        testNegativeCapacity();
    }

    private static void testValidCapacity() {
        int capacity = 10;
        LinkedBlockingDeque<Object> deque = new LinkedBlockingDeque<>(capacity);
        if (deque == null) throw new AssertionError("Deque instance is null");
    }

    private static void testZeroCapacity() {
        try {
            new LinkedBlockingDeque<>(0);
            throw new AssertionError("Expected IllegalArgumentException for zero capacity");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testNegativeCapacity() {
        try {
            new LinkedBlockingDeque<>(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative capacity");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
