/*
 * @test
 * @summary Test java.util.ArrayList.indexOf(Object) method behavior
 * @run main java.util.ArrayListWitupTest
 */

import java.util.ArrayList;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testIndexOfWithElements();
        testIndexOfWithNullElement();
        testIndexOfWithAbsentElement();
        testIndexOfWithEmptyList();
    }

    private static void testIndexOfWithElements() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        int indexA = list.indexOf("a");
        int indexB = list.indexOf("b");
        int indexC = list.indexOf("c");

        if (indexA != 0) {
            throw new RuntimeException("indexOf failed for element 'a': expected 0, got " + indexA);
        }
        if (indexB != 1) {
            throw new RuntimeException("indexOf failed for element 'b': expected 1, got " + indexB);
        }
        if (indexC != 2) {
            throw new RuntimeException("indexOf failed for element 'c': expected 2, got " + indexC);
        }
    }

    private static void testIndexOfWithNullElement() {
        ArrayList<String> list = new ArrayList<>();
        list.add(null);
        list.add("x");

        int indexNull = list.indexOf(null);
        int indexX = list.indexOf("x");

        if (indexNull != 0) {
            throw new RuntimeException("indexOf failed for null element: expected 0, got " + indexNull);
        }
        if (indexX != 1) {
            throw new RuntimeException("indexOf failed for element 'x': expected 1, got " + indexX);
        }
    }

    private static void testIndexOfWithAbsentElement() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");

        int index = list.indexOf("z");
        if (index != -1) {
            throw new RuntimeException("indexOf failed for absent element: expected -1, got " + index);
        }
    }

    private static void testIndexOfWithEmptyList() {
        ArrayList<String> list = new ArrayList<>();
        int index = list.indexOf("any");
        if (index != -1) {
            throw new RuntimeException("indexOf failed for empty list: expected -1, got " + index);
        }
    }
}
