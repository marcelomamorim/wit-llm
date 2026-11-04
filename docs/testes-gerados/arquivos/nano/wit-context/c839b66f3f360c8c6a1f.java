/*
 * @test    @summary Test for remainingCapacity() method in LinkedBlockingDeque
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantLock;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testRemainingCapacity();
    }

    public static void testRemainingCapacity() {
        // Test with default capacity (Integer.MAX_VALUE)
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        int capacity = Integer.MAX_VALUE;
        int count = 0;
        int expectedRemaining = capacity - count;
        int actualRemaining = deque.remainingCapacity();
        if (actualRemaining != expectedRemaining) {
            throw new AssertionError("Expected remaining capacity: " + expectedRemaining + 
                ", but got: " + actualRemaining);
        }

        // Test with custom capacity
        int customCapacity = 10;
        LinkedBlockingDeque<Integer> deque2 = new LinkedBlockingDeque<>(customCapacity);
        // Fill the deque to capacity
        for (int i = 0; i < customCapacity; i++) {
            deque2.offer(i);
        }
        int remaining = deque2.remainingCapacity();
        if (remaining != 0) {
            throw new AssertionError("Expected remaining capacity: 0, but got: " + remaining);
        }

        // Remove an element and check remaining capacity
        deque2.poll();
        int remainingAfterPoll = deque2.remainingCapacity();
        if (remainingAfterPoll != 1) {
            throw new AssertionError("Expected remaining capacity: 1, but got: " + remainingAfterPoll);
        }
    }
}
