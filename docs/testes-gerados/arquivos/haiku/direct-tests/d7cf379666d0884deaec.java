/*
 * @test
 * @summary Behavioral tests for ArrayList.retainAll(Collection)
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testRetainsOnlyMatchingElements();
        testReturnsFalseWhenNothingRemoved();
        testEmptyListReturnsFalse();
        testEmptyCollectionClearsList();
        testKeepsAllOccurrencesOfRetainedElements();
        testNullElementsAreRetainedWhenContained();
        testListRemainsUsableAfterRetainAll();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRetainsOnlyMatchingElements() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        boolean changed = list.retainAll(Arrays.asList(2, 4, 6));
        check(changed, "retainAll should return true when elements are removed");
        check(list.equals(Arrays.asList(2, 4)), "unexpected contents: " + list);
        check(list.size() == 2, "unexpected size: " + list.size());
    }

    static void testReturnsFalseWhenNothingRemoved() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2));
        boolean changed = list.retainAll(Arrays.asList(1, 2, 3));
        check(!changed, "retainAll should return false when no element is removed");
        check(list.equals(Arrays.asList(1, 2)), "list should be unchanged: " + list);
    }

    static void testEmptyListReturnsFalse() {
        ArrayList<Integer> list = new ArrayList<>();
        boolean changed = list.retainAll(Collections.<Integer>emptyList());
        check(!changed, "empty list should report no change");
        check(list.isEmpty(), "empty list should remain empty");
    }

    static void testEmptyCollectionClearsList() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        boolean changed = list.retainAll(new ArrayList<Integer>());
        check(changed, "retaining against an empty collection should return true");
        check(list.isEmpty(), "list should be empty after retaining nothing");
    }

    static void testKeepsAllOccurrencesOfRetainedElements() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 1, 3));
        boolean changed = list.retainAll(Arrays.asList(1));
        check(changed, "retainAll should return true when elements are removed");
        check(list.equals(Arrays.asList(1, 1)), "duplicates of retained element should stay: " + list);
    }

    static void testNullElementsAreRetainedWhenContained() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList(null, "a", "b"));
        boolean changed = list.retainAll(Arrays.asList(null, "a"));
        check(changed, "retainAll should return true when \"b\" is removed");
        check(list.equals(Arrays.asList(null, "a")), "null should be retained: " + list);
    }

    static void testListRemainsUsableAfterRetainAll() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        list.retainAll(Arrays.asList(3));
        check(list.equals(Arrays.asList(3)), "unexpected contents after retainAll: " + list);
        list.add(7);
        check(list.equals(Arrays.asList(3, 7)), "list should accept additions after retainAll: " + list);
        check(list.get(1) == 7, "get should return added element");
    }
}
