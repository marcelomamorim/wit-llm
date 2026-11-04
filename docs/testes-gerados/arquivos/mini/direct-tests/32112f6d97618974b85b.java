/*
 * @test
 * @summary Test java.util.ArrayDeque.forEachRemaining(Consumer) for normal and concurrent modification cases
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;
import java.util.Objects;

public class ArrayDequeWitupTest {
    public static void main(String[] args) {
        testForEachRemainingNormal();
        testForEachRemainingEmpty();
        testForEachRemainingConcurrentModification();
    }

    static void testForEachRemainingNormal() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");

        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        // Use iterator and call forEachRemaining on it
        java.util.Iterator<String> it = deque.iterator();
        it.next(); // advance one element
        it.forEachRemaining(consumer);

        // After skipping "a", forEachRemaining should consume "b" and "c"
        if (!sb.toString().equals("bc")) {
            throw new AssertionError("Expected 'bc' but got '" + sb.toString() + "'");
        }
    }

    static void testForEachRemainingEmpty() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        java.util.Iterator<String> it = deque.iterator();

        final boolean[] called = {false};
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                called[0] = true;
            }
        };

        // forEachRemaining on empty iterator should not call consumer
        it.forEachRemaining(consumer);

        if (called[0]) {
            throw new AssertionError("Consumer should not be called on empty iterator");
        }
    }

    static void testForEachRemainingConcurrentModification() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("x");
        deque.add("y");

        java.util.Iterator<String> it = deque.iterator();

        // Modify deque after iterator creation to cause ConcurrentModificationException
        deque.add("z");

        final Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                // no-op
            }
        };

        boolean cmeThrown = false;
        try {
            it.forEachRemaining(consumer);
        } catch (ConcurrentModificationException e) {
            cmeThrown = true;
        }

        if (!cmeThrown) {
            throw new AssertionError("Expected ConcurrentModificationException not thrown");
        }
    }
}
