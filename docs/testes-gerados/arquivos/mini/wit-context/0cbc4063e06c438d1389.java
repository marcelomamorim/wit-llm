/*
 * @test
 * @summary Test java.util.ArrayList.tryAdvance(Consumer) for expected exceptions and normal behavior
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testTryAdvanceNullAction();
        testTryAdvanceConcurrentModification();
        testTryAdvanceNormal();
    }

    private static void testTryAdvanceNullAction() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        try {
            list.tryAdvance(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testTryAdvanceConcurrentModification() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");

        // Create a Consumer that does nothing
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                // no-op
            }
        };

        // We need to cause modCount != expectedModCount during tryAdvance
        // The only way is to structurally modify the list during iteration
        // Since tryAdvance uses an internal spliterator, we simulate by subclassing

        // But ArrayList.tryAdvance is final and no public way to set modCount or expectedModCount
        // So we simulate by calling tryAdvance and modifying list concurrently

        // We call tryAdvance once to get index incremented
        boolean advanced = list.tryAdvance(consumer);
        if (!advanced) {
            throw new RuntimeException("tryAdvance should have advanced on non-empty list");
        }

        // Now modify list structurally to change modCount
        list.add("b");

        // Next tryAdvance call should detect concurrent modification
        try {
            list.tryAdvance(consumer);
            throw new RuntimeException("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }

    private static void testTryAdvanceNormal() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");

        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        boolean advanced1 = list.tryAdvance(consumer);
        if (!advanced1) {
            throw new RuntimeException("tryAdvance should return true when elements remain");
        }
        if (!sb.toString().equals("x")) {
            throw new RuntimeException("Consumer did not receive expected element 'x'");
        }

        boolean advanced2 = list.tryAdvance(consumer);
        if (!advanced2) {
            throw new RuntimeException("tryAdvance should return true when elements remain");
        }
        if (!sb.toString().equals("xy")) {
            throw new RuntimeException("Consumer did not receive expected element 'y'");
        }

        boolean advanced3 = list.tryAdvance(consumer);
        if (advanced3) {
            throw new RuntimeException("tryAdvance should return false when no elements remain");
        }
    }
}
