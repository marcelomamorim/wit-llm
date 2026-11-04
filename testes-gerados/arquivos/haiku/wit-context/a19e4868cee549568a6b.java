/*
 * @test
 * @summary WITUP-derived tests for CopyOnWriteArrayList.removeAll(Collection)
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testNullArgumentThrowsNPE();
        testNullArgumentLeavesListUnchanged();
        testRemoveAllRemovesMatchingElements();
        testRemoveAllWithoutMatchReturnsFalse();
        testRemoveAllWithEmptyCollectionReturnsFalse();
        testRemoveAllOnEmptyListReturnsFalse();
    }

    static void testNullArgumentThrowsNPE() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>(Arrays.asList(1, 2, 3));
        boolean thrown = false;
        try {
            list.removeAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "removeAll(null) must throw NullPointerException");
    }

    static void testNullArgumentLeavesListUnchanged() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>(Arrays.asList(1, 2, 3));
        try {
            list.removeAll(null);
        } catch (NullPointerException expected) {
        }
        check(list.equals(Arrays.asList(1, 2, 3)), "list must be unchanged after removeAll(null)");
    }

    static void testRemoveAllRemovesMatchingElements() {
        CopyOnWriteArrayList<Integer> list =
            new CopyOnWriteArrayList<>(Arrays.asList(1, 2, 3, 2, null));
        boolean changed = list.removeAll(Arrays.asList(2, null));
        check(changed, "removeAll must return true when elements are removed");
        check(list.equals(Arrays.asList(1, 3)),
              "unexpected list content after removeAll: " + list);
    }

    static void testRemoveAllWithoutMatchReturnsFalse() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>(Arrays.asList(1, 2, 3));
        boolean changed = list.removeAll(Arrays.asList(4, 5));
        check(!changed, "removeAll must return false when nothing is removed");
        check(list.equals(Arrays.asList(1, 2, 3)), "list must be unchanged when nothing matches");
    }

    static void testRemoveAllWithEmptyCollectionReturnsFalse() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>(Arrays.asList(1, 2));
        boolean changed = list.removeAll(Collections.emptyList());
        check(!changed, "removeAll(empty) must return false");
        check(list.equals(Arrays.asList(1, 2)), "list must be unchanged by removeAll(empty)");
    }

    static void testRemoveAllOnEmptyListReturnsFalse() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        boolean changed = list.removeAll(Arrays.asList(1, 2));
        check(!changed, "removeAll on empty list must return false");
        check(list.isEmpty(), "empty list must remain empty");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
