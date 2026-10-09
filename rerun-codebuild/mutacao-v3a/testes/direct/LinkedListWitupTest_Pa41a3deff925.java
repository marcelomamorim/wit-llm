/*
 * @test
 * @summary Tests LinkedList.removeLast() for correct exception throwing and behavior
 * @run main LinkedListWitupTest_Pa41a3deff925
 */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest_Pa41a3deff925 {

    public static void main(String[] args) {
        testRemoveLastEmptyList();
        testRemoveLastSingleElement();
        testRemoveLastMultipleElements();
    }

    static void testRemoveLastEmptyList() {
        LinkedList<Integer> list = new LinkedList<>();
        try {
            list.removeLast();
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException e) {
            // expected
        }
    }

    static void testRemoveLastSingleElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add("element");
        String removed = list.removeLast();
        if (!"element".equals(removed)) {
            throw new AssertionError("removeLast did not return the correct element");
        }
        if (!list.isEmpty()) {
            throw new AssertionError("List should be empty after removing the only element");
        }
    }

    static void testRemoveLastMultipleElements() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        int last = list.removeLast();
        if (last != 3) {
            throw new AssertionError("removeLast did not return the last element");
        }
        if (list.size() != 2 || list.getLast() != 2) {
            throw new AssertionError("List state incorrect after removeLast");
        }
    }
}
