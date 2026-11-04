/*
 * @test
 * @summary Test java.util.ArrayList add(E e) method behavior and fail-fast on concurrent modification
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.ListIterator;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testAddInListIterator();
        testConcurrentModificationExceptionOnAdd();
    }

    private static void testAddInListIterator() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        ListIterator<String> it = list.listIterator();
        // Move cursor forward once
        it.next(); // returns "a"
        // Add element at cursor position
        it.add("x");

        // After add, cursor should have advanced by 1
        // Next call to next() should return "b"
        String next = it.next();
        if (!"b".equals(next)) {
            throw new AssertionError("Expected next element to be 'b' but got: " + next);
        }

        // The list should now be ["a", "x", "b", "c"]
        if (!list.get(1).equals("x")) {
            throw new AssertionError("Element at index 1 should be 'x' but was: " + list.get(1));
        }
    }

    private static void testConcurrentModificationExceptionOnAdd() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");

        ListIterator<String> it = list.listIterator();
        it.next(); // move cursor to 1

        // Modify list structurally outside iterator
        list.add("c");

        boolean cmeThrown = false;
        try {
            it.add("x");
        } catch (ConcurrentModificationException ex) {
            cmeThrown = true;
        }

        if (!cmeThrown) {
            throw new AssertionError("Expected ConcurrentModificationException was not thrown");
        }
    }
}
