/* @test    @summary Test for LinkedList.removeFirst() to verify behavior when list is empty.    @run main LinkedListWitupTest_P7bbfa5541a43 */

import java.util.LinkedList;

public class LinkedListWitupTest_P7bbfa5541a43 {

    public static void main(String[] args) {
        testRemoveFirstEmptyList();
        testRemoveFirstNonEmptyList();
    }

    static void testRemoveFirstEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        try {
            list.removeFirst();
            System.err.println("Expected NoSuchElementException not thrown");
            System.exit(1);
        } catch (NoSuchElementException e) {
            // Expected exception, test passes
        }
    }

    static void testRemoveFirstNonEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        list.add("element");
        String removed = list.removeFirst();
        if (!"element".equals(removed)) {
            System.err.println("removeFirst() returned unexpected element");
            System.exit(1);
        }
        if (!list.isEmpty()) {
            System.err.println("List not empty after removeFirst()");
            System.exit(1);
        }
    }
}
