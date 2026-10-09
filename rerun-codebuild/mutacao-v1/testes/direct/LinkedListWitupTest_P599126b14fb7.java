/* @test    @summary Test removeLast() method of LinkedList for normal and empty list scenarios    @run main LinkedListWitupTest_P599126b14fb7 */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest_P599126b14fb7 {

    public static void main(String[] args) {
        testRemoveLastWithElements();
        testRemoveLastEmptyList();
    }

    private static void testRemoveLastWithElements() {
        LinkedList<String> list = new LinkedList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        String removed = list.removeLast();
        if (!"C".equals(removed)) {
            throw new AssertionError("Expected 'C', but got: " + removed);
        }
        if (list.size() != 2) {
            throw new AssertionError("List size should be 2 after removal, but is: " + list.size());
        }
        if (!list.getLast().equals("B")) {
            throw new AssertionError("Last element should be 'B', but is: " + list.getLast());
        }
    }

    private static void testRemoveLastEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        try {
            list.removeLast();
            throw new AssertionError("Expected NoSuchElementException when removing last from empty list");
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }
}
