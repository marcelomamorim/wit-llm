/*
 * @test
 * @summary Behavioral tests for LinkedList.set(int, E): returns the previous element, updates the slot, keeps size, and rejects out-of-range indexes.
 * @run main LinkedListWitupTest
 */

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testReturnsOldValueAndUpdatesSlot();
        testFirstAndLastIndex();
        testNullElement();
        testOutOfRangeIndexThrowsAndLeavesListUnchanged();
        testEmptyListThrows();
        testListCreatedFromCollection();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testReturnsOldValueAndUpdatesSlot() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        String old = list.set(1, "B");
        check("b".equals(old), "set should return previous value, got " + old);
        check("B".equals(list.get(1)), "set should store new value");
        check(list.size() == 3, "set must not change size");
        check(list.get(0).equals("a"), "neighbor at index 0 changed");
        check(list.get(2).equals("c"), "neighbor at index 2 changed");
    }

    static void testFirstAndLastIndex() {
        LinkedList<Integer> list = new LinkedList<>(Arrays.asList(10, 20, 30));

        Integer first = list.set(0, 11);
        check(first == 10, "first index old value wrong: " + first);
        check(list.getFirst() == 11, "first element not updated");

        Integer last = list.set(2, 33);
        check(last == 30, "last index old value wrong: " + last);
        check(list.getLast() == 33, "last element not updated");
        check(list.size() == 3, "size changed after set");
        check(list.get(1) == 20, "middle element changed");
    }

    static void testNullElement() {
        LinkedList<String> list = new LinkedList<>(Arrays.asList("x", "y"));

        String old = list.set(0, null);
        check("x".equals(old), "old value wrong when setting null");
        check(list.get(0) == null, "null element not stored");
        check(list.size() == 2, "size changed when storing null");

        String previousNull = list.set(0, "z");
        check(previousNull == null, "set should return null when slot held null");
        check("z".equals(list.get(0)), "non-null value not restored");
    }

    static void testOutOfRangeIndexThrowsAndLeavesListUnchanged() {
        LinkedList<String> list = new LinkedList<>(Arrays.asList("p", "q"));

        expectIndexOutOfBounds(list, -1);
        expectIndexOutOfBounds(list, 2);
        expectIndexOutOfBounds(list, Integer.MAX_VALUE);

        check(list.size() == 2, "size changed after failed set");
        check("p".equals(list.get(0)), "element 0 changed after failed set");
        check("q".equals(list.get(1)), "element 1 changed after failed set");
    }

    static void testEmptyListThrows() {
        LinkedList<String> list = new LinkedList<>();
        expectIndexOutOfBounds(list, 0);
        check(list.isEmpty(), "empty list became non-empty after failed set");
    }

    static void testListCreatedFromCollection() {
        List<String> source = Arrays.asList("one", "two", "three");
        LinkedList<String> list = new LinkedList<>(source);

        String old = list.set(2, "THREE");
        check("three".equals(old), "old value wrong for collection-built list");
        check(list.equals(Arrays.asList("one", "two", "THREE")), "list contents wrong after set");
        check(source.get(2).equals("three"), "source collection was modified");
    }

    static void expectIndexOutOfBounds(LinkedList<String> list, int index) {
        try {
            list.set(index, "x");
        } catch (IndexOutOfBoundsException expected) {
            return;
        }
        throw new RuntimeException("expected IndexOutOfBoundsException for index " + index);
    }
}
