/* @test    @summary Test for LinkedList.next() method to observe behavior and exceptions    @run main LinkedListWitupTest */

import java.util.LinkedList;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testNextMethodNormal();
        testNextThrowsNoSuchElementException();
        testNextThrowsConcurrentModificationException();
    }

    private static void testNextMethodNormal() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.iterator();
        list.listIterator();
        try {
            // Obtain list iterator at position 0
            java.util.ListIterator<String> it = list.listIterator();
            // Move to first element
            it.next();
            String result = it.next();
            if (!"b".equals(result)) {
                throw new AssertionError("Expected 'b', got: " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in normal next() call", e);
        }
    }

    private static void testNextThrowsNoSuchElementException() {
        LinkedList<String> list = new LinkedList<>();
        list.add("x");
        java.util.ListIterator<String> it = list.listIterator();
        try {
            // Move iterator to end
            it.next();
            // Next call should throw NoSuchElementException
            it.next();
            throw new AssertionError("Expected NoSuchElementException was not thrown");
        } catch (java.util.NoSuchElementException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception", e);
        }
    }

    private static void testNextThrowsConcurrentModificationException() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        java.util.ListIterator<String> it = list.listIterator();
        list.add("c"); // Structural modification
        try {
            it.next();
            throw new AssertionError("Expected ConcurrentModificationException was not thrown");
        } catch (java.util.ConcurrentModificationException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception", e);
        }
    }
}
