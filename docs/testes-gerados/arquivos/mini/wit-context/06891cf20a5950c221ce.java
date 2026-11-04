/*
 * @test
 * @summary Test LinkedList.tryAdvance method for expected behavior and exceptions
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.function.Consumer;
import java.util.ConcurrentModificationException;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        testTryAdvanceNullAction();
        testTryAdvanceEmptyList();
        testTryAdvanceSingleElement();
        testTryAdvanceConcurrentModification();
        System.out.println("All tests passed.");
    }

    static void testTryAdvanceNullAction() {
        LinkedList<String> list = new LinkedList<>();
        LinkedList<String>.LLSpliterator spliterator = list.new LLSpliterator();
        try {
            spliterator.tryAdvance(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testTryAdvanceEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        LinkedList<String>.LLSpliterator spliterator = list.new LLSpliterator();
        boolean advanced = spliterator.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                throw new AssertionError("Should not be called on empty list");
            }
        });
        if (advanced) {
            throw new AssertionError("tryAdvance should return false on empty list");
        }
    }

    static void testTryAdvanceSingleElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add("element");
        LinkedList<String>.LLSpliterator spliterator = list.new LLSpliterator();
        final StringBuilder sb = new StringBuilder();
        boolean advanced = spliterator.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });
        if (!advanced) {
            throw new AssertionError("tryAdvance should return true when element is present");
        }
        if (!"element".equals(sb.toString())) {
            throw new AssertionError("Consumer did not receive correct element");
        }
        // Next call should return false as no more elements
        boolean advanced2 = spliterator.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                throw new AssertionError("No more elements expected");
            }
        });
        if (advanced2) {
            throw new AssertionError("tryAdvance should return false after last element");
        }
    }

    static void testTryAdvanceConcurrentModification() {
        LinkedList<String> list = new LinkedList<>();
        list.add("one");
        LinkedList<String>.LLSpliterator spliterator = list.new LLSpliterator();
        final boolean[] accepted = {false};
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                accepted[0] = true;
                // Modify list structurally during accept to trigger ConcurrentModificationException
                list.add("two");
            }
        };
        try {
            spliterator.tryAdvance(consumer);
            throw new AssertionError("Expected ConcurrentModificationException due to structural modification");
        } catch (ConcurrentModificationException expected) {
            if (!accepted[0]) {
                throw new AssertionError("Consumer was not called before exception");
            }
        }
    }
}
