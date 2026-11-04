/*
 * @test
 * @summary Witup-generated tests for ArrayList.sort(Comparator)
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ConcurrentModificationException;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testEmptyListWithComparator();
        testReverseComparator();
        testNullComparatorNaturalOrder();
        testNullComparatorNonComparableThrows();
        testComparatorStructuralModificationThrows();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testEmptyListWithComparator() {
        ArrayList<Integer> list = new ArrayList<>();
        list.sort(Comparator.naturalOrder());
        check(list.isEmpty(), "empty list must remain empty");
    }

    static void testReverseComparator() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(2, 5, 1, 4));
        list.sort(Comparator.reverseOrder());
        check(list.equals(Arrays.asList(5, 4, 2, 1)), "reverse sort order");
    }

    static void testNullComparatorNaturalOrder() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(3, 1, 2));
        list.sort(null);
        check(list.equals(Arrays.asList(1, 2, 3)), "null comparator must use natural order");
    }

    static void testNullComparatorNonComparableThrows() {
        ArrayList<Object> list = new ArrayList<>();
        list.add(new Object());
        list.add(new Object());
        try {
            list.sort(null);
            throw new RuntimeException("expected ClassCastException");
        } catch (ClassCastException e) {
            // expected
        }
    }

    static void testComparatorStructuralModificationThrows() {
        final ArrayList<Integer> list = new ArrayList<>(Arrays.asList(3, 1, 2));
        final boolean[] added = {false};
        Comparator<Integer> c = (a, b) -> {
            if (!added[0]) {
                added[0] = true;
                list.add(99);
            }
            return a.compareTo(b);
        };
        try {
            list.sort(c);
            throw new RuntimeException("expected ConcurrentModificationException");
        } catch (ConcurrentModificationException e) {
            // expected
        }
    }
}
