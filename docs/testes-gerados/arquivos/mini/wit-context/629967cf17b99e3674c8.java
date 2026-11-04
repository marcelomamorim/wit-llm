/*
 * @test
 * @summary Test ConcurrentLinkedQueue.tryAdvance method behavior and null argument handling
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

public class ConcurrentLinkedQueueWitupTest {
    public static void main(String[] args) {
        testTryAdvanceWithNull();
        testTryAdvanceWithEmptyQueue();
        testTryAdvanceWithNonEmptyQueue();
    }

    private static void testTryAdvanceWithNull() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        try {
            queue.tryAdvance(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testTryAdvanceWithEmptyQueue() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        boolean result = queue.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                throw new AssertionError("Should not be called on empty queue");
            }
        });
        if (result) {
            throw new AssertionError("tryAdvance should return false on empty queue");
        }
    }

    private static void testTryAdvanceWithNonEmptyQueue() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.add("test-element");
        final boolean[] accepted = {false};
        boolean result = queue.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                if (!"test-element".equals(s)) {
                    throw new AssertionError("Unexpected element: " + s);
                }
                accepted[0] = true;
            }
        });
        if (!result) {
            throw new AssertionError("tryAdvance should return true when element is present");
        }
        if (!accepted[0]) {
            throw new AssertionError("Consumer.accept was not called");
        }
    }
}
