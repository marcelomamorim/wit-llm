/*
 * @test
 * @summary Test java.util.ArrayDeque.forEachRemaining(Consumer) method
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.Objects;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithEmptyDeque();
        testForEachRemainingWithElements();
        testForEachRemainingConcurrentModification();
    }

    static void testForEachRemainingWithEmptyDeque() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        final int[] count = {0};
        deque.iterator().forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                count[0]++;
            }
        });
        if (count[0] != 0) {
            throw new AssertionError("forEachRemaining on empty deque should not call accept");
        }
    }

    static void testForEachRemainingWithElements() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");

        final StringBuilder sb = new StringBuilder();
        deque.iterator().forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });

        String result = sb.toString();
        if (!"abc".equals(result)) {
            throw new AssertionError("forEachRemaining should visit all elements in order: expected 'abc' but got '" + result + "'");
        }
    }

    static void testForEachRemainingConcurrentModification() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("x");
        deque.add("y");

        java.util.Iterator<String> it = deque.iterator();
        // Modify deque after iterator creation to cause ConcurrentModificationException
        deque.add("z");

        boolean cmeThrown = false;
        try {
            it.forEachRemaining(new Consumer<String>() {
                public void accept(String s) {
                    // no-op
                }
            });
        } catch (ConcurrentModificationException e) {
            cmeThrown = true;
        }

        if (!cmeThrown) {
            throw new AssertionError("forEachRemaining should throw ConcurrentModificationException if deque modified during iteration");
        }
    }
}
