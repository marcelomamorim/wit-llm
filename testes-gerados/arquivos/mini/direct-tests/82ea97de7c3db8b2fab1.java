/*
 * @test
 * @summary Test ArrayList.forEachRemaining method behavior
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testForEachRemainingNormal();
        testForEachRemainingEmpty();
        testForEachRemainingConcurrentModification();
    }

    static void testForEachRemainingNormal() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        Iterator<String> it = list.iterator();
        // advance iterator once
        it.next();

        final StringBuilder sb = new StringBuilder();
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        it.forEachRemaining(action);

        // after consuming remaining elements, sb should contain "bc"
        if (!"bc".equals(sb.toString())) {
            throw new AssertionError("Expected 'bc' but got '" + sb.toString() + "'");
        }
    }

    static void testForEachRemainingEmpty() {
        ArrayList<String> list = new ArrayList<>();
        Iterator<String> it = list.iterator();

        final StringBuilder sb = new StringBuilder();
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        it.forEachRemaining(action);

        // no elements, sb should be empty
        if (sb.length() != 0) {
            throw new AssertionError("Expected empty but got '" + sb.toString() + "'");
        }
    }

    static void testForEachRemainingConcurrentModification() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");

        Iterator<String> it = list.iterator();

        // forcibly cause concurrent modification by modifying list's internal array length
        // This is not possible via public API, so we simulate by reflection
        // But reflection is disallowed by instructions, so instead we simulate by structural modification

        // Advance iterator once
        it.next();

        // Modify list structurally
        list.add("z");

        final Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                // do nothing
            }
        };

        boolean threw = false;
        try {
            it.forEachRemaining(action);
        } catch (ConcurrentModificationException e) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("Expected ConcurrentModificationException due to structural modification");
        }
    }
}
