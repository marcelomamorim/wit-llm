/*
 * @test
 * @summary Test for ArrayList.lastIndexOf(Object o)
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testLastIndexOfWithNull();
        testLastIndexOfWithElement();
        testLastIndexOfEmptyList();
        testLastIndexOfWithMultipleElements();
    }

    private static void testLastIndexOfWithNull() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add(null);
        list.add("b");
        int index = list.lastIndexOf(null);
        if (index != 1) {
            throw new AssertionError("Expected index 1 for null element, but got " + index);
        }
    }

    private static void testLastIndexOfWithElement() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");
        list.add("x");
        int index = list.lastIndexOf("x");
        if (index != 2) {
            throw new AssertionError("Expected index 2 for element 'x', but got " + index);
        }
    }

    private static void testLastIndexOfEmptyList() {
        ArrayList<String> list = new ArrayList<>();
        int index = list.lastIndexOf("any");
        if (index != -1) {
            throw new AssertionError("Expected -1 for empty list, but got " + index);
        }
    }

    private static void testLastIndexOfWithMultipleElements() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(2);
        list.add(1);
        int index = list.lastIndexOf(2);
        if (index != 3) {
            throw new AssertionError("Expected index 3 for element 2, but got " + index);
        }
    }
}
