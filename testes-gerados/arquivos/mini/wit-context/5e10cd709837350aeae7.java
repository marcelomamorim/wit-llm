/*
 * @test
 * @summary Test java.util.ArrayList add(int index, E element) method for exception and normal behavior
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testAddAtValidIndex();
        testAddAtIndexOutOfBounds();
        testAddConcurrentModificationException();
    }

    private static void testAddAtValidIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add(1, "c");
        if (!"c".equals(list.get(1))) {
            throw new AssertionError("Element not added at correct index");
        }
        if (list.size() != 3) {
            throw new AssertionError("Size not updated correctly after add");
        }
    }

    private static void testAddAtIndexOutOfBounds() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        try {
            list.add(-1, "x");
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException expected) {
        }
        try {
            list.add(2, "x"); // size is 1, valid indices are 0 or 1
            throw new AssertionError("Expected IndexOutOfBoundsException for index > size");
        } catch (IndexOutOfBoundsException expected) {
        }
    }

    private static void testAddConcurrentModificationException() {
        // ConcurrentModificationException is thrown if modCount changes unexpectedly during add
        // We simulate this by subclassing ArrayList and modifying modCount externally
        // Since modCount is private, we cannot access it directly, so we test via subList

        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        List<String> subList = list.subList(0, 2);

        // Modify the original list structurally to cause concurrent modification
        list.add("c");

        try {
            subList.add(1, "x");
            throw new AssertionError("Expected ConcurrentModificationException due to concurrent modification");
        } catch (ConcurrentModificationException expected) {
        }
    }
}
