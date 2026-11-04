/* @test    @summary Test for ArrayList.indexOf(Object o) method behavior.    @run main ArrayListWitupTest */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testIndexOfWithExistingElement();
        testIndexOfWithNullElement();
        testIndexOfWithNonExistingElement();
    }

    private static void testIndexOfWithExistingElement() {
        ArrayList<String> list = new ArrayList<>();
        list.add("apple");
        list.add("banana");
        list.add("cherry");
        int index = list.indexOf("banana");
        if (index != 1) {
            throw new AssertionError("Expected index 1 for 'banana', but got " + index);
        }
    }

    private static void testIndexOfWithNullElement() {
        ArrayList<String> list = new ArrayList<>();
        list.add(null);
        list.add("orange");
        int index = list.indexOf(null);
        if (index != 0) {
            throw new AssertionError("Expected index 0 for null element, but got " + index);
        }
    }

    private static void testIndexOfWithNonExistingElement() {
        ArrayList<String> list = new ArrayList<>();
        list.add("dog");
        list.add("cat");
        int index = list.indexOf("mouse");
        if (index != -1) {
            throw new AssertionError("Expected -1 for non-existing element, but got " + index);
        }
    }
}
