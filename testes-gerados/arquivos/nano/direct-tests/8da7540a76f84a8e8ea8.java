/* @test    @summary Test for LinkedList.previous() method to ensure correct element retrieval and exception handling.    @run main LinkedListWitupTest */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testPreviousWithElements();
        testPreviousWithoutElements();
    }

    private static void testPreviousWithElements() {
        LinkedList<String> list = new LinkedList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.listIterator(3); // move iterator to end
        try {
            String element = list.previous();
            if (!"C".equals(element)) {
                throw new AssertionError("Expected 'C', got: " + element);
            }
        } catch (NoSuchElementException e) {
            throw new AssertionError("Unexpected NoSuchElementException when elements are present", e);
        }
    }

    private static void testPreviousWithoutElements() {
        LinkedList<String> list = new LinkedList<>();
        list.listIterator(); // iterator at start
        try {
            list.previous();
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException e) {
            // expected exception
        }
    }
}
