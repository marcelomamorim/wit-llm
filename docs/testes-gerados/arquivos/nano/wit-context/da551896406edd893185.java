/*
 * @test
 * @summary Test for PriorityQueue.tryAdvance with null Consumer
 * @run main PriorityQueueWitupTest
 */

import java.util.PriorityQueue;
import java.util.function.Consumer;

public class PriorityQueueWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullConsumer();
        testTryAdvanceWithEmptyQueue();
        testTryAdvanceWithValidConsumer();
    }

    static void testTryAdvanceWithNullConsumer() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        Consumer<Integer> consumer = null;
        boolean threw = false;
        try {
            pq.tryAdvance(consumer);
        } catch (NullPointerException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NullPointerException when consumer is null");
        }
    }

    static void testTryAdvanceWithEmptyQueue() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        Consumer<Integer> consumer = e -> {};
        boolean result = pq.tryAdvance(consumer);
        if (result != false) {
            throw new AssertionError("Expected false when queue is empty");
        }
    }

    static void testTryAdvanceWithValidConsumer() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(42);
        final boolean[] called = {false};
        Consumer<Integer> consumer = e -> { called[0] = true; };
        boolean result = pq.tryAdvance(consumer);
        if (result != true) {
            throw new AssertionError("Expected true when queue has elements");
        }
        if (!called[0]) {
            throw new AssertionError("Consumer was not called");
        }
    }
}
