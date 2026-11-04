/*
 * @test
 * @summary Test PriorityBlockingQueue.tryAdvance method behavior
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.function.Consumer;
import java.util.Objects;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullConsumer();
        testTryAdvanceWithEmptyQueue();
        testTryAdvanceWithOneElement();
    }

    static void testTryAdvanceWithNullConsumer() {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
        try {
            queue.tryAdvance(null);
            throw new Error("Expected NullPointerException for null consumer");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testTryAdvanceWithEmptyQueue() {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
        // The queue is empty, so tryAdvance should return false
        boolean result = queue.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                throw new Error("Should not be called on empty queue");
            }
        });
        if (result) {
            throw new Error("tryAdvance returned true on empty queue");
        }
    }

    static void testTryAdvanceWithOneElement() {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
        queue.add("test");
        final StringBuilder sb = new StringBuilder();
        boolean result = queue.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });
        if (!result) {
            throw new Error("tryAdvance returned false when element present");
        }
        if (!"test".equals(sb.toString())) {
            throw new Error("Consumer did not receive correct element");
        }
        // Subsequent call should return false because index advanced
        boolean secondResult = queue.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                throw new Error("Should not be called after first element consumed");
            }
        });
        if (secondResult) {
            throw new Error("tryAdvance returned true after all elements consumed");
        }
    }
}
