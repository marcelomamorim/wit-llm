/*
 * @test
 * @summary Test java.util.ArrayList tryAdvance method behavior and exceptions
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testTryAdvanceNullAction();
        testTryAdvanceNormalIteration();
        testTryAdvanceConcurrentModification();
        testTryAdvanceEmptyList();
    }

    static void testTryAdvanceNullAction() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        java.util.Spliterator<String> spliterator = list.spliterator();
        try {
            spliterator.tryAdvance(null);
            throw new RuntimeException("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testTryAdvanceNormalIteration() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        java.util.Spliterator<String> spliterator = list.spliterator();

        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        boolean advanced1 = spliterator.tryAdvance(consumer);
        if (!advanced1 || !sb.toString().equals("a")) {
            throw new RuntimeException("tryAdvance failed to consume first element");
        }

        boolean advanced2 = spliterator.tryAdvance(consumer);
        if (!advanced2 || !sb.toString().equals("ab")) {
            throw new RuntimeException("tryAdvance failed to consume second element");
        }

        boolean advanced3 = spliterator.tryAdvance(consumer);
        if (advanced3) {
            throw new RuntimeException("tryAdvance should return false when no elements remain");
        }
    }

    static void testTryAdvanceConcurrentModification() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        java.util.Spliterator<String> spliterator = list.spliterator();

        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        // Advance once normally
        boolean advanced1 = spliterator.tryAdvance(consumer);
        if (!advanced1 || !sb.toString().equals("a")) {
            throw new RuntimeException("tryAdvance failed to consume first element");
        }

        // Modify list structurally to cause ConcurrentModificationException
        list.add("c");

        try {
            spliterator.tryAdvance(consumer);
            throw new RuntimeException("Expected ConcurrentModificationException after structural modification");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }

    static void testTryAdvanceEmptyList() {
        ArrayList<String> list = new ArrayList<>();
        java.util.Spliterator<String> spliterator = list.spliterator();

        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                throw new RuntimeException("Should not be called on empty list");
            }
        };

        boolean advanced = spliterator.tryAdvance(consumer);
        if (advanced) {
            throw new RuntimeException("tryAdvance should return false on empty list");
        }
    }
}
