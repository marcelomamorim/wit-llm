/*
 * @test
 * @summary Tests CopyOnWriteArrayList.removeAll behavior
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRemoveSomeMatches() {
        CopyOnWriteArrayList<Integer> list =
            new CopyOnWriteArrayList<>(new Integer[]{1, 2, 3, 4, 5});
        boolean changed = list.removeAll(Arrays.asList(2, 4));
        check(changed, "removeAll should return true when elements are removed");
        check(list.size() == 3, "expected size 3 but was " + list.size());
        check(list.get(0) == 1, "unexpected element at 0");
        check(list.get(1) == 3, "unexpected element at 1");
        check(list.get(2) == 5, "unexpected element at 2");
    }

    static void testNoMatches() {
        CopyOnWriteArrayList<Integer> list =
            new CopyOnWriteArrayList<>(new Integer[]{1, 2, 3});
        boolean changed = list.removeAll(Arrays.asList(9));
        check(!changed, "removeAll should return false when nothing matches");
        check(list.size() == 3, "list should be unchanged");
        check(list.get(0) == 1 && list.get(1) == 2 && list.get(2) == 3,
              "list contents should be unchanged");
    }

    static void testEmptyArgument() {
        CopyOnWriteArrayList<Integer> list =
            new CopyOnWriteArrayList<>(new Integer[]{1, 2});
        boolean changed = list.removeAll(Arrays.asList());
        check(!changed, "removeAll with empty collection should return false");
        check(list.size() == 2, "list should be unchanged for empty argument");
    }

    static void testEmptyList() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        boolean changed = list.removeAll(Arrays.asList(1));
        check(!changed, "removeAll on empty list should return false");
        check(list.isEmpty(), "list should remain empty");
    }

    static void testNullElementsRemoved() {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<>(new String[]{"a", null, "b", null});
        boolean changed = list.removeAll(Arrays.asList((String) null));
        check(changed, "removeAll should remove null elements");
        check(list.size() == 2, "expected size 2 but was " + list.size());
        check("a".equals(list.get(0)), "unexpected element at 0");
        check("b".equals(list.get(1)), "unexpected element at 1");
    }

    static void testDuplicatesRemoved() {
        CopyOnWriteArrayList<Integer> list =
            new CopyOnWriteArrayList<>(new Integer[]{1, 1, 2, 1});
        boolean changed = list.removeAll(Arrays.asList(1));
        check(changed, "removeAll should return true");
        check(list.size() == 1, "expected size 1 but was " + list.size());
        check(list.get(0) == 2, "remaining element should be 2");
    }

    static void testRemoveAllElements() {
        CopyOnWriteArrayList<Integer> list =
            new CopyOnWriteArrayList<>(new Integer[]{1, 2, 3});
        boolean changed = list.removeAll(Arrays.asList(1, 2, 3));
        check(changed, "removeAll should return true");
        check(list.isEmpty(), "list should be empty");
    }

    static void testOrderPreserved() {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<>(new String[]{"x", "y", "z", "w"});
        list.removeAll(Arrays.asList("y"));
        check(list.size() == 3, "expected size 3 but was " + list.size());
        check("x".equals(list.get(0)), "order not preserved at 0");
        check("z".equals(list.get(1)), "order not preserved at 1");
        check("w".equals(list.get(2)), "order not preserved at 2");
    }

    static void testNullArgumentThrows() {
        CopyOnWriteArrayList<Integer> list =
            new CopyOnWriteArrayList<>(new Integer[]{1, 2});
        boolean thrown = false;
        try {
            list.removeAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "removeAll(null) should throw NullPointerException");
        check(list.size() == 2, "list should be unchanged after NPE");
    }

    public static void main(String[] args) {
        testRemoveSomeMatches();
        testNoMatches();
        testEmptyArgument();
        testEmptyList();
        testNullElementsRemoved();
        testDuplicatesRemoved();
        testRemoveAllElements();
        testOrderPreserved();
        testNullArgumentThrows();
    }
}
