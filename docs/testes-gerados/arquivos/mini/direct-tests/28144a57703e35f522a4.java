/*
 * @test
 * @summary Test PriorityBlockingQueue.poll() method behavior
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testPollEmptyQueue();
        testPollNonEmptyQueue();
    }

    static void testPollEmptyQueue() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        Integer result = queue.poll();
        if (result != null) {
            throw new AssertionError("poll() on empty queue should return null");
        }
    }

    static void testPollNonEmptyQueue() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(10);
        queue.add(5);
        queue.add(20);

        Integer first = queue.poll();
        if (first == null || first != 5) {
            throw new AssertionError("poll() should return the smallest element 5, got: " + first);
        }

        Integer second = queue.poll();
        if (second == null || second != 10) {
            throw new AssertionError("poll() should return the next smallest element 10, got: " + second);
        }

        Integer third = queue.poll();
        if (third == null || third != 20) {
            throw new AssertionError("poll() should return the next smallest element 20, got: " + third);
        }

        Integer fourth = queue.poll();
        if (fourth != null) {
            throw new AssertionError("poll() on empty queue after removals should return null");
        }
    }
}
