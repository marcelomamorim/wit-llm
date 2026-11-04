/*
 * @test
 * @summary Black-box checks of ArrayList.add(int, E) observed through the public subList view, including range and fail-fast behavior
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testSubListAddInsertsIntoParent();
        testSubListAddAtStartAndAtEndOfView();
        testSubListAddOutOfRangeThrows();
        testSubListAddDetectsComodification();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static ArrayList<String> abc() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        return list;
    }

    static void testSubListAddInsertsIntoParent() {
        ArrayList<String> list = abc();
        java.util.List<String> sub = list.subList(1, 3);
        sub.add(1, "X");
        check(sub.size() == 3, "sublist size after add");
        check("X".equals(sub.get(1)), "sublist element at inserted index");
        check(list.size() == 4, "parent size after add");
        check("[a, b, X, c]".equals(list.toString()), "parent contents: " + list);
    }

    static void testSubListAddAtStartAndAtEndOfView() {
        ArrayList<String> list = abc();
        java.util.List<String> empty = list.subList(0, 0);
        empty.add(0, "x");
        check("[x, a, b, c]".equals(list.toString()), "add at start of empty view: " + list);

        java.util.List<String> view = list.subList(0, 1);
        view.add(1, "E");
        check("[x, E, a, b, c]".equals(list.toString()), "add at end of view: " + list);
        check(view.size() == 2, "view size after add at end");
    }

    static void testSubListAddOutOfRangeThrows() {
        ArrayList<String> list = abc();
        java.util.List<String> sub = list.subList(0, 1);

        boolean thrownHigh = false;
        try {
            sub.add(2, "z");
        } catch (IndexOutOfBoundsException e) {
            thrownHigh = true;
        }
        check(thrownHigh, "index above view size must throw IndexOutOfBoundsException");

        boolean thrownNegative = false;
        try {
            sub.add(-1, "z");
        } catch (IndexOutOfBoundsException e) {
            thrownNegative = true;
        }
        check(thrownNegative, "negative index must throw IndexOutOfBoundsException");

        check(list.size() == 3, "failed add must not change parent size");
        check("[a, b, c]".equals(list.toString()), "failed add must not change parent: " + list);
    }

    static void testSubListAddDetectsComodification() {
        ArrayList<String> list = abc();
        java.util.List<String> sub = list.subList(0, 1);
        list.add("q");

        boolean thrown = false;
        try {
            sub.add(0, "w");
        } catch (java.util.ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "sublist add after structural change of parent must throw ConcurrentModificationException");
    }
}
