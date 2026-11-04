/*
 * @test
 * @summary Test java.util.ArrayList add(E e) method behavior in SubList iterator context
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.ListIterator;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testAddInSubListIterator();
        testAddNullElement();
    }

    // Test add(E e) on SubList's ListIterator updates cursor and expectedModCount correctly
    private static void testAddInSubListIterator() {
        ArrayList<String> root = new ArrayList<>();
        root.add("a");
        root.add("b");
        root.add("c");

        List<String> subList = root.subList(1, 3); // contains "b", "c"
        ListIterator<String> it = subList.listIterator();

        // Add element "x" at cursor 0
        it.add("x");

        // After add, cursor should be 1
        if (it.nextIndex() != 1) {
            throw new AssertionError("Cursor not updated correctly after add");
        }

        // The root list should now be ["a", "x", "b", "c"]
        if (!root.get(1).equals("x")) {
            throw new AssertionError("Element not added correctly to root list");
        }

        // The subList should now be ["x", "b", "c"]
        if (!subList.get(0).equals("x")) {
            throw new AssertionError("Element not added correctly to subList");
        }

        // Add another element "y" at cursor 1
        it.add("y");

        if (it.nextIndex() != 2) {
            throw new AssertionError("Cursor not updated correctly after second add");
        }

        // root list should be ["a", "x", "y", "b", "c"]
        if (!root.get(2).equals("y")) {
            throw new AssertionError("Second element not added correctly to root list");
        }

        // subList should be ["x", "y", "b", "c"]
        if (!subList.get(1).equals("y")) {
            throw new AssertionError("Second element not added correctly to subList");
        }
    }

    // Test that add(E e) accepts null elements without throwing
    private static void testAddNullElement() {
        ArrayList<String> root = new ArrayList<>();
        root.add("a");
        root.add("b");

        List<String> subList = root.subList(0, 2);
        ListIterator<String> it = subList.listIterator();

        // Add null element
        it.add(null);

        // The root list should contain null at index 0
        if (root.get(0) != null) {
            throw new AssertionError("Null element not added correctly");
        }

        // The subList should contain null at index 0
        if (subList.get(0) != null) {
            throw new AssertionError("Null element not added correctly to subList");
        }
    }
}
