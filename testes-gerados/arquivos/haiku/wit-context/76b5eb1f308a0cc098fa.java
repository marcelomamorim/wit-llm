/*
 * @test
 * @summary Behavioral tests for Vector.sort(Comparator) covering empty, single, natural order, custom comparator, non-comparable elements and concurrent modification from the comparator
 * @run main VectorWitupTest
 */

import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testEmptyVector();
        testSingleElement();
        testNaturalOrderWithNullComparator();
        testCustomComparator();
        testNonComparableWithNullComparatorThrowsClassCastException();
        testComparatorModifyingVectorThrowsConcurrentModificationException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyVector() {
        Vector<Integer> v = new Vector<>();
        v.sort(null);
        check(v.isEmpty(), "empty vector must remain empty with null comparator");
        v.sort(Comparator.reverseOrder());
        check(v.isEmpty(), "empty vector must remain empty with reverse comparator");
    }

    static void testSingleElement() {
        Vector<Integer> v = new Vector<>();
        v.add(42);
        v.sort(null);
        check(v.size() == 1, "size must be unchanged after sorting single element");
        check(v.get(0).equals(Integer.valueOf(42)), "single element must be unchanged");
    }

    static void testNaturalOrderWithNullComparator() {
        Vector<Integer> v = new Vector<>();
        v.add(3);
        v.add(1);
        v.add(2);
        v.add(1);
        v.sort(null);
        check(v.size() == 4, "size must be preserved");
        check(v.get(0).equals(1), "index 0 must be 1");
        check(v.get(1).equals(1), "index 1 must be 1");
        check(v.get(2).equals(2), "index 2 must be 2");
        check(v.get(3).equals(3), "index 3 must be 3");
    }

    static void testCustomComparator() {
        Vector<Integer> v = new Vector<>();
        v.add(1);
        v.add(3);
        v.add(2);
        v.sort(Comparator.reverseOrder());
        check(v.get(0).equals(3), "index 0 must be 3 in reverse order");
        check(v.get(1).equals(2), "index 1 must be 2 in reverse order");
        check(v.get(2).equals(1), "index 2 must be 1 in reverse order");
    }

    static void testNonComparableWithNullComparatorThrowsClassCastException() {
        Vector<Object> v = new Vector<>();
        v.add(new Object());
        v.add(new Object());
        boolean thrown = false;
        try {
            v.sort(null);
        } catch (ClassCastException e) {
            thrown = true;
        }
        check(thrown, "non-comparable elements with null comparator must throw ClassCastException");
    }

    static void testComparatorModifyingVectorThrowsConcurrentModificationException() {
        final Vector<Integer> v = new Vector<>();
        v.add(3);
        v.add(1);
        v.add(2);
        final boolean[] added = new boolean[1];
        Comparator<Integer> c = (a, b) -> {
            if (!added[0]) {
                added[0] = true;
                v.add(99);
            }
            return a.compareTo(b);
        };
        boolean thrown = false;
        try {
            v.sort(c);
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "structural modification during sort must throw ConcurrentModificationException");
    }
}
