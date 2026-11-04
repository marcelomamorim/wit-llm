/*
 * @test
 * @summary Test LinkedList.removeFirst() behavior including exception on empty list
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        testRemoveFirstOnEmptyList();
        testRemoveFirstOnNonEmptyList();
    }

    private static void testRemoveFirstOnEmptyList() {
        LinkedList<Object> list = new LinkedList<>();
        try {
            list.removeFirst();
            throw new AssertionError("Expected NoSuchElementException when removing first from empty list");
        } catch (NoSuchElementException expected) {
            // Expected exception
        }
    }

    private static void testRemoveFirstOnNonEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        list.add("first");
        list.add("second");
        String removed = list.removeFirst();
        if (!"first".equals(removed)) {
            throw new AssertionError("removeFirst() did not return the first element");
        }
        if (list.size() != 1 || !"second".equals(list.getFirst())) {
            throw new AssertionError("List state incorrect after removeFirst()");
        }
    }
}
