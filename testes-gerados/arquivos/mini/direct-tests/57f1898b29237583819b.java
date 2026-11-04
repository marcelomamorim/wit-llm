/*
 * @test
 * @summary Test java.util.LinkedList.getFirst() method behavior
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        testGetFirstOnEmptyList();
        testGetFirstOnNonEmptyList();
    }

    private static void testGetFirstOnEmptyList() {
        LinkedList<Object> list = new LinkedList<>();
        try {
            list.getFirst();
            throw new AssertionError("Expected NoSuchElementException for empty list");
        } catch (NoSuchElementException expected) {
            // Expected exception
        }
    }

    private static void testGetFirstOnNonEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        list.add("first");
        list.add("second");
        String first = list.getFirst();
        if (!"first".equals(first)) {
            throw new AssertionError("Expected 'first' but got: " + first);
        }
    }
}
