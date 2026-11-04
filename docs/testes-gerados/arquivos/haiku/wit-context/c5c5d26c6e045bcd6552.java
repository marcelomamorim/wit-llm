/*
 * @test
 * @summary Behavioral checks for AbstractList.addAll(Collection) using a minimal list-backed subclass
 * @run main AbstractListWitupTest
 */

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AbstractListWitupTest {

    static class BackedList extends AbstractList<String> {
        private final ArrayList<String> data = new ArrayList<>();

        @Override
        public String get(int index) {
            return data.get(index);
        }

        @Override
        public int size() {
            return data.size();
        }

        @Override
        public void add(int index, String element) {
            data.add(index, element);
        }

        @Override
        public String set(int index, String element) {
            return data.set(index, element);
        }

        @Override
        public String remove(int index) {
            return data.remove(index);
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        testAddAllEmptyCollectionReturnsFalse();
        testAddAllAppendsElementsInOrder();
        testAddAllPreservesExistingElements();
        testAddAllNullCollectionThrowsNpe();
    }

    static void testAddAllEmptyCollectionReturnsFalse() {
        BackedList list = new BackedList();
        boolean changed = list.addAll(Collections.<String>emptyList());
        check(!changed, "addAll(empty) should return false");
        check(list.size() == 0, "size should remain 0 after addAll(empty)");
    }

    static void testAddAllAppendsElementsInOrder() {
        BackedList list = new BackedList();
        boolean changed = list.addAll(Arrays.asList("a", "b", "c"));
        check(changed, "addAll(non-empty) should return true");
        check(list.equals(Arrays.asList("a", "b", "c")),
              "elements should be appended in iteration order");
    }

    static void testAddAllPreservesExistingElements() {
        BackedList list = new BackedList();
        list.add("x");
        boolean changed = list.addAll(Arrays.asList("y", "z"));
        check(changed, "addAll should return true when elements are added");
        check(list.equals(Arrays.asList("x", "y", "z")),
              "existing elements should be kept before appended ones");
    }

    static void testAddAllNullCollectionThrowsNpe() {
        BackedList list = new BackedList();
        list.add("x");
        boolean npe = false;
        try {
            list.addAll(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "addAll(null) should throw NullPointerException");
        check(list.equals(Arrays.asList("x")), "list should be unchanged after failed addAll(null)");
    }
}
