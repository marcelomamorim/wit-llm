/*
 * @test
 * @summary Tests for ArrayList.subList method to ensure correct behavior and exception handling.
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.List;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testSubListValidRange();
        testSubListFromIndexNegative();
        testSubListToIndexNegative();
        testSubListFromIndexGreaterThanSize();
        testSubListToIndexGreaterThanSize();
        testSubListFromIndexGreaterThanToIndex();
    }

    static void testSubListValidRange() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        List<Integer> sub = list.subList(2, 5);
        assert sub.size() == 3 : "Expected size 3, got " + sub.size();
        assert sub.get(0) == 2 : "Expected 2, got " + sub.get(0);
        assert sub.get(2) == 4 : "Expected 4, got " + sub.get(2);
    }

    static void testSubListFromIndexNegative() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        try {
            list.subList(-1, 1);
            assert false : "Expected IndexOutOfBoundsException for negative fromIndex";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testSubListToIndexNegative() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        try {
            list.subList(0, -1);
            assert false : "Expected IndexOutOfBoundsException for negative toIndex";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testSubListFromIndexGreaterThanSize() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        try {
            list.subList(2, 3);
            assert false : "Expected IndexOutOfBoundsException for fromIndex > size";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testSubListToIndexGreaterThanSize() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        try {
            list.subList(0, 2);
            assert false : "Expected IndexOutOfBoundsException for toIndex > size";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testSubListFromIndexGreaterThanToIndex() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        try {
            list.subList(3, 2);
            assert false : "Expected IllegalArgumentException for fromIndex > toIndex";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
