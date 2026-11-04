/*
 * @test
 * @summary Test java.util.ArrayDeque.forEach method for null action and concurrent modification
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;
import java.util.Objects;

public class ArrayDequeWitupTest {
    public static void main(String[] args) {
        testForEachNullAction();
        testForEachConcurrentModification();
        testForEachNormalIteration();
    }

    private static void testForEachNullAction() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("one");
        try {
            deque.forEach(null);
            throw new RuntimeException("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testForEachConcurrentModification() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("one");
        deque.add("two");

        final Consumer<String> action = new Consumer<String>() {
            private int count = 0;
            @Override
            public void accept(String s) {
                count++;
                if (count == 1) {
                    // Modify deque during iteration to trigger ConcurrentModificationException
                    deque.add("three");
                }
            }
        };

        try {
            deque.forEach(action);
            throw new RuntimeException("Expected ConcurrentModificationException due to modification during forEach");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }

    private static void testForEachNormalIteration() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");

        final StringBuilder sb = new StringBuilder();
        Consumer<String> action = new Consumer<String>() {
            @Override
            public void accept(String s) {
                sb.append(s);
            }
        };

        deque.forEach(action);

        String result = sb.toString();
        if (!"abc".equals(result) && !"cba".equals(result)) {
            // The order depends on internal structure but should contain all elements concatenated
            throw new RuntimeException("Unexpected iteration result: " + result);
        }
    }
}
