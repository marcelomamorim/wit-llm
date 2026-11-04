/*
 * @test
 * @summary Test java.util.ArrayList.tryAdvance method behavior
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;
import java.util.Objects;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithElements();
        testTryAdvanceEmpty();
        testTryAdvanceNullAction();
        testTryAdvanceConcurrentModification();
    }

    private static void testTryAdvanceWithElements() {
        final ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        // Create a Spliterator from the list
        java.util.Spliterator<String> spliterator = list.spliterator();

        final StringBuilder sb = new StringBuilder();
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        boolean advanced1 = spliterator.tryAdvance(action);
        boolean advanced2 = spliterator.tryAdvance(action);
        boolean advanced3 = spliterator.tryAdvance(action);
        boolean advanced4 = spliterator.tryAdvance(action);

        if (!advanced1 || !advanced2 || !advanced3) {
            throw new AssertionError("Expected to advance for first three elements");
        }
        if (advanced4) {
            throw new AssertionError("Expected no advance after all elements consumed");
        }

        String result = sb.toString();
        if (!result.equals("abc")) {
            throw new AssertionError("Expected concatenated elements 'abc', got: " + result);
        }
    }

    private static void testTryAdvanceEmpty() {
        final ArrayList<String> list = new ArrayList<>();
        java.util.Spliterator<String> spliterator = list.spliterator();

        boolean advanced = spliterator.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                throw new AssertionError("Should not be called on empty list");
            }
        });

        if (advanced) {
            throw new AssertionError("Expected tryAdvance to return false on empty list");
        }
    }

    private static void testTryAdvanceNullAction() {
        final ArrayList<String> list = new ArrayList<>();
        list.add("x");
        java.util.Spliterator<String> spliterator = list.spliterator();

        try {
            spliterator.tryAdvance(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testTryAdvanceConcurrentModification() {
        final ArrayList<String> list = new ArrayList<>();
        list.add("x");
        final java.util.Spliterator<String> spliterator = list.spliterator();

        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                // Modify list structurally during iteration
                list.add("y");
            }
        };

        try {
            spliterator.tryAdvance(action);
            throw new AssertionError("Expected ConcurrentModificationException due to structural modification");
        } catch (ConcurrentModificationException cme) {
            // expected
        }
    }
}
