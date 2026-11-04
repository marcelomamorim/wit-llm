/*
 * @test
 * @summary Test ConcurrentLinkedDeque tryAdvance method behavior
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithElements();
        testTryAdvanceWithEmptyDeque();
        testTryAdvanceWithNullConsumer();
    }

    static void testTryAdvanceWithElements() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("one");
        deque.add("two");

        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            boolean first = true;
            public void accept(String s) {
                if (first) {
                    sb.append(s);
                    first = false;
                } else {
                    // Should not be called more than once per tryAdvance call
                    throw new AssertionError("accept called more than once");
                }
            }
        };

        boolean advanced = deque.spliterator().tryAdvance(consumer);
        if (!advanced) {
            throw new AssertionError("tryAdvance should return true when elements exist");
        }
        String result = sb.toString();
        if (!"one".equals(result) && !"two".equals(result)) {
            throw new AssertionError("accept should be called with one of the deque elements, got: " + result);
        }
    }

    static void testTryAdvanceWithEmptyDeque() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                throw new AssertionError("accept should not be called on empty deque");
            }
        };

        boolean advanced = deque.spliterator().tryAdvance(consumer);
        if (advanced) {
            throw new AssertionError("tryAdvance should return false when deque is empty");
        }
    }

    static void testTryAdvanceWithNullConsumer() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("element");
        try {
            deque.spliterator().tryAdvance(null);
            throw new AssertionError("tryAdvance should throw NullPointerException when consumer is null");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
