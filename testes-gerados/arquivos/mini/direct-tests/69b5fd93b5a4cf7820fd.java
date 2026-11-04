/*
 * @test
 * @summary Test LinkedList.addAll(Collection) method
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.Collection;
import java.util.ArrayList;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        testAddAllWithNonEmptyCollection();
        testAddAllWithEmptyCollection();
        testAddAllWithNullCollection();
    }

    private static void testAddAllWithNonEmptyCollection() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");

        Collection<String> toAdd = new ArrayList<>();
        toAdd.add("c");
        toAdd.add("d");

        boolean changed = list.addAll(toAdd);

        if (!changed) {
            throw new RuntimeException("addAll should return true when elements are added");
        }

        if (list.size() != 4) {
            throw new RuntimeException("List size should be 4 after addAll");
        }

        if (!list.get(2).equals("c") || !list.get(3).equals("d")) {
            throw new RuntimeException("Elements not added correctly at the end");
        }
    }

    private static void testAddAllWithEmptyCollection() {
        LinkedList<String> list = new LinkedList<>();
        list.add("x");

        Collection<String> empty = new ArrayList<>();

        boolean changed = list.addAll(empty);

        if (changed) {
            throw new RuntimeException("addAll should return false when adding empty collection");
        }

        if (list.size() != 1) {
            throw new RuntimeException("List size should remain 1 after adding empty collection");
        }

        if (!list.get(0).equals("x")) {
            throw new RuntimeException("Original element should remain unchanged");
        }
    }

    private static void testAddAllWithNullCollection() {
        LinkedList<String> list = new LinkedList<>();
        try {
            list.addAll(null);
            throw new RuntimeException("addAll(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
