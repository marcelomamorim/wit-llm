/*
 * @test
 * @summary Test LinkedBlockingDeque.remainingCapacity() method
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        testRemainingCapacityDefaultConstructor();
        testRemainingCapacityWithCapacity();
        testRemainingCapacityAfterAddAndRemove();
    }

    static void testRemainingCapacityDefaultConstructor() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        int remaining = deque.remainingCapacity();
        // Default capacity is Integer.MAX_VALUE
        if (remaining != Integer.MAX_VALUE) {
            throw new AssertionError("Expected remainingCapacity to be Integer.MAX_VALUE but was " + remaining);
        }
    }

    static void testRemainingCapacityWithCapacity() {
        int capacity = 10;
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(capacity);
        int remaining = deque.remainingCapacity();
        if (remaining != capacity) {
            throw new AssertionError("Expected remainingCapacity to be " + capacity + " but was " + remaining);
        }
    }

    static void testRemainingCapacityAfterAddAndRemove() throws InterruptedException {
        int capacity = 5;
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(capacity);
        // Initially full capacity
        if (deque.remainingCapacity() != capacity) {
            throw new AssertionError("Initial remainingCapacity incorrect");
        }
        // Add elements
        for (int i = 0; i < capacity; i++) {
            boolean added = deque.offer(i);
            if (!added) {
                throw new AssertionError("Failed to add element " + i);
            }
        }
        // Now remainingCapacity should be 0
        if (deque.remainingCapacity() != 0) {
            throw new AssertionError("Expected remainingCapacity 0 after filling deque but was " + deque.remainingCapacity());
        }
        // Remove one element
        Integer removed = deque.poll();
        if (removed == null) {
            throw new AssertionError("Expected to remove an element but got null");
        }
        // Now remainingCapacity should be 1
        if (deque.remainingCapacity() != 1) {
            throw new AssertionError("Expected remainingCapacity 1 after removing one element but was " + deque.remainingCapacity());
        }
    }
}
