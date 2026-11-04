/*
 * @test
 * @summary Witup-derived behavioral checks for toArray() on ArrayList sublists (fail-fast and range copy)
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testSubListToArrayReturnsRange();
        testEmptySubListToArray();
        testSetIsNotStructuralModification();
        testAddThrowsComodification();
        testRemoveThrowsComodification();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSubListToArrayReturnsRange() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        Object[] arr = list.subList(1, 3).toArray();
        check(arr.length == 2, "expected length 2 but was " + arr.length);
        check("b".equals(arr[0]), "expected b at 0");
        check("c".equals(arr[1]), "expected c at 1");
    }

    static void testEmptySubListToArray() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        Object[] arr = list.subList(1, 1).toArray();
        check(arr.length == 0, "expected empty array but was length " + arr.length);
    }

    static void testSetIsNotStructuralModification() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        List<String> sub = list.subList(0, 2);
        list.set(0, "z");
        Object[] arr = sub.toArray();
        check(arr.length == 2, "expected length 2 but was " + arr.length);
        check("z".equals(arr[0]), "expected updated value z at 0");
        check("b".equals(arr[1]), "expected b at 1");
    }

    static void testAddThrowsComodification() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        List<String> sub = list.subList(0, 2);
        list.add("d");
        boolean thrown = false;
        try {
            sub.toArray();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "expected ConcurrentModificationException after structural add on root");
    }

    static void testRemoveThrowsComodification() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        List<String> sub = list.subList(0, 2);
        list.remove(0);
        boolean thrown = false;
        try {
            sub.toArray();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "expected ConcurrentModificationException after structural remove on root");
    }
}
