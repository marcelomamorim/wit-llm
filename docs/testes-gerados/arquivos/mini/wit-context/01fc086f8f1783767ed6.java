/*
 * @test
 * @summary Test java.util.ArrayList.SubList.Anonymous forEachRemaining method for NPE and CME
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingConcurrentModification();
        testForEachRemainingNormal();
        System.out.println("All tests passed.");
    }

    // Test that forEachRemaining throws NullPointerException if action is null
    private static void testForEachRemainingNullAction() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        List<String> subList = list.subList(0, 2);
        ListIterator<String> it = subList.listIterator();
        try {
            it.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
        }
    }

    // Test that forEachRemaining throws ConcurrentModificationException if list structurally modified
    private static void testForEachRemainingConcurrentModification() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        List<String> subList = list.subList(0, 2);
        ListIterator<String> it = subList.listIterator();
        // Modify the root list structurally to cause CME
        list.add("c");
        try {
            it.forEachRemaining(new Consumer<String>() {
                public void accept(String s) {
                    // do nothing
                }
            });
            throw new AssertionError("Expected ConcurrentModificationException due to structural modification");
        } catch (ConcurrentModificationException expected) {
        }
    }

    // Test normal forEachRemaining behavior without exceptions
    private static void testForEachRemainingNormal() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");
        list.add("z");
        List<String> subList = list.subList(0, 3);
        ListIterator<String> it = subList.listIterator();
        final StringBuilder sb = new StringBuilder();
        it.forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });
        if (!"xyz".equals(sb.toString())) {
            throw new AssertionError("forEachRemaining did not process all elements correctly");
        }
    }
}
