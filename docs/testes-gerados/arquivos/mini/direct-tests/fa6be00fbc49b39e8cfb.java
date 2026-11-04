/*
 * @test
 * @summary Test java.util.ArrayList.ListItr.previous() method behavior
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testPreviousNormal();
        testPreviousNoSuchElementException();
        testPreviousConcurrentModificationException();
    }

    private static void testPreviousNormal() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        ListIterator<String> it = list.listIterator(list.size());
        // cursor is at end, previous should return last element "c"
        String prev = it.previous();
        if (!"c".equals(prev)) {
            throw new AssertionError("Expected previous() to return 'c', got: " + prev);
        }
        // previous again should return "b"
        prev = it.previous();
        if (!"b".equals(prev)) {
            throw new AssertionError("Expected previous() to return 'b', got: " + prev);
        }
    }

    private static void testPreviousNoSuchElementException() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");

        ListIterator<String> it = list.listIterator();
        // cursor is at 0, previous() should throw NoSuchElementException
        boolean thrown = false;
        try {
            it.previous();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NoSuchElementException from previous() at start");
        }
    }

    private static void testPreviousConcurrentModificationException() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");

        ListIterator<String> it = list.listIterator(list.size());
        // Modify list structurally to cause concurrent modification
        list.add("z");

        boolean thrown = false;
        try {
            it.previous();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected ConcurrentModificationException from previous() after modification");
        }
    }
}
