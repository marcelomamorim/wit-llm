/* @test    @summary Test for LinkedList.previous() method to verify exception paths and behavior    @run main LinkedListWitupTest */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testPreviousThrowsNoSuchElementException();
        testPreviousNormalBehavior();
    }

    private static void testPreviousThrowsNoSuchElementException() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        // Create an iterator at the beginning of the list
        LinkedList<Integer>.ListItr itr = list.new ListItr(0);
        // Move iterator to the start (no previous element)
        while (itr.hasNext()) {
            itr.next();
        }
        // Now, previous() should throw NoSuchElementException
        try {
            itr.previous();
            // Fail if no exception
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }

    private static void testPreviousNormalBehavior() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(10);
        list.add(20);
        list.add(30);
        // Create an iterator at position 2 (after second element)
        LinkedList<Integer>.ListItr itr = list.new ListItr(2);
        // Move iterator back to previous element
        Integer prevItem = itr.previous();
        if (prevItem == null || prevItem != 20) {
            throw new AssertionError("Unexpected previous item: " + prevItem);
        }
        // Verify internal state after previous()
        if (itr.nextIndex() != 1) {
            throw new AssertionError("Unexpected nextIndex after previous()");
        }
    }
}
