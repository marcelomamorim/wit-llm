/*
 * @test
 * @summary Test java.util.ArrayList.subList(int, int) method behavior
 * @run main java.util.ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.List;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testSubListBasic();
        testSubListEmpty();
        testSubListFullRange();
        testSubListInvalidRange();
    }

    private static void testSubListBasic() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");

        List<String> sub = list.subList(1, 4);
        if (sub.size() != 3) {
            throw new AssertionError("Expected sublist size 3 but got " + sub.size());
        }
        if (!"b".equals(sub.get(0)) || !"d".equals(sub.get(2))) {
            throw new AssertionError("Sublist elements mismatch");
        }
    }

    private static void testSubListEmpty() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(20);

        List<Integer> sub = list.subList(1, 1);
        if (sub.size() != 0) {
            throw new AssertionError("Expected empty sublist but size is " + sub.size());
        }
    }

    private static void testSubListFullRange() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");

        List<String> sub = list.subList(0, list.size());
        if (sub.size() != list.size()) {
            throw new AssertionError("Expected sublist size " + list.size() + " but got " + sub.size());
        }
        if (!sub.get(0).equals("x") || !sub.get(1).equals("y")) {
            throw new AssertionError("Sublist elements mismatch for full range");
        }
    }

    private static void testSubListInvalidRange() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");

        boolean caught = false;
        try {
            list.subList(-1, 1);
        } catch (IndexOutOfBoundsException e) {
            caught = true;
        }
        if (!caught) {
            throw new AssertionError("Expected IndexOutOfBoundsException for negative fromIndex");
        }

        caught = false;
        try {
            list.subList(1, 3);
        } catch (IndexOutOfBoundsException e) {
            caught = true;
        }
        if (!caught) {
            throw new AssertionError("Expected IndexOutOfBoundsException for toIndex > size");
        }

        caught = false;
        try {
            list.subList(2, 1);
        } catch (IllegalArgumentException e) {
            caught = true;
        }
        if (!caught) {
            throw new AssertionError("Expected IllegalArgumentException for fromIndex > toIndex");
        }
    }
}
