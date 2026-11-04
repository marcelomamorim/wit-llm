/*
 * @test
 * @summary Test LinkedBlockingDeque.remainingCapacity() method behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<Integer>(10);

        // Initially empty, remainingCapacity should be equal to capacity
        int remaining = deque.remainingCapacity();
        if (remaining != 10) {
            throw new AssertionError("Expected remainingCapacity 10 but got " + remaining);
        }

        // Add elements and check remainingCapacity decreases accordingly
        deque.add(1);
        deque.add(2);
        remaining = deque.remainingCapacity();
        if (remaining != 8) {
            throw new AssertionError("Expected remainingCapacity 8 after adding 2 elements but got " + remaining);
        }

        // Fill to capacity and check remainingCapacity is zero
        for (int i = 0; i < 8; i++) {
            deque.add(i + 3);
        }
        remaining = deque.remainingCapacity();
        if (remaining != 0) {
            throw new AssertionError("Expected remainingCapacity 0 when full but got " + remaining);
        }

        // Remove one element and check remainingCapacity increases
        deque.take();
        remaining = deque.remainingCapacity();
        if (remaining != 1) {
            throw new AssertionError("Expected remainingCapacity 1 after removing one element but got " + remaining);
        }

        System.out.println("All tests passed.");
    }
}
