/*
 * @test
 * @summary Test LinkedList.element() method for normal and exceptional behavior
 * @run main java.util.LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        testElementOnNonEmptyList();
        testElementOnEmptyList();
    }

    private static void testElementOnNonEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        list.add("first");
        String element = list.element();
        if (!"first".equals(element)) {
            throw new AssertionError("Expected 'first' but got: " + element);
        }
    }

    private static void testElementOnEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        try {
            list.element();
            throw new AssertionError("Expected NoSuchElementException when calling element() on empty list");
        } catch (NoSuchElementException expected) {
            // Expected exception
        }
    }
}
