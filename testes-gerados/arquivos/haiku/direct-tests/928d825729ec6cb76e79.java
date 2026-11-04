/*
 * @test
 * @summary Behavioral tests for ArrayList.sort(Comparator), including null comparator, empty list, iterator invalidation and comparator-driven structural modification
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testNullComparatorUsesNaturalOrder();
        testReverseComparator();
        testEmptyListSort();
        testSortInvalidatesExistingIterator();
        testStructuralModificationDuringSortThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullComparatorUsesNaturalOrder() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(1);
        list.add(2);
        list.sort(null);
        check(list.equals(List.of(1, 2, 3)), "null comparator should sort naturally: " + list);
    }

    static void testReverseComparator() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("c");
        list.add("b");
        list.sort(Comparator.reverseOrder());
        check(list.equals(List.of("c", "b", "a")), "reverse order failed: " + list);
    }

    static void testEmptyListSort() {
        ArrayList<Integer> list = new ArrayList<>();
        list.sort(Comparator.naturalOrder());
        check(list.isEmpty(), "empty list should remain empty");
    }

    static void testSortInvalidatesExistingIterator() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        Iterator<Integer> it = list.iterator();
        list.sort(null);
        boolean thrown = false;
        try {
            it.next();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "iterator obtained before sort should be invalidated");
    }

    static void testStructuralModificationDuringSortThrows() {
        final ArrayList<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(1);
        list.add(2);
        final boolean[] added = new boolean[1];
        Comparator<Integer> c = (a, b) -> {
            if (!added[0]) {
                added[0] = true;
                list.add(99);
            }
            return a.compareTo(b);
        };
        boolean thrown = false;
        try {
            list.sort(c);
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(added[0], "comparator should have been invoked");
        check(thrown, "structural modification during sort should throw ConcurrentModificationException");
    }
}
