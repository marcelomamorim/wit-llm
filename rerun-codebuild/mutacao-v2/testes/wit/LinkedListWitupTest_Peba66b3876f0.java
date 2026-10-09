/*
 * @test
 * @summary Test LinkedList.removeLast() for normal and exceptional behavior
 * @run main LinkedListWitupTest_Peba66b3876f0
 */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest_Peba66b3876f0 {
    public static void main(String[] args) {
        testRemoveLastOnEmptyList();
        testRemoveLastOnNonEmptyList();
    }

    private static void testRemoveLastOnEmptyList() {
        LinkedList<Object> list = new LinkedList<>();
        try {
            list.removeLast();
            throw new AssertionError("Expected NoSuchElementException when removing last from empty list");
        } catch (NoSuchElementException expected) {
            // Expected exception
        }
    }

    private static void testRemoveLastOnNonEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        list.add("first");
        list.add("second");
        String removed = list.removeLast();
        if (!"second".equals(removed)) {
            throw new AssertionError("removeLast() did not return the last element as expected");
        }
        if (list.size() != 1 || !"first".equals(list.getLast())) {
            throw new AssertionError("List state incorrect after removeLast()");
        }
    }
}
