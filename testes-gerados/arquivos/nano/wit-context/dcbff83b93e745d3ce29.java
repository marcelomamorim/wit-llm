/*
 * @test
 * @summary Test for LinkedList.set(E e) method to verify exception paths and behavior.
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testSetWithNullLastReturned();
        testSetWithModifiedList();
    }

    private static void testSetWithNullLastReturned() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        // Create a ListItr and move it to the second element
        LinkedList.ListItr itr = list.new ListItr(1);
        // Manually set lastReturned to null to simulate the exception condition
        // Note: lastReturned is private; simulate by calling set without prior next() or previous()
        // Since lastReturned is private, we cannot set it directly; instead, we invoke set without prior traversal
        try {
            // Directly call set without prior traversal to trigger exception
            itr.set("new_value");
            // If no exception, fail the test
            System.out.println("FAIL: Expected IllegalStateException not thrown.");
        } catch (IllegalStateException e) {
            System.out.println("PASS: Caught expected IllegalStateException.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testSetWithModifiedList() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        // Create a ListItr and move it to the second element
        LinkedList.ListItr itr = list.new ListItr(1);
        // Move iterator forward to set lastReturned
        try {
            itr.next(); // move to second element
            // Modify list structurally
            list.add("c");
            // Now, check for concurrent modification
            itr.set("new_value");
            System.out.println("FAIL: Expected ConcurrentModificationException not thrown.");
        } catch (java.util.ConcurrentModificationException e) {
            System.out.println("PASS: Caught expected ConcurrentModificationException.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
