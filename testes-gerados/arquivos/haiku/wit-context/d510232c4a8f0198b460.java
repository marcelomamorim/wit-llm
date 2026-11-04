/*
 * @test
 * @summary Witup-derived behavioral tests for ArrayBlockingQueue.offer
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testNullElementThrowsNPE();
        testOfferOnFullQueueReturnsFalse();
        testOfferAddsAtTail();
        testFairConstructorOfferBehavior();
        testOfferAfterDrainSucceeds();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullElementThrowsNPE() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
        boolean thrown = false;
        try {
            queue.offer(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        check(thrown, "offer(null) must throw NullPointerException");
        check(queue.isEmpty(), "queue must remain empty after rejected null offer");
    }

    static void testOfferOnFullQueueReturnsFalse() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(1);
        check(queue.offer("a"), "first offer must succeed");
        check(!queue.offer("b"), "offer on full queue must return false");
        check(queue.size() == 1, "size must stay at capacity");
        check("a".equals(queue.peek()), "head must remain the original element");
    }

    static void testOfferAddsAtTail() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        check(queue.offer("a"), "offer a");
        check(queue.offer("b"), "offer b");
        check(queue.offer("c"), "offer c");
        check("a".equals(queue.poll()), "FIFO order: first must be a");
        check("b".equals(queue.poll()), "FIFO order: second must be b");
        check("c".equals(queue.poll()), "FIFO order: third must be c");
        check(queue.poll() == null, "queue must be empty");
    }

    static void testFairConstructorOfferBehavior() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(1, true);
        check(queue.offer("x"), "offer on fair queue with space must succeed");
        check(!queue.offer("y"), "offer on full fair queue must return false");
        check("x".equals(queue.peek()), "head must be x");
    }

    static void testOfferAfterDrainSucceeds() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(1);
        check(queue.offer("a"), "first offer must succeed");
        check("a".equals(queue.poll()), "poll must return a");
        check(queue.offer("b"), "offer after drain must succeed");
        check("b".equals(queue.peek()), "head must be b");
    }
}
