/*
 * @test
 * @summary Test java.util.ArrayList.forEachRemaining(Consumer) for NPE and normal behavior
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingNormal();
        testForEachRemainingConcurrentModification();
    }

    private static void testForEachRemainingNullAction() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        java.util.ArrayList.ArrayListSpliterator<String> spliterator =
            (java.util.ArrayList.ArrayListSpliterator<String>) list.spliterator();
        try {
            spliterator.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testForEachRemainingNormal() {
        ArrayList<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        list.add("three");

        final StringBuilder sb = new StringBuilder();
        java.util.ArrayList.ArrayListSpliterator<String> spliterator =
            (java.util.ArrayList.ArrayListSpliterator<String>) list.spliterator();

        spliterator.forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s).append(",");
            }
        });

        String result = sb.toString();
        if (!result.equals("one,two,three,")) {
            throw new AssertionError("Unexpected forEachRemaining output: " + result);
        }
    }

    private static void testForEachRemainingConcurrentModification() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");

        java.util.ArrayList.ArrayListSpliterator<String> spliterator =
            (java.util.ArrayList.ArrayListSpliterator<String>) list.spliterator();

        // Modify list structurally to cause ConcurrentModificationException
        list.add("z");

        try {
            spliterator.forEachRemaining(new Consumer<String>() {
                public void accept(String s) {
                    // do nothing
                }
            });
            throw new AssertionError("Expected ConcurrentModificationException due to structural modification");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
