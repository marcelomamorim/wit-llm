/*
 * @test
 * @summary Test LinkedList.remove() method for normal and exceptional behavior
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        testRemoveOnNonEmptyList();
        testRemoveOnEmptyListThrows();
        System.out.println("All tests passed.");
    }

    private static void testRemoveOnNonEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        list.add("first");
        list.add("second");
        String removed = list.remove();
        if (!"first".equals(removed)) {
            throw new AssertionError("Expected 'first' but got: " + removed);
        }
        if (list.size() != 1 || !"second".equals(list.getFirst())) {
            throw new AssertionError("List state incorrect after remove");
        }
    }

    private static void testRemoveOnEmptyListThrows() {
        LinkedList<Object> emptyList = new LinkedList<>();
        try {
            emptyList.remove();
            throw new AssertionError("Expected NoSuchElementException when removing from empty list");
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }
}
