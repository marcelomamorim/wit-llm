/*
 * @test
 * @summary Test LinkedList.removeLast() behavior including empty list exception
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        testRemoveLastOnNonEmptyList();
        testRemoveLastOnEmptyListThrows();
    }

    private static void testRemoveLastOnNonEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        list.add("first");
        list.add("second");

        String removed = list.removeLast();
        if (!"second".equals(removed)) {
            throw new AssertionError("Expected 'second' but got: " + removed);
        }

        removed = list.removeLast();
        if (!"first".equals(removed)) {
            throw new AssertionError("Expected 'first' but got: " + removed);
        }

        if (!list.isEmpty()) {
            throw new AssertionError("List should be empty after removing all elements");
        }
    }

    private static void testRemoveLastOnEmptyListThrows() {
        LinkedList<Object> list = new LinkedList<>();
        try {
            list.removeLast();
            throw new AssertionError("Expected NoSuchElementException when removing last from empty list");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }
}
