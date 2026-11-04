/*
 * @test
 * @summary Test java.util.ArrayList.forEachRemaining behavior
 * @run main java.util.ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.function.Consumer;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testForEachRemainingNormal();
        testForEachRemainingEmpty();
        testForEachRemainingNullAction();
        testForEachRemainingConcurrentModification();
    }

    static void testForEachRemainingNormal() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        Iterator<String> it = list.iterator();
        final StringBuilder sb = new StringBuilder();
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        it.forEachRemaining(action);

        if (!"abc".equals(sb.toString())) {
            throw new AssertionError("Expected concatenated string 'abc' but got '" + sb.toString() + "'");
        }
    }

    static void testForEachRemainingEmpty() {
        ArrayList<String> list = new ArrayList<>();
        Iterator<String> it = list.iterator();
        final boolean[] called = {false};
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                called[0] = true;
            }
        };
        it.forEachRemaining(action);
        if (called[0]) {
            throw new AssertionError("Action should not be called on empty iterator");
        }
    }

    static void testForEachRemainingNullAction() {
        ArrayList<String> list = new ArrayList<>();
        Iterator<String> it = list.iterator();
        try {
            it.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachRemainingConcurrentModification() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        Iterator<String> it = list.iterator();
        // Modify list structurally after iterator creation
        list.add("y");
        try {
            it.forEachRemaining(new Consumer<String>() {
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
