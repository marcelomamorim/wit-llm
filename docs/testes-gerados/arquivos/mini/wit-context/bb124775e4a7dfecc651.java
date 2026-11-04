/*
 * @test
 * @summary Test LinkedBlockingDeque(int capacity) constructor for valid and invalid capacities
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        testValidCapacity();
        testZeroCapacity();
        testNegativeCapacity();
        System.out.println("All tests passed.");
    }

    private static void testValidCapacity() {
        int capacity = 10;
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(capacity);
        if (deque == null) {
            throw new AssertionError("Constructor returned null for valid capacity");
        }
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
