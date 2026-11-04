/*
 * @test
 * @summary Test for ArrayList.indexOf(Object o) method, including exception path.
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testIndexOfWithNull();
        testIndexOfWithNonNull();
        testIndexOfConcurrentModification();
    }

    private static void testIndexOfWithNull() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add(null);
        list.add("b");
        int index = list.indexOf(null);
        if (index != 1) {
            throw new AssertionError("Expected index 1 for null element, got " + index);
        }
    }

    private static void testIndexOfWithNonNull() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");
        list.add("z");
        int index = list.indexOf("y");
        if (index != 1) {
            throw new AssertionError("Expected index 1 for element 'y', got " + index);
        }
    }

    private static void testIndexOfConcurrentModification() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        ArrayList<String> sublist = (ArrayList<String>) list.clone();
        try {
            // Simulate concurrent modification by modifying list after cloning
            list.add("d");
            // Call indexOf on the clone, which should throw ConcurrentModificationException
            sublist.indexOf("a");
            throw new AssertionError("Expected ConcurrentModificationException was not thrown");
        } catch (java.util.ConcurrentModificationException e) {
            // Expected exception
        }
    }
}
