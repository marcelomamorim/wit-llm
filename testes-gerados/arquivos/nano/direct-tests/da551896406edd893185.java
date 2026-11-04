/* @test    @summary Test PriorityQueue.tryAdvance method for normal and edge cases    @run main PriorityQueueWitupTest */

import java.util.PriorityQueue;
import java.util.function.Consumer;

public class PriorityQueueWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNonNullConsumer();
        testTryAdvanceWithNullConsumer();
        testTryAdvanceWithEmptyQueue();
        testTryAdvanceWithModifiedQueue();
    }

    static void testTryAdvanceWithNonNullConsumer() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(1);
        pq.offer(2);
        Consumer<Integer> consumer = (Integer e) -> {
            assert e != null : "Element should not be null";
        };
        boolean result = pq.tryAdvance(consumer);
        assert result : "tryAdvance should return true when element is processed";
    }

    static void testTryAdvanceWithNullConsumer() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(1);
        try {
            pq.tryAdvance(null);
            assert false : "NullPointerException expected";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testTryAdvanceWithEmptyQueue() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        Consumer<Integer> consumer = (Integer e) -> {};
        boolean result = pq.tryAdvance(consumer);
        assert !result : "tryAdvance should return false when queue is empty";
    }

    static void testTryAdvanceWithModifiedQueue() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(1);
        Consumer<Integer> consumer = (Integer e) -> {};
        // Simulate modification by removing element
        pq.poll();
        try {
            pq.tryAdvance(consumer);
            // No exception expected, but the internal modCount check may throw
            // However, since modCount is internal, we cannot access it directly.
            // So, this test assumes that if the queue was modified, the method may throw.
            // But in this implementation, no exception is thrown here.
        } catch (ConcurrentModificationException e) {
            // expected if internal modCount check triggers
        }
    }
}
