/*
 * @test
 * @summary ArrayList.retainAll keeps only the elements contained in the given collection and reports whether the list changed
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testRetainSubsetRemovesOthers();
        testRetainAllContainingReturnsFalse();
        testRetainEmptyCollectionClearsList();
        testRetainKeepsDuplicatesOfRetainedValues();
        testRetainNullElement();
        testRetainOnEmptyListReturnsFalse();
        testRetainWithSetPreservesOrder();
    }

    static void testRetainSubsetRemovesOthers() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        boolean changed = list.retainAll(Arrays.asList(2, 4, 6));
        check(changed, "retainAll should return true when elements are removed");
        check(list.equals(Arrays.asList(2, 4)), "expected [2, 4] but was " + list);
        check(list.size() == 2, "size should be 2");
    }

    static void testRetainAllContainingReturnsFalse() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        boolean changed = list.retainAll(Arrays.asList(1, 2, 3, 4));
        check(!changed, "retainAll should return false when nothing is removed");
        check(list.equals(Arrays.asList(1, 2, 3)), "list should be unchanged but was " + list);
    }

    static void testRetainEmptyCollectionClearsList() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(7, 8, 9));
        boolean changed = list.retainAll(Collections.<Integer>emptyList());
        check(changed, "retainAll with empty collection should return true");
        check(list.isEmpty(), "list should be empty but was " + list);
    }

    static void testRetainKeepsDuplicatesOfRetainedValues() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 1, 2, 2, 3));
        boolean changed = list.retainAll(Collections.singletonList(1));
        check(changed, "retainAll should return true when 2 and 3 are removed");
        check(list.equals(Arrays.asList(1, 1)), "expected [1, 1] but was " + list);
    }

    static void testRetainNullElement() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(null, 1, null, 2));
        boolean changed = list.retainAll(Collections.<Integer>singletonList(null));
        check(changed, "retainAll should return true when non-null elements are removed");
        check(list.equals(Arrays.asList(null, null)), "expected [null, null] but was " + list);
    }

    static void testRetainOnEmptyListReturnsFalse() {
        ArrayList<Integer> list = new ArrayList<>();
        boolean changed = list.retainAll(Arrays.asList(1, 2));
        check(!changed, "retainAll on empty list should return false");
        check(list.isEmpty(), "list should remain empty");
    }

    static void testRetainWithSetPreservesOrder() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(5, 3, 1, 4, 2));
        Set<Integer> keep = new HashSet<>(Arrays.asList(1, 2, 3));
        boolean changed = list.retainAll(keep);
        check(changed, "retainAll should return true");
        check(list.equals(Arrays.asList(3, 1, 2)), "expected [3, 1, 2] but was " + list);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
