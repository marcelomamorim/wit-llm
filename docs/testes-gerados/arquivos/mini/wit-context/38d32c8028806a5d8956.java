/*
 * @test
 * @summary Test java.util.ArrayList.subList(int, int) for exception and normal cases
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.List;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testSubListValidRange();
        testSubListFromIndexNegative();
        testSubListToIndexGreaterThanSize();
        testSubListFromIndexGreaterThanToIndex();
    }

    private static void testSubListValidRange() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        List<String> sub = list.subList(0, 3);
        if (sub.size() != 3) {
            throw new AssertionError("Expected sublist size 3 but got " + sub.size());
        }
        if (!"a".equals(sub.get(0)) || !"b".equals(sub.get(1)) || !"c".equals(sub.get(2))) {
            throw new AssertionError("Sublist elements do not match expected values");
        }
    }

    private static void testSubListFromIndexNegative() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        try {
            list.subList(-1, 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for fromIndex < 0");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testSubListToIndexGreaterThanSize() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        try {
            list.subList(0, 2); // toIndex > size
            throw new AssertionError("Expected IndexOutOfBoundsException for toIndex > size");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testSubListFromIndexGreaterThanToIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        try {
            list.subList(1, 0); // fromIndex > toIndex
            throw new AssertionError("Expected IllegalArgumentException for fromIndex > toIndex");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
