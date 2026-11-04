/* @test    @summary Test LinkedList remove() method for correct behavior    @run main LinkedListWitupTest */

import java.util.LinkedList;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testRemoveOnEmptyList();
        testRemoveOnSingleElementList();
        testRemoveOnMultipleElements();
    }

    static void testRemoveOnEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        String result = list.remove();
        if (result != null) {
            throw new AssertionError("Expected null when removing from empty list, but got: " + result);
        }
    }

    static void testRemoveOnSingleElementList() {
        LinkedList<String> list = new LinkedList<>();
        list.add("element");
        String removed = list.remove();
        if (!"element".equals(removed)) {
            throw new AssertionError("Expected 'element', but got: " + removed);
        }
        if (!list.isEmpty()) {
            throw new AssertionError("List should be empty after removal.");
        }
    }

    static void testRemoveOnMultipleElements() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Integer first = list.remove();
        if (!first.equals(1)) {
            throw new AssertionError("Expected first element to be 1, but got: " + first);
        }
        if (list.size() != 2 || list.getFirst() != 2 || list.getLast() != 3) {
            throw new AssertionError("List state incorrect after removal.");
        }
    }
}
