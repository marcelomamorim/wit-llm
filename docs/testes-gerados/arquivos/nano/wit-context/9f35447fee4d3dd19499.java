/* @test    @summary Test for LinkedList.removeFirst() method to verify exception when list is empty    @run main LinkedListWitupTest */

import java.util.LinkedList;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testRemoveFirstEmptyList();
        testRemoveFirstNonEmptyList();
    }

    private static void testRemoveFirstEmptyList() {
        LinkedList<Object> list = new LinkedList<>();
        try {
            list.removeFirst();
            System.err.println("Failed: Expected NoSuchElementException was not thrown.");
            System.exit(1);
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }

    private static void testRemoveFirstNonEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        list.add("element");
        String removed = list.removeFirst();
        if (!"element".equals(removed)) {
            System.err.println("Failed: removeFirst() did not return the expected element.");
            System.exit(1);
        }
        if (!list.isEmpty()) {
            System.err.println("Failed: List should be empty after removal.");
            System.exit(1);
        }
    }
}
