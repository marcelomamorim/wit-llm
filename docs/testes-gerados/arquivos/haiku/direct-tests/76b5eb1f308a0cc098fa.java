/*
 * @test
 * @summary Behavioral tests for Vector.sort(Comparator) ordering, empty/null-comparator handling, modCount effects and ConcurrentModificationException
 * @run main VectorWitupTest
 */

import java.util.Arrays;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testSortAscendingWithNaturalOrderComparator();
        testSortWithNullComparatorUsesNaturalOrder();
        testSortEmptyAndSingleElement();
        testSortOnlyAffectsElementCount();
        testSortWithDescendingComparator();
        testSortInvalidatesExistingIterator();
        testComparatorModifyingVectorThrowsConcurrentModificationException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSortAscendingWithNaturalOrderComparator() {
        Vector<Integer> v = new Vector<>();
        v.add(5);
        v.add(3);
        v.add(9);
        v.add(1);
        v.sort((a, b) -> a.compareTo(b));
        check(v.size() == 4, "size changed by sort");
        check(v.get(0) == 1, "index 0 expected 1 but was " + v.get(0));
        check(v.get(1) == 3, "index 1 expected 3 but was " + v.get(1));
        check(v.get(2) == 5, "index 2 expected 5 but was " + v.get(2));
        check(v.get(3) == 9, "index 3 expected 9 but was " + v.get(3));
    }

    static void testSortWithNullComparatorUsesNaturalOrder() {
        Vector<Integer> v = new Vector<>();
        v.add(4);
        v.add(2);
        v.add(8);
        v.sort(null);
        check(v.equals(Arrays.asList(2, 4, 8)), "null comparator did not sort naturally: " + v);
    }

    static void testSortEmptyAndSingleElement() {
        Vector<Integer> empty = new Vector<>();
        empty.sort(null);
        check(empty.isEmpty(), "empty vector changed by sort");

        Vector<Integer> single = new Vector<>();
        single.add(7);
        single.sort(null);
        check(single.size() == 1 && single.get(0) == 7, "single element changed by sort");
    }

    static void testSortOnlyAffectsElementCount() {
        Vector<Integer> v = new Vector<>(10);
        v.add(3);
        v.add(1);
        v.add(2);
        v.sort(null);
        check(v.size() == 3, "size expected 3 but was " + v.size());
        check(v.equals(Arrays.asList(1, 2, 3)), "unexpected contents: " + v);
    }

    static void testSortWithDescendingComparator() {
        Vector<Integer> v = new Vector<>();
        v.add(2);
        v.add(7);
        v.add(4);
        Comparator<Integer> desc = (a, b) -> b.compareTo(a);
        v.sort(desc);
        check(v.equals(Arrays.asList(7, 4, 2)), "descending sort wrong: " + v);
    }

    static void testSortInvalidatesExistingIterator() {
        Vector<Integer> v = new Vector<>();
        v.add(1);
        v.add(2);
        v.add(3);
        Iterator<Integer> it = v.iterator();
        v.sort(null);
        boolean thrown = false;
        try {
            it.next();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "iterator created before sort did not fail-fast");
    }

    static void testComparatorModifyingVectorThrowsConcurrentModificationException() {
        Vector<Integer> v = new Vector<>();
        v.add(3);
        v.add(1);
        v.add(2);
        final boolean[] modified = {false};
        Comparator<Integer> c = (a, b) -> {
            if (!modified[0]) {
                modified[0] = true;
                v.add(100);
            }
            return a.compareTo(b);
        };
        boolean thrown = false;
        try {
            v.sort(c);
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "structural modification during sort did not throw ConcurrentModificationException");
    }
}
