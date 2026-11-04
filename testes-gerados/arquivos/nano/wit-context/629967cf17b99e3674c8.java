/*
 * @test
 * @summary Tests for ConcurrentLinkedQueue.tryAdvance method behavior.
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

public class ConcurrentLinkedQueueWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullAction();
        testTryAdvanceWithEmptyQueue();
        testTryAdvanceWithSingleElement();
        testTryAdvanceWithMultipleElements();
    }

    static void testTryAdvanceWithNullAction() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        boolean exceptionThrown = false;
        try {
            queue.tryAdvance(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to tryAdvance");
        }
    }

    static void testTryAdvanceWithEmptyQueue() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        final boolean[] called = {false};
        boolean result = queue.tryAdvance(e -> {
            called[0] = true;
        });
        if (result) {
            throw new AssertionError("tryAdvance should return false on empty queue");
        }
        if (called[0]) {
            throw new AssertionError("Consumer should not be called on empty queue");
        }
    }

    static void testTryAdvanceWithSingleElement() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.add("element");
        final String[] received = {null};
        boolean result = queue.tryAdvance(e -> {
            received[0] = e;
        });
        if (!result) {
            throw new AssertionError("tryAdvance should return true when element is present");
        }
        if (!"element".equals(received[0])) {
            throw new AssertionError("Consumer received wrong element: " + received[0]);
        }
        if (queue.isEmpty()) {
            throw new AssertionError("Queue should not be empty after tryAdvance");
        }
    }

    static void testTryAdvanceWithMultipleElements() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.add("first");
        queue.add("second");
        final String[] received = {null};
        boolean result = queue.tryAdvance(e -> {
            received[0] = e;
        });
        if (!result) {
            throw new AssertionError("tryAdvance should return true when elements are present");
        }
        if (!("first".equals(received[0]) || "second".equals(received[0]))) {
            throw new AssertionError("Consumer received unexpected element: " + received[0]);
        }
        if (queue.isEmpty()) {
            throw new AssertionError("Queue should not be empty after tryAdvance");
        }
    }
}
