/*
 * @test
 * @summary Test ArrayDeque.DescendingIterator.forEachRemaining behavior including null action and concurrent modification
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;
import java.util.Objects;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingConcurrentModification();
        testForEachRemainingNormal();
    }

    static void testForEachRemainingNullAction() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("a");
        ArrayDeque.DescendingIterator<String> it = (ArrayDeque.DescendingIterator<String>) deque.descendingIterator();
        try {
            it.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachRemainingConcurrentModification() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("a");
        ArrayDeque.DescendingIterator<String> it = (ArrayDeque.DescendingIterator<String>) deque.descendingIterator();
        // corrupt internal state to simulate concurrent modification
        // We do this by removing element from deque after iterator creation
        deque.remove();
        try {
            it.forEachRemaining(new Consumer<String>() {
                public void accept(String s) {
                    // do nothing
                }
            });
            throw new AssertionError("Expected ConcurrentModificationException due to concurrent modification");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }

    static void testForEachRemainingNormal() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("a");
        deque.add("b");
        final StringBuilder sb = new StringBuilder();
        ArrayDeque.DescendingIterator<String> it = (ArrayDeque.DescendingIterator<String>) deque.descendingIterator();
        it.forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });
        String result = sb.toString();
        // descending iterator returns elements in reverse order
        if (!"ba".equals(result)) {
            throw new AssertionError("Expected 'ba' but got '" + result + "'");
        }
    }

}