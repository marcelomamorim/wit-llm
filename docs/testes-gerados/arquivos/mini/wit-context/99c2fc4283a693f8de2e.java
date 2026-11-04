/*
 * @test
 * @summary Test LinkedTransferQueue.tryAdvance behavior including null action rejection
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.function.Consumer;
import java.util.Objects;

public class LinkedTransferQueueWitupTest {
    public static void main(String[] args) {
        testTryAdvanceWithNullAction();
        testTryAdvanceOnEmptyQueue();
        testTryAdvanceOnNonEmptyQueue();
    }

    static void testTryAdvanceWithNullAction() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        boolean npeThrown = false;
        try {
            queue.tryAdvance(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when action is null");
        }
    }

    static void testTryAdvanceOnEmptyQueue() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        boolean result = queue.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                throw new AssertionError("Should not be called on empty queue");
            }
        });
        if (result) {
            throw new AssertionError("tryAdvance should return false on empty queue");
        }
    }

    static void testTryAdvanceOnNonEmptyQueue() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
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
