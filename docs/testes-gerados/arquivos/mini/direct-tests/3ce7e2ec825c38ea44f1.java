/*
 * @test
 * @summary Test LinkedBlockingDeque.forEachRemaining with various scenarios
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingDeque.Node;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.Objects;

public class LinkedBlockingDequeWitupTest<E> {

    public static void main(String[] args) {
        testForEachRemainingWithElements();
        testForEachRemainingEmptyDeque();
        testForEachRemainingNullAction();
    }

    // Test forEachRemaining on a deque with some elements, verify all elements are consumed
    private static void testForEachRemainingWithElements() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("one");
        deque.add("two");
        deque.add("three");

        final List<String> collected = new ArrayList<>();

        Iterator<String> it = deque.iterator();

        // Use forEachRemaining on the iterator
        it.forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                collected.add(s);
            }
        });

        if (collected.size() != 3 || !collected.contains("one") || !collected.contains("two") || !collected.contains("three")) {
            throw new AssertionError("forEachRemaining did not consume all elements correctly: " + collected);
        }
    }

    // Test forEachRemaining on an empty deque iterator: should do nothing and not throw
    private static void testForEachRemainingEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        Iterator<String> it = deque.iterator();

        final List<String> collected = new ArrayList<>();

        it.forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                collected.add(s);
            }
        });

        if (!collected.isEmpty()) {
            throw new AssertionError("forEachRemaining on empty iterator should not consume elements");
        }
    }

    // Test forEachRemaining with null action: should throw NullPointerException
    private static void testForEachRemainingNullAction() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        Iterator<String> it = deque.iterator();
        boolean npeThrown = false;
        try {
            it.forEachRemaining(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("forEachRemaining should throw NullPointerException when action is null");
        }
    }
}
