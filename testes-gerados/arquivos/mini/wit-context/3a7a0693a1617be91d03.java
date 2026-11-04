/*
 * @test
 * @summary Test ConcurrentLinkedDeque.tryAdvance behavior including null action
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Consumer;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ConcurrentLinkedDequeWitupTest {
    public static void main(String[] args) {
        testTryAdvanceWithNullAction();
        testTryAdvanceOnEmptyDeque();
        testTryAdvanceOnNonEmptyDeque();
    }

    private static void testTryAdvanceWithNullAction() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        boolean npeThrown = false;
        try {
            deque.tryAdvance(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when action is null");
        }
    }

    private static void testTryAdvanceOnEmptyDeque() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                throw new AssertionError("Should not be called on empty deque");
            }
        };
        boolean result = deque.tryAdvance(consumer);
        if (result) {
            throw new AssertionError("tryAdvance should return false on empty deque");
        }
    }

    private static void testTryAdvanceOnNonEmptyDeque() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("test");
        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };
        boolean result = deque.tryAdvance(consumer);
        if (!result) {
            throw new AssertionError("tryAdvance should return true when element is present");
        }
        if (!"test".equals(sb.toString())) {
            throw new AssertionError("Consumer did not receive the expected element");
        }
    }
}
