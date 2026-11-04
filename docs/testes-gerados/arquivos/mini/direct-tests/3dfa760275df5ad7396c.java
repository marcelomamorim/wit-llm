/*
 * @test
 * @summary Test LinkedBlockingDeque.tryAdvance method behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.NoSuchElementException;
import java.util.Objects;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithElements();
        testTryAdvanceEmptyDeque();
        testTryAdvanceNullConsumer();
    }

    static void testTryAdvanceWithElements() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("one");
        deque.add("two");

        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            int count = 0;
            public void accept(String s) {
                sb.append(s);
                count++;
            }
        };

        // We cannot access 'current' or 'exhausted' directly, so we test tryAdvance repeatedly
        boolean advanced1 = deque.tryAdvance(consumer);
        boolean advanced2 = deque.tryAdvance(consumer);
        boolean advanced3 = deque.tryAdvance(consumer);

        if (!advanced1 || !advanced2) {
            throw new AssertionError("tryAdvance should return true for first two elements");
        }
        if (advanced3) {
            throw new AssertionError("tryAdvance should return false after all elements are consumed");
        }
        String result = sb.toString();
        if (!(result.contains("one") && result.contains("two"))) {
            throw new AssertionError("Consumer did not receive expected elements: " + result);
        }
    }

    static void testTryAdvanceEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                throw new AssertionError("Consumer should not be called on empty deque");
            }
        };
        boolean advanced = deque.tryAdvance(consumer);
        if (advanced) {
            throw new AssertionError("tryAdvance should return false on empty deque");
        }
    }

    static void testTryAdvanceNullConsumer() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.tryAdvance(null);
            throw new AssertionError("tryAdvance should throw NullPointerException when action is null");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
