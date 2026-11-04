/*
 * @test
 * @summary Test java.util.ArrayDeque.tryAdvance(Consumer) method behavior
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.Objects;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithElements();
        testTryAdvanceEmptyDeque();
        testTryAdvanceNullConsumer();
    }

    static void testTryAdvanceWithElements() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("one");
        deque.add("two");

        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        boolean advanced1 = deque.tryAdvance(consumer);
        boolean advanced2 = deque.tryAdvance(consumer);
        boolean advanced3 = deque.tryAdvance(consumer);

        if (!advanced1) throw new AssertionError("Expected first tryAdvance to return true");
        if (!advanced2) throw new AssertionError("Expected second tryAdvance to return true");
        if (advanced3) throw new AssertionError("Expected third tryAdvance to return false");

        String result = sb.toString();
        if (!result.equals("onetwo") && !result.equals("twoone")) {
            throw new AssertionError("Consumer did not receive expected elements, got: " + result);
        }
    }

    static void testTryAdvanceEmptyDeque() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                throw new AssertionError("Consumer should not be called on empty deque");
            }
        };

        boolean advanced = deque.tryAdvance(consumer);
        if (advanced) {
            throw new AssertionError("Expected tryAdvance to return false on empty deque");
        }
    }

    static void testTryAdvanceNullConsumer() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("element");
        try {
            deque.tryAdvance(null);
            throw new AssertionError("Expected NullPointerException when passing null consumer");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
