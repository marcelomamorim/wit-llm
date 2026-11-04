/*
 * @test
 * @summary Deterministic test for PriorityBlockingQueue.poll() method
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();

        // Test poll() on empty queue returns null
        Integer result = queue.poll();
        if (result != null) {
            throw new AssertionError("poll() on empty queue should return null, but returned: " + result);
        }

        // Add elements and test poll() returns the smallest element
        queue.add(10);
        queue.add(5);
        queue.add(20);

        Integer first = queue.poll();
        if (first == null || first.intValue() != 5) {
            throw new AssertionError("poll() should return 5 as the smallest element, but returned: " + first);
        }

        Integer second = queue.poll();
        if (second == null || second.intValue() != 10) {
            throw new AssertionError("poll() should return 10 as the next smallest element, but returned: " + second);
        }

        Integer third = queue.poll();
        if (third == null || third.intValue() != 20) {
            throw new AssertionError("poll() should return 20 as the last element, but returned: " + third);
        }

        // After all elements are polled, poll() returns null again
        Integer emptyPoll = queue.poll();
        if (emptyPoll != null) {
            throw new AssertionError("poll() on empty queue after removals should return null, but returned: " + emptyPoll);
        }

        System.out.println("PriorityBlockingQueue.poll() tests passed.");
    }
}
