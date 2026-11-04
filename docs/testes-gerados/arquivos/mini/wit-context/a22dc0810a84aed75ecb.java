/*
 * @test
 * @summary Test ArrayDeque.forEachRemaining with null Consumer and normal usage
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;

public class ArrayDequeWitupTest {
    public static void main(String[] args) {
        testForEachRemainingNullConsumer();
        testForEachRemainingNormal();
        testForEachRemainingConcurrentModification();
    }

    static void testForEachRemainingNullConsumer() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("one");
        try {
            deque.forEachRemaining(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachRemainingNormal() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("a");
        deque.add("b");
        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };
        deque.forEachRemaining(consumer);
        String result = sb.toString();
        if (!result.contains("a") || !result.contains("b")) {
            throw new Error("forEachRemaining did not process all elements: " + result);
        }
    }

    static void testForEachRemainingConcurrentModification() {
        // This test tries to provoke ConcurrentModificationException by modifying the deque during iteration
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("x");
        deque.add("y");

        final Consumer<String> consumer = new Consumer<String>() {
            boolean first = true;
            public void accept(String s) {
                if (first) {
                    first = false;
                    // Modify deque during iteration to provoke CME
                    deque.clear();
                }
            }
        };

        try {
            deque.forEachRemaining(consumer);
            throw new Error("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
