/*
 * @test
 * @summary Test PriorityQueue.tryAdvance(Consumer) for null action and normal iteration
 * @run main PriorityQueueWitupTest
 */

import java.util.PriorityQueue;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;

public class PriorityQueueWitupTest {
    public static void main(String[] args) {
        testTryAdvanceNullAction();
        testTryAdvanceNormalIteration();
        testTryAdvanceConcurrentModification();
    }

    private static void testTryAdvanceNullAction() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        pq.add("a");
        try {
            pq.tryAdvance(null);
            throw new Error("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testTryAdvanceNormalIteration() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        pq.add("a");
        pq.add("b");
        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        boolean advanced1 = pq.tryAdvance(consumer);
        boolean advanced2 = pq.tryAdvance(consumer);
        boolean advanced3 = pq.tryAdvance(consumer);

        if (!advanced1 || !advanced2) {
            throw new Error("Expected two successful tryAdvance calls");
        }
        if (advanced3) {
            throw new Error("Expected third tryAdvance call to return false");
        }
        String result = sb.toString();
        if (!(result.equals("ab") || result.equals("ba"))) {
            throw new Error("Unexpected iteration order: " + result);
        }
    }

    private static void testTryAdvanceConcurrentModification() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        pq.add("a");
        pq.add("b");
        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        // Start iteration to initialize fence and expectedModCount
        boolean advanced = pq.tryAdvance(consumer);
        if (!advanced) {
            throw new Error("Expected first tryAdvance call to succeed");
        }

        // Modify the queue to cause ConcurrentModificationException on next tryAdvance
        pq.add("c");

        try {
            pq.tryAdvance(consumer);
            throw new Error("Expected ConcurrentModificationException after modification");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
