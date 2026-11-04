/*
 * @test
 * @summary Test for tryAdvance method in ConcurrentLinkedDeque
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Consumer;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullAction();
        testTryAdvanceWithEmptyDeque();
        testTryAdvanceWithElement();
    }

    static void testTryAdvanceWithNullAction() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.tryAdvance(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when action is null");
        }
    }

    static void testTryAdvanceWithEmptyDeque() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        boolean result = deque.tryAdvance(e -> {
            throw new AssertionError("Should not be called on empty deque");
        });
        if (result != false) {
            throw new AssertionError("tryAdvance should return false on empty deque");
        }
    }

    static void testTryAdvanceWithElement() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("element");
        final boolean[] called = {false};
        boolean result = deque.tryAdvance(e -> {
            if (!"element".equals(e)) {
                throw new AssertionError("Unexpected element: " + e);
            }
            called[0] = true;
        });
        if (!result) {
            throw new AssertionError("tryAdvance should return true when element is present");
        }
        if (!called[0]) {
            throw new AssertionError("Consumer was not called");
        }
    }
}
