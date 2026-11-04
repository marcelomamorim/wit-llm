/*
 * @test
 * @summary Test LinkedList remove() method delegates to removeFirst()
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        testRemoveOnNonEmptyList();
        testRemoveOnEmptyList();
    }

    private static void testRemoveOnNonEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        String removed = list.remove();
        if (!"a".equals(removed)) {
            throw new AssertionError("Expected 'a' but got: " + removed);
        }
        if (list.size() != 1 || !"b".equals(list.getFirst())) {
            throw new AssertionError("List state incorrect after remove");
        }
    }

    private static void testRemoveOnEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        try {
            list.remove();
            throw new AssertionError("Expected NoSuchElementException on remove from empty list");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }
}
