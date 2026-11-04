/*
 * @test
 * @summary Test ConcurrentLinkedDeque.forEachRemaining(Consumer) behavior
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithElements();
        testForEachRemainingEmptyDeque();
        testForEachRemainingNullAction();
    }

    static void testForEachRemainingWithElements() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");

        Iterator<String> it = deque.iterator();

        final List<String> collected = new ArrayList<>();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                collected.add(s);
            }
        };

        it.forEachRemaining(consumer);

        // The collected list should contain all elements remaining in iteration order
        if (collected.size() != 3 || !collected.contains("a") || !collected.contains("b") || !collected.contains("c")) {
            throw new AssertionError("forEachRemaining did not process all elements: " + collected);
        }

        // After forEachRemaining, iterator should be exhausted
        if (it.hasNext()) {
            throw new AssertionError("Iterator should be exhausted after forEachRemaining");
        }
    }

    static void testForEachRemainingEmptyDeque() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        Iterator<String> it = deque.iterator();

        final List<String> collected = new ArrayList<>();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                collected.add(s);
            }
        };

        it.forEachRemaining(consumer);

        // No elements should be collected
        if (!collected.isEmpty()) {
            throw new AssertionError("forEachRemaining on empty iterator should not call consumer");
        }

        if (it.hasNext()) {
            throw new AssertionError("Iterator should be exhausted after forEachRemaining on empty deque");
        }
    }

    static void testForEachRemainingNullAction() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        Iterator<String> it = deque.iterator();

        try {
            it.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
