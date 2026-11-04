/* @test    @summary Test constructor with valid capacity value    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testConstructorWithPositiveCapacity();
        testConstructorWithZeroCapacity();
        testConstructorWithNegativeCapacity();
    }

    static void testConstructorWithPositiveCapacity() {
        int capacity = 10;
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(capacity);
        assert deque != null : "Deque should be instantiated with positive capacity";
    }

    static void testConstructorWithZeroCapacity() {
        try {
            new LinkedBlockingDeque<>(0);
            assert false : "Expected IllegalArgumentException for zero capacity";
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static void testConstructorWithNegativeCapacity() {
        try {
            new LinkedBlockingDeque<>(-5);
            assert false : "Expected IllegalArgumentException for negative capacity";
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
