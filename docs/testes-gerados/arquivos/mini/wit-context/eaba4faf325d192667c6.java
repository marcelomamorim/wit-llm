/*
 * @test
 * @summary Test LinkedList.remove(int) for valid and invalid indices
 * @run main java.util.LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        testRemoveValidIndex();
        testRemoveInvalidIndexNegative();
        testRemoveInvalidIndexTooLarge();
    }

    private static void testRemoveValidIndex() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        // Remove element at index 1 ("b")
        String removed = list.remove(1);
        if (!"b".equals(removed)) {
            throw new AssertionError("Expected removed element 'b', got: " + removed);
        }

        // After removal, list should contain "a" and "c" in order
        if (list.size() != 2 || !"a".equals(list.get(0)) || !"c".equals(list.get(1))) {
            throw new AssertionError("List contents incorrect after removal: " + list);
        }
    }

    private static void testRemoveInvalidIndexNegative() {
        LinkedList<String> list = new LinkedList<>();
        list.add("x");

        try {
            list.remove(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException expected) {
            // Expected exception
        }
    }

    private static void testRemoveInvalidIndexTooLarge() {
        LinkedList<String> list = new LinkedList<>();
        list.add("x");

        try {
            list.remove(1); // index == size
            throw new AssertionError("Expected IndexOutOfBoundsException for index equal to size");
        } catch (IndexOutOfBoundsException expected) {
            // Expected exception
        }

        try {
            list.remove(100); // index > size
            throw new AssertionError("Expected IndexOutOfBoundsException for index greater than size");
        } catch (IndexOutOfBoundsException expected) {
            // Expected exception
        }
    }
}
