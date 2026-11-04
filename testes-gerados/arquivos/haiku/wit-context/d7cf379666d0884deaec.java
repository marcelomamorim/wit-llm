/*
 * @test
 * @summary Behavioral tests for ArrayList.retainAll(Collection) including null collection handling
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testNullCollectionThrowsNPE();
        testRetainsOnlyMatchingElementsInOrder();
        testReturnsFalseWhenNothingRemoved();
        testEmptyRetainClearsList();
        testRetainOnEmptyList();
        testNullElementsAreRetained();
    }

    private static void testNullCollectionThrowsNPE() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        boolean thrown = false;
        try {
            list.retainAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "retainAll(null) must throw NullPointerException");
        check(list.equals(Arrays.asList(1, 2, 3)), "list must be unchanged after NPE");
    }

    private static void testRetainsOnlyMatchingElementsInOrder() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 2, 5));
        boolean changed = list.retainAll(Arrays.asList(2, 4, 5));
        check(changed, "retainAll must report modification");
        check(list.equals(Arrays.asList(2, 4, 2, 5)), "unexpected contents: " + list);
    }

    private static void testReturnsFalseWhenNothingRemoved() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        boolean changed = list.retainAll(Arrays.asList(1, 2, 3, 4));
        check(!changed, "retainAll must return false when no element removed");
        check(list.equals(Arrays.asList(1, 2, 3)), "list must be unchanged");
    }

    private static void testEmptyRetainClearsList() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        boolean changed = list.retainAll(new ArrayList<Integer>());
        check(changed, "retainAll with empty collection must report modification");
        check(list.isEmpty(), "list must be empty");
    }

    private static void testRetainOnEmptyList() {
        ArrayList<Integer> list = new ArrayList<>();
        boolean changed = list.retainAll(Arrays.asList(1, 2));
        check(!changed, "retainAll on empty list must return false");
        check(list.isEmpty(), "list must remain empty");
    }

    private static void testNullElementsAreRetained() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, null, 2, null));
        boolean changed = list.retainAll(Arrays.asList(null, 2));
        check(changed, "retainAll must report modification");
        List<Integer> expected = Arrays.asList(null, 2, null);
        check(list.equals(expected), "unexpected contents: " + list);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
