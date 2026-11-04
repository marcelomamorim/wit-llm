/*
 * @test
 * @summary Test LinkedTransferQueue.tryAdvance(Consumer) basic and edge cases
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;
import java.util.function.Consumer;
import java.util.concurrent.atomic.AtomicBoolean;

public class LinkedTransferQueueWitupTest {
    public static void main(String[] args) {
        testTryAdvanceWithElements();
        testTryAdvanceEmptyQueue();
        testTryAdvanceNullAction();
    }

    static void testTryAdvanceWithElements() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.add("one");
        queue.add("two");

        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            boolean first = true;
            public void accept(String s) {
                if (first) {
                    sb.append(s);
                    first = false;
                } else {
                    throw new AssertionError("tryAdvance should only accept one element per call");
                }
            }
        };

        boolean advanced = queue.tryAdvance(consumer);
        if (!advanced) {
            throw new AssertionError("tryAdvance should return true when element is present");
        }
        if (!"one".equals(sb.toString()) && !"two".equals(sb.toString())) {
            throw new AssertionError("tryAdvance accepted unexpected element: " + sb.toString());
        }

        // Subsequent call should also succeed and accept the next element
        final AtomicBoolean acceptedSecond = new AtomicBoolean(false);
        boolean secondAdvanced = queue.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                acceptedSecond.set(true);
            }
        });
        if (!secondAdvanced) {
            throw new AssertionError("tryAdvance should return true for second element");
        }
        if (!acceptedSecond.get()) {
            throw new AssertionError("Consumer for second element was not called");
        }
    }

    static void testTryAdvanceEmptyQueue() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        boolean advanced = queue.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                throw new AssertionError("Consumer should not be called on empty queue");
            }
        });
        if (advanced) {
            throw new AssertionError("tryAdvance should return false when queue is empty");
        }
    }

    static void testTryAdvanceNullAction() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        try {
            queue.tryAdvance(null);
            throw new AssertionError("tryAdvance should throw NullPointerException when action is null");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
