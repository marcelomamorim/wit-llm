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
        testSubListFull();
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
            throw new RuntimeException("Expected sublist size 3 but got " + sub.size());
        }
        if (!"b".equals(sub.get(0)) || !"c".equals(sub.get(1)) || !"d".equals(sub.get(2))) {
            throw new RuntimeException("Sublist elements do not match expected values");
        }
    }

    private static void testSubListEmpty() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(20);

        List<Integer> sub = list.subList(1, 1);
        if (sub.size() != 0) {
            throw new RuntimeException("Expected empty sublist but size was " + sub.size());
        }
    }

    private static void testSubListFull() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");

        List<String> sub = list.subList(0, list.size());
        if (sub.size() != list.size()) {
            throw new RuntimeException("Expected sublist size equal to list size");
        }
        if (!sub.get(0).equals("x") || !sub.get(1).equals("y")) {
            throw new RuntimeException("Sublist elements do not match original list");
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
            throw new RuntimeException("Expected IndexOutOfBoundsException for negative fromIndex");
        }

        caught = false;
        try {
            list.subList(1, 3);
        } catch (IndexOutOfBoundsException e) {
            caught = true;
        }
        if (!caught) {
            throw new RuntimeException("Expected IndexOutOfBoundsException for toIndex > size");
        }

        caught = false;
        try {
            list.subList(2, 1);
        } catch (IllegalArgumentException e) {
            caught = true;
        }
        if (!caught) {
            throw new RuntimeException("Expected IllegalArgumentException for fromIndex > toIndex");
        }
    }
}
