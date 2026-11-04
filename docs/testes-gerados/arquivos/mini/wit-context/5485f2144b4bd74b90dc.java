/*
 * @test
 * @summary Test LinkedBlockingDeque.clear() method behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        // Add some elements
        deque.add("one");
        deque.add("two");
        deque.add("three");

        // Clear the deque
        deque.clear();

        // After clear, size should be zero
        if (deque.size() != 0) {
            throw new AssertionError("Deque size after clear() is not zero: " + deque.size());
        }

        // The deque should be empty
        if (!deque.isEmpty()) {
            throw new AssertionError("Deque is not empty after clear()");
        }

        // Polling should return null
        if (deque.poll() != null) {
            throw new AssertionError("poll() after clear() should return null");
        }

        // Try adding after clear to ensure deque still works
        deque.add("four");
        if (deque.size() != 1) {
            throw new AssertionError("Deque size after adding post-clear is not 1: " + deque.size());
        }

        System.out.println("Test passed.");
    }
}
