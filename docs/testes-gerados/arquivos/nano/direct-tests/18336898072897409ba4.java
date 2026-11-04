/* @test    @summary Test lastIndexOf method of ArrayList for various scenarios    @run main ArrayListWitupTest */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testLastIndexOfWithExistingElement();
        testLastIndexOfWithNonExistingElement();
        testLastIndexOfWithNullElement();
        testLastIndexOfInEmptyList();
    }

    private static void testLastIndexOfWithExistingElement() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("a");
        int index = list.lastIndexOf("a");
        if (index != 2) {
            throw new AssertionError("Expected index 2, but got " + index);
        }
    }

    private static void testLastIndexOfWithNonExistingElement() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        int index = list.lastIndexOf("c");
        if (index != -1) {
            throw new AssertionError("Expected index -1, but got " + index);
        }
    }

    private static void testLastIndexOfWithNullElement() {
        ArrayList<String> list = new ArrayList<>();
        list.add(null);
        list.add("a");
        list.add(null);
        int index = list.lastIndexOf(null);
        if (index != 2) {
            throw new AssertionError("Expected index 2, but got " + index);
        }
    }

    private static void testLastIndexOfInEmptyList() {
        ArrayList<String> list = new ArrayList<>();
        int index = list.lastIndexOf("a");
        if (index != -1) {
            throw new AssertionError("Expected index -1, but got " + index);
        }
    }
}
