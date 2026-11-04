/*
 * @test
 * @summary Test LinkedList.forEachRemaining behavior including normal iteration, null action, and concurrent modification
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;

public class LinkedListWitupTest {

    static class TestConsumer<E> implements Consumer<E> {
        int count = 0;
        E last = null;
        public void accept(E e) {
            count++;
            last = e;
        }
    }

    public static void main(String[] args) {
        testForEachRemainingNormal();
        testForEachRemainingNullAction();
        testForEachRemainingConcurrentModification();
    }

    // Test normal iteration over all elements using forEachRemaining
    private static void testForEachRemainingNormal() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        // We cannot access the internal iterator or current directly,
        // so we test by creating an iterator and calling forEachRemaining on it.
        java.util.Iterator<String> it = list.iterator();

        TestConsumer<String> consumer = new TestConsumer<>();
        it.forEachRemaining(consumer);

        if (consumer.count != 3) {
            throw new AssertionError("Expected 3 elements consumed, got " + consumer.count);
        }
        if (!"c".equals(consumer.last)) {
            throw new AssertionError("Expected last element 'c', got " + consumer.last);
        }

        // After forEachRemaining, iterator should be exhausted
        if (it.hasNext()) {
            throw new AssertionError("Iterator should be exhausted after forEachRemaining");
        }
    }

    // Test that passing null to forEachRemaining throws NullPointerException
    private static void testForEachRemainingNullAction() {
        LinkedList<String> list = new LinkedList<>();
        list.add("x");
        java.util.Iterator<String> it = list.iterator();
        try {
            it.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    // Test that concurrent modification during forEachRemaining throws ConcurrentModificationException
    private static void testForEachRemainingConcurrentModification() {
        LinkedList<String> list = new LinkedList<>();
        list.add("one");
        list.add("two");

        java.util.Iterator<String> it = list.iterator();

        // We will modify the list after first element is consumed
        Consumer<String> consumer = new Consumer<String>() {
            boolean modified = false;
            public void accept(String s) {
                if (!modified) {
                    list.add("three"); // structural modification
                    modified = true;
                }
            }
        };

        try {
            it.forEachRemaining(consumer);
            throw new AssertionError("Expected ConcurrentModificationException due to structural modification");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
