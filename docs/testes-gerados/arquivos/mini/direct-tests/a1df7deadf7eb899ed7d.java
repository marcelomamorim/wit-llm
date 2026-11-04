/*
 * @test
 * @summary Test ConcurrentLinkedQueue.forEachRemaining(Consumer) behavior
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.Objects;

public class ConcurrentLinkedQueueWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithElements();
        testForEachRemainingWithEmptyQueue();
        testForEachRemainingWithNullAction();
    }

    static void testForEachRemainingWithElements() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");

        Iterator<String> it = queue.iterator();

        final StringBuilder sb = new StringBuilder();
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        it.forEachRemaining(action);

        // The StringBuilder should contain all elements from the iterator in order
        String result = sb.toString();
        if (!"abc".equals(result)) {
            throw new AssertionError("Expected 'abc' but got '" + result + "'");
        }

        // After forEachRemaining, iterator should be exhausted
        if (it.hasNext()) {
            throw new AssertionError("Iterator should be exhausted after forEachRemaining");
        }

        try {
            it.next();
            throw new AssertionError("Expected NoSuchElementException after iterator exhausted");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testForEachRemainingWithEmptyQueue() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        Iterator<String> it = queue.iterator();

        final boolean[] called = {false};
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                called[0] = true;
            }
        };

        it.forEachRemaining(action);

        // Since queue is empty, action should not be called
        if (called[0]) {
            throw new AssertionError("Action should not be called for empty iterator");
        }

        if (it.hasNext()) {
            throw new AssertionError("Iterator should be exhausted for empty queue");
        }
    }

    static void testForEachRemainingWithNullAction() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.add("x");
        Iterator<String> it = queue.iterator();

        try {
            it.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
