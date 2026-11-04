/*
 * @test
 * @summary Test LinkedBlockingQueue.tryAdvance(Consumer) behavior
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue.Node;
import java.util.function.Consumer;
import java.util.Objects;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullAction();
        testTryAdvanceEmptyQueue();
        testTryAdvanceWithElements();
    }

    static void testTryAdvanceWithNullAction() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        try {
            queue.tryAdvance(null);
            throw new Error("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testTryAdvanceEmptyQueue() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        boolean result = queue.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                throw new Error("Should not be called on empty queue");
            }
        });
        if (result) {
            throw new Error("tryAdvance should return false on empty queue");
        }
    }

    static void testTryAdvanceWithElements() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("one");
        queue.add("two");

        final StringBuilder sb = new StringBuilder();

        // First tryAdvance should accept "one"
        boolean advanced1 = queue.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });
        if (!advanced1) {
            throw new Error("tryAdvance should return true when element is present");
        }
        if (!"one".equals(sb.toString())) {
            throw new Error("Expected 'one' but got: " + sb.toString());
        }

        // Second tryAdvance should accept "two"
        sb.setLength(0);
        boolean advanced2 = queue.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });
        if (!advanced2) {
            throw new Error("tryAdvance should return true when second element is present");
        }
        if (!"two".equals(sb.toString())) {
            throw new Error("Expected 'two' but got: " + sb.toString());
        }

        // Third tryAdvance should return false (no more elements)
        boolean advanced3 = queue.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                throw new Error("Should not be called when no more elements");
            }
        });
        if (advanced3) {
            throw new Error("tryAdvance should return false when no more elements");
        }
    }
}
