/*
 * @test
 * @summary Test java.util.ArrayList.lastIndexOf(Object) for expected behavior and fail-fast on concurrent modification
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testLastIndexOfBasic();
        testLastIndexOfConcurrentModification();
    }

    private static void testLastIndexOfBasic() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("a");
        // lastIndexOf should return the last occurrence index
        int idx = list.lastIndexOf("a");
        if (idx != 2) {
            throw new AssertionError("Expected lastIndexOf to return 2 but got " + idx);
        }
        // lastIndexOf for element not present returns -1
        idx = list.lastIndexOf("c");
        if (idx != -1) {
            throw new AssertionError("Expected lastIndexOf to return -1 for missing element but got " + idx);
        }
        // lastIndexOf for null element when null not present returns -1
        idx = list.lastIndexOf(null);
        if (idx != -1) {
            throw new AssertionError("Expected lastIndexOf to return -1 for null element not present but got " + idx);
        }
        // add null and test lastIndexOf
        list.add(null);
        idx = list.lastIndexOf(null);
        if (idx != 3) {
            throw new AssertionError("Expected lastIndexOf to return 3 for null element but got " + idx);
        }
    }

    private static void testLastIndexOfConcurrentModification() {
        // The reported expath relates to SubList.lastIndexOf throwing ConcurrentModificationException
        // We test that concurrent modification during lastIndexOf triggers ConcurrentModificationException
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");
        list.add("z");

        // Create a subList to trigger the fail-fast behavior
        java.util.List<String> subList = list.subList(0, 3);

        // Modify the root list structurally after creating subList
        list.add("w");

        // Now calling lastIndexOf on subList should throw ConcurrentModificationException
        boolean cmeThrown = false;
        try {
            subList.lastIndexOf("x");
        } catch (ConcurrentModificationException cme) {
            cmeThrown = true;
        }
        if (!cmeThrown) {
            throw new AssertionError("Expected ConcurrentModificationException not thrown by subList.lastIndexOf after structural modification");
        }
    }
}
