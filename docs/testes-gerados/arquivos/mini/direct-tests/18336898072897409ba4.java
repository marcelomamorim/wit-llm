/*
 * @test
 * @summary Test java.util.ArrayList.lastIndexOf(Object) method behavior
 * @run main java.util.ArrayListWitupTest
 */

import java.util.ArrayList;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testLastIndexOfWithNull();
        testLastIndexOfWithExistingElement();
        testLastIndexOfWithNonExistingElement();
        testLastIndexOfWithMultipleOccurrences();
    }

    private static void testLastIndexOfWithNull() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add(null);
        list.add("b");
        int index = list.lastIndexOf(null);
        if (index != 1) {
            throw new RuntimeException("Expected lastIndexOf(null) to be 1 but was " + index);
        }
    }

    private static void testLastIndexOfWithExistingElement() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        int index = list.lastIndexOf("b");
        if (index != 1) {
            throw new RuntimeException("Expected lastIndexOf(\"b\") to be 1 but was " + index);
        }
    }

    private static void testLastIndexOfWithNonExistingElement() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        int index = list.lastIndexOf("z");
        if (index != -1) {
            throw new RuntimeException("Expected lastIndexOf(\"z\") to be -1 but was " + index);
        }
    }

    private static void testLastIndexOfWithMultipleOccurrences() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");
        list.add("x");
        list.add("z");
        int index = list.lastIndexOf("x");
        if (index != 2) {
            throw new RuntimeException("Expected lastIndexOf(\"x\") to be 2 but was " + index);
        }
    }
}
