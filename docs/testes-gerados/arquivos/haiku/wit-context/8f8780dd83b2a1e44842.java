/*
 * @test
 * @summary Behavioral tests for ArrayList.retainAll(Collection), including null-argument rejection
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testRetainAllRemovesNonMatching();
        testRetainAllNoChangeReturnsFalse();
        testRetainAllEmptyCollectionClearsList();
        testRetainAllNullElementsPreserved();
        testRetainAllNullCollectionThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRetainAllRemovesNonMatching() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        boolean changed = list.retainAll(Arrays.asList(2, 4, 5));
        check(changed, "retainAll should report modification");
        check(list.equals(Arrays.asList(2, 4)), "unexpected content: " + list);
    }

    static void testRetainAllNoChangeReturnsFalse() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2));
        boolean changed = list.retainAll(Arrays.asList(1, 2, 3));
        check(!changed, "retainAll should return false when nothing removed");
        check(list.equals(Arrays.asList(1, 2)), "list should be unchanged");
    }

    static void testRetainAllEmptyCollectionClearsList() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        boolean changed = list.retainAll(new ArrayList<Integer>());
        check(changed, "retainAll with empty collection should modify list");
        check(list.isEmpty(), "list should be empty");
    }

    static void testRetainAllNullElementsPreserved() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(null, 1));
        boolean changed = list.retainAll(Arrays.asList(null, 1));
        check(!changed, "null elements contained in argument should be retained");
        check(list.size() == 2 && list.get(0) == null && list.get(1) == 1,
              "unexpected content: " + list);
    }

    static void testRetainAllNullCollectionThrowsNPE() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        boolean thrown = false;
        try {
            list.retainAll(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        check(thrown, "retainAll(null) should throw NullPointerException");
        check(list.size() == 3, "list should be unchanged after NPE");
    }
}
