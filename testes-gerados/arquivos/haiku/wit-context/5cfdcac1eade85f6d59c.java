/*
 * @test
 * @summary Observable hashCode behavior of ArrayList and its SubList view, including fail-fast comodification detection
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class ArrayListWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyListHashCode() {
        List<Integer> list = new ArrayList<>();
        check(list.hashCode() == 1, "empty list hashCode must be 1, was " + list.hashCode());
    }

    static void testKnownHashCode() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        check(list.hashCode() == 30817, "hashCode of [1,2,3] must be 30817, was " + list.hashCode());
    }

    static void testNullElementContributesZero() {
        List<Integer> list = new ArrayList<>();
        list.add(null);
        check(list.hashCode() == 31, "hashCode of [null] must be 31, was " + list.hashCode());
    }

    static void testEqualListsHaveEqualHashCodes() {
        List<String> a = new ArrayList<>();
        List<String> b = new ArrayList<>();
        a.add("x");
        a.add(null);
        b.add("x");
        b.add(null);
        check(a.equals(b), "lists must be equal");
        check(a.hashCode() == b.hashCode(), "equal lists must have equal hashCode");
    }

    static void testSubListHashCodeMatchesEquivalentList() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        List<Integer> sub = list.subList(1, 3);
        List<Integer> expected = new ArrayList<>();
        expected.add(2);
        expected.add(3);
        check(sub.hashCode() == expected.hashCode(),
              "subList hashCode must match equivalent list, was " + sub.hashCode());
        check(sub.hashCode() == 1026, "subList [2,3] hashCode must be 1026, was " + sub.hashCode());
    }

    static void testSubListHashCodeAfterStructuralModificationThrows() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        List<Integer> sub = list.subList(0, 2);
        list.add(5);
        boolean thrown = false;
        try {
            sub.hashCode();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "subList hashCode after structural modification of root must throw ConcurrentModificationException");
    }

    public static void main(String[] args) {
        testEmptyListHashCode();
        testKnownHashCode();
        testNullElementContributesZero();
        testEqualListsHaveEqualHashCodes();
        testSubListHashCodeMatchesEquivalentList();
        testSubListHashCodeAfterStructuralModificationThrows();
    }
}
