/*
 * @test
 * @summary Test java.util.ArrayDeque.forEach method behavior
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.ConcurrentModificationException;
import java.util.Objects;
import java.util.function.Consumer;

public class ArrayDequeWitupTest {
    public static void main(String[] args) {
        testForEachWithElements();
        testForEachWithEmptyDeque();
        testForEachNullConsumer();
        testForEachConcurrentModification();
    }

    private static void testForEachWithElements() {
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

        deque.forEach(consumer);

        String result = sb.toString();
        if (!"abc".equals(result)) {
            throw new AssertionError("Expected concatenated string 'abc' but got '" + result + "'");
        }
    }

    private static void testForEachWithEmptyDeque() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        final int[] count = {0};
        Consumer<Integer> consumer = new Consumer<Integer>() {
            public void accept(Integer i) {
                count[0]++;
            }
        };
        deque.forEach(consumer);
        if (count[0] != 0) {
            throw new AssertionError("Expected consumer not to be called on empty deque");
        }
    }

    private static void testForEachNullConsumer() {
        ArrayDeque<Object> deque = new ArrayDeque<>();
        try {
            deque.forEach(null);
            throw new AssertionError("Expected NullPointerException when passing null consumer");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testForEachConcurrentModification() {
        final ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("x");
        deque.add("y");

        Consumer<String> consumer = new Consumer<String>() {
            private int count = 0;
            public void accept(String s) {
                count++;
                if (count == 1) {
                    // Concurrent modification during iteration
                    deque.add("z");
                }
            }
        };

        try {
            deque.forEach(consumer);
            throw new AssertionError("Expected ConcurrentModificationException due to modification during forEach");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
