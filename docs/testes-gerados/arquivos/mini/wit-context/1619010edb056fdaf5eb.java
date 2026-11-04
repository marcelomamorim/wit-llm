/*
 * @test
 * @summary Test java.util.ArrayList.SubList.indexOf for expected behavior and fail-fast
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testIndexOfNormal();
        testIndexOfFailFast();
    }

    static void testIndexOfNormal() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        // Create a subList covering the whole list
        java.util.List<String> subList = list.subList(0, list.size());

        // indexOf should find "b" at index 1
        int idx = subList.indexOf("b");
        if (idx != 1) {
            throw new Error("Expected indexOf to return 1, got " + idx);
        }

        // indexOf should return -1 for element not present
        idx = subList.indexOf("z");
        if (idx != -1) {
            throw new Error("Expected indexOf to return -1 for missing element, got " + idx);
        }

        // indexOf should find null if present
        subList = list.subList(0, list.size());
        ((ArrayList<String>)list).add(null);
        subList = list.subList(0, list.size());
        idx = subList.indexOf(null);
        if (idx != list.size() - 1) {
            throw new Error("Expected indexOf to find null at last index, got " + idx);
        }
    }

    static void testIndexOfFailFast() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");

        java.util.List<String> subList = list.subList(0, list.size());

        // Modify the root list structurally after creating subList
        list.add("z");

        try {
            // This should throw ConcurrentModificationException due to fail-fast
            subList.indexOf("x");
            throw new Error("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // Expected
        }

        try {
            // Also test with null argument triggers fail-fast
            subList.indexOf(null);
            throw new Error("Expected ConcurrentModificationException not thrown for null argument");
        } catch (ConcurrentModificationException expected) {
            // Expected
        }
    }
}
