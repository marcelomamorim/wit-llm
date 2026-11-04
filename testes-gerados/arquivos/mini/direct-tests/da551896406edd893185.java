/*
 * @test
 * @summary Test PriorityQueue.tryAdvance(Consumer) behavior
 * @run main PriorityQueueWitupTest
 */

import java.util.PriorityQueue;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;

public class PriorityQueueWitupTest {
    public static void main(String[] args) {
        testTryAdvanceWithNullAction();
        testTryAdvanceOnEmptyQueue();
        testTryAdvanceOnNonEmptyQueue();
        testTryAdvanceConcurrentModification();
    }

    static void testTryAdvanceWithNullAction() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        try {
            pq.tryAdvance(null);
            throw new Error("Expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testTryAdvanceOnEmptyQueue() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        // tryAdvance should return false when queue is empty
        boolean result = pq.tryAdvance(e -> { throw new Error("Should not be called"); });
        if (result) {
            throw new Error("tryAdvance returned true on empty queue");
        }
    }

    static void testTryAdvanceOnNonEmptyQueue() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        pq.add("a");
        pq.add("b");
        final StringBuilder sb = new StringBuilder();
        boolean result = pq.tryAdvance(sb::append);
        if (!result) {
            throw new Error("tryAdvance returned false on non-empty queue");
        }
        String consumed = sb.toString();
        if (!consumed.equals("a") && !consumed.equals("b")) {
            throw new Error("tryAdvance consumed unexpected element: " + consumed);
        }
    }

    static void testTryAdvanceConcurrentModification() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        pq.add("a");
        pq.add("b");

        // We need to call tryAdvance once to initialize fence and expectedModCount
        pq.tryAdvance(e -> {});

        // Modify the queue to cause ConcurrentModificationException on next tryAdvance
        pq.add("c");

        try {
            pq.tryAdvance(e -> {});
            throw new Error("Expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
