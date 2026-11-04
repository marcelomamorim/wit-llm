/*
 * @test
 * @summary Test java.util.ArrayDeque.forEachRemaining(Consumer) method
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;

public class ArrayDequeWitupTest {
    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testForEachRemainingProcessesAllElements();
        testForEachRemainingConcurrentModificationException();
    }

    static void testNullActionThrowsNPE() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        boolean npeThrown = false;
        try {
            deque.iterator().forEachRemaining(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when action is null");
        }
    }

    static void testForEachRemainingProcessesAllElements() {
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

        java.util.Iterator<String> it = deque.iterator();
        it.next(); // advance cursor by one
        it.forEachRemaining(consumer);

        // The consumer should have processed "b" and "c"
        if (!sb.toString().equals("bc")) {
            throw new AssertionError("forEachRemaining did not process expected elements: " + sb.toString());
        }
    }

    static void testForEachRemainingConcurrentModificationException() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("x");
        deque.add("y");

        java.util.Iterator<String> it = deque.iterator();
        it.next();

        // Modify deque to cause concurrent modification
        deque.clear();

        boolean cmeThrown = false;
        try {
            it.forEachRemaining(new Consumer<String>() {
                public void accept(String s) {
                    // do nothing
                }
            });
        } catch (ConcurrentModificationException e) {
            cmeThrown = true;
        }

        if (!cmeThrown) {
            throw new AssertionError("Expected ConcurrentModificationException after modification");
        }
    }
}
