/*
 * @test
 * @summary Test LinkedList.removeFirst() behavior including empty list exception
 * @run main LinkedListWitupTest_P0f7e84536775
 */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest_P0f7e84536775 {
    public static void main(String[] args) {
        testRemoveFirstOnNonEmptyList();
        testRemoveFirstOnEmptyListThrows();
    }

    private static void testRemoveFirstOnNonEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        list.add("first");
        list.add("second");

        String removed = list.removeFirst();
        if (!"first".equals(removed)) {
            throw new AssertionError("Expected 'first' but got: " + removed);
        }

        // After removal, first element should be "second"
        String newFirst = list.getFirst();
        if (!"second".equals(newFirst)) {
            throw new AssertionError("Expected 'second' as new first element but got: " + newFirst);
        }
    }

    private static void testRemoveFirstOnEmptyListThrows() {
        LinkedList<Object> emptyList = new LinkedList<>();
        try {
            emptyList.removeFirst();
            throw new AssertionError("Expected NoSuchElementException when removing from empty list");
        } catch (NoSuchElementException expected) {
            // Expected exception
        }
    }
}
