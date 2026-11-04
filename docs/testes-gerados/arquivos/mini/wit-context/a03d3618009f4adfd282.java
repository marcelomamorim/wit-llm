/*
 * @test
 * @summary Test deterministic behavior of ArrayDeque.tryAdvance(Consumer) including null action
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.Objects;

public class ArrayDequeWitupTest {
    public static void main(String[] args) {
        testTryAdvanceWithNullAction();
        testTryAdvanceOnEmptyDeque();
        testTryAdvanceConsumesElement();
    }

    private static void testTryAdvanceWithNullAction() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("one");
        try {
            deque.tryAdvance(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testTryAdvanceOnEmptyDeque() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        boolean result = deque.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                throw new AssertionError("Should not be called on empty deque");
            }
        });
        if (result) {
            throw new AssertionError("tryAdvance should return false on empty deque");
        }
    }

    private static void testTryAdvanceConsumesElement() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("first");
        final StringBuilder sb = new StringBuilder();
        boolean result = deque.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });
        if (!result) {
            throw new AssertionError("tryAdvance should return true when element is consumed");
        }
        if (!"first".equals(sb.toString())) {
            throw new AssertionError("Consumer did not receive expected element");
        }
        // Subsequent tryAdvance should return false as deque is not modified but cursor advanced
        boolean secondResult = deque.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                throw new AssertionError("No more elements expected");
            }
        });
        if (secondResult) {
            throw new AssertionError("tryAdvance should return false after consuming all elements");
        }
    }
}
