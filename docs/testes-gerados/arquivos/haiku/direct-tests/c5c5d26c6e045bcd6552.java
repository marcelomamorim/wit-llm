/*
 * @test
 * @summary Tests AbstractList.addAll(Collection) appends elements at the end via addAll(size(), c)
 * @run main AbstractListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.AbstractList;
import java.util.Collection;
import java.util.List;

public class AbstractListWitupTest {

    static class BackedList extends AbstractList<String> {
        final ArrayList<String> data = new ArrayList<>();

        @Override
        public String get(int index) {
            return data.get(index);
        }

        @Override
        public int size() {
            return data.size();
        }

        @Override
        public void add(int index, String e) {
            data.add(index, e);
        }
    }

    static class ReadOnlyList extends AbstractList<String> {
        @Override
        public String get(int index) {
            return "v" + index;
        }

        @Override
        public int size() {
            return 0;
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testAddAllToEmptyList() {
        BackedList list = new BackedList();
        Collection<String> c = Arrays.asList("a", "b");
        boolean changed = list.addAll(c);
        check(changed, "addAll should return true when elements are added");
        check(list.size() == 2, "size should be 2 but was " + list.size());
        check(list.get(0).equals("a"), "element 0 should be a");
        check(list.get(1).equals("b"), "element 1 should be b");
    }

    static void testAddAllAppendsToEnd() {
        BackedList list = new BackedList();
        list.add("x");
        boolean changed = list.addAll(Arrays.asList("a", "b"));
        check(changed, "addAll should return true");
        List<String> expected = Arrays.asList("x", "a", "b");
        check(list.equals(expected), "expected [x, a, b] but was " + list);
    }

    static void testAddAllEmptyCollectionReturnsFalse() {
        BackedList list = new BackedList();
        list.add("x");
        boolean changed = list.addAll(new ArrayList<String>());
        check(!changed, "addAll with empty collection should return false");
        check(list.size() == 1 && list.get(0).equals("x"), "list should be unchanged");
    }

    static void testAddAllUnsupportedListNonEmpty() {
        ReadOnlyList list = new ReadOnlyList();
        try {
            list.addAll(Arrays.asList("a"));
            throw new RuntimeException("expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    static void testAddAllUnsupportedListEmptyCollection() {
        ReadOnlyList list = new ReadOnlyList();
        boolean changed = list.addAll(new ArrayList<String>());
        check(!changed, "addAll with empty collection should return false without adding");
    }

    public static void main(String[] args) {
        testAddAllToEmptyList();
        testAddAllAppendsToEnd();
        testAddAllEmptyCollectionReturnsFalse();
        testAddAllUnsupportedListNonEmpty();
        testAddAllUnsupportedListEmptyCollection();
    }
}
