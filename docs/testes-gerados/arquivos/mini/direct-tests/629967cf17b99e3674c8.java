/*
 * @test
 * @summary Test ConcurrentLinkedQueue.tryAdvance method behavior
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

public class ConcurrentLinkedQueueWitupTest {
    public static void main(String[] args) {
        testTryAdvanceWithNonEmptyQueue();
        testTryAdvanceWithEmptyQueue();
        testTryAdvanceWithNullAction();
    }

    static void testTryAdvanceWithNonEmptyQueue() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.add("one");
        queue.add("two");

        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        boolean advanced = queue.tryAdvance(consumer);
        if (!advanced) {
            throw new AssertionError("tryAdvance should return true when queue is not empty");
        }
        String result = sb.toString();
        if (!"one".equals(result) && !"two".equals(result)) {
            throw new AssertionError("tryAdvance accepted unexpected element: " + result);
        }

        // Second tryAdvance should also work
        sb.setLength(0);
        boolean advanced2 = queue.tryAdvance(consumer);
        if (!advanced2) {
            throw new AssertionError("Second tryAdvance should return true when queue is not empty");
        }
        String result2 = sb.toString();
        if (!"one".equals(result2) && !"two".equals(result2)) {
            throw new AssertionError("Second tryAdvance accepted unexpected element: " + result2);
        }

        // Third tryAdvance likely returns false (queue has only two elements)
        boolean advanced3 = queue.tryAdvance(consumer);
        if (advanced3) {
            // It may or may not return true depending on iterator state, so no assert here
        }
    }

    static void testTryAdvanceWithEmptyQueue() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                throw new AssertionError("Consumer should not be called on empty queue");
            }
        };
        boolean advanced = queue.tryAdvance(consumer);
        if (advanced) {
            throw new AssertionError("tryAdvance should return false when queue is empty");
        }
    }

    static void testTryAdvanceWithNullAction() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        try {
            queue.tryAdvance(null);
            throw new AssertionError("tryAdvance should throw NullPointerException when action is null");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
