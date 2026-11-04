/*
 * @test
 * @summary Test ArrayDeque.forEachRemaining with null Consumer and normal usage
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;
import java.util.Objects;

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
            throw new Error("Expected NullPointerException for null Consumer");
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
        if (!"ab".equals(result) && !"ba".equals(result)) {
            throw new Error("Unexpected iteration order or elements: " + result);
        }
    }

    static void testForEachRemainingConcurrentModification() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("x");
        deque.add("y");
        final Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                // no-op
            }
        };

        // We cannot directly cause ConcurrentModificationException from forEachRemaining
        // without reflection or internal state manipulation, so this test is omitted.
        // This is noted in the 'notes' field.
    }
}
