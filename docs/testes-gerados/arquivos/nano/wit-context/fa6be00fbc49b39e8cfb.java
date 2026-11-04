/*
 * @test
 * @summary Tests for ArrayList.previous() method to verify exception paths and behavior.
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testPreviousThrowsNoSuchElementException();
        testPreviousThrowsConcurrentModificationException();
        testPreviousNormalBehavior();
    }

    private static void testPreviousThrowsNoSuchElementException() {
        ArrayList<Object> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        // Create a ListIterator at position 0
        java.util.ListIterator<Object> it = list.listIterator(0);
        // Move cursor to start
        it.next();
        it.next();
        it.next();
        // Cursor now at end
        // Move cursor back to start
        it.previous();
        it.previous();
        it.previous();
        // Now at position 0
        try {
            it.previous();
            // Should throw NoSuchElementException
            System.out.println("Failed: Expected NoSuchElementException not thrown");
        } catch (java.util.NoSuchElementException e) {
            System.out.println("Passed: NoSuchElementException thrown as expected");
        } catch (Exception e) {
            System.out.println("Failed: Unexpected exception: " + e);
        }
    }

    private static void testPreviousThrowsConcurrentModificationException() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");
        list.add("z");
        java.util.ListIterator<String> it = list.listIterator();
        // Modify list after creating iterator to trigger exception
        list.add("new");
        try {
            it.previous();
            System.out.println("Failed: Expected ConcurrentModificationException not thrown");
        } catch (java.util.ConcurrentModificationException e) {
            System.out.println("Passed: ConcurrentModificationException thrown as expected");
        } catch (Exception e) {
            System.out.println("Failed: Unexpected exception: " + e);
        }
    }

    private static void testPreviousNormalBehavior() {
        ArrayList<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        list.add("three");
        java.util.ListIterator<String> it = list.listIterator(3); // position after last
        try {
            String val = it.previous();
            if ("three".equals(val)) {
                System.out.println("Passed: previous() returned last element as expected");
            } else {
                System.out.println("Failed: previous() did not return expected element");
            }
        } catch (Exception e) {
            System.out.println("Failed: Unexpected exception: " + e);
        }
    }
}
