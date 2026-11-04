/*
 * @test
 * @summary Test java.util.ArrayList removeAll(Collection<?>) method
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testRemoveAllWithEmptyCollection();
        testRemoveAllWithNonEmptyCollection();
        testRemoveAllWithNoMatchingElements();
        testRemoveAllWithAllMatchingElements();
        testRemoveAllWithNullCollection();
    }

    private static void testRemoveAllWithEmptyCollection() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        boolean changed = list.removeAll(new ArrayList<>());
        if (changed) {
            throw new RuntimeException("removeAll with empty collection should not change the list");
        }
        if (list.size() != 3) {
            throw new RuntimeException("List size should remain 3 after removeAll with empty collection");
        }
    }

    private static void testRemoveAllWithNonEmptyCollection() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");

        Collection<String> toRemove = Arrays.asList("b", "d");
        boolean changed = list.removeAll(toRemove);
        if (!changed) {
            throw new RuntimeException("removeAll should return true when elements are removed");
        }
        if (list.size() != 2) {
            throw new RuntimeException("List size should be 2 after removeAll");
        }
        if (list.contains("b") || list.contains("d")) {
            throw new RuntimeException("List should not contain removed elements");
        }
    }

    private static void testRemoveAllWithNoMatchingElements() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");

        Collection<String> toRemove = Arrays.asList("x", "y");
        boolean changed = list.removeAll(toRemove);
        if (changed) {
            throw new RuntimeException("removeAll should return false when no elements are removed");
        }
        if (list.size() != 2) {
            throw new RuntimeException("List size should remain 2 when no elements removed");
        }
    }

    private static void testRemoveAllWithAllMatchingElements() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");

        Collection<String> toRemove = Arrays.asList("a", "b");
        boolean changed = list.removeAll(toRemove);
        if (!changed) {
            throw new RuntimeException("removeAll should return true when all elements are removed");
        }
        if (!list.isEmpty()) {
            throw new RuntimeException("List should be empty after removeAll removes all elements");
        }
    }

    private static void testRemoveAllWithNullCollection() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        try {
            list.removeAll(null);
            throw new RuntimeException("removeAll with null collection should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
