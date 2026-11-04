/*
 * @test
 * @summary Test LinkedList.element() method behavior
 * @run main LinkedListWitupTest
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
        list.add("second");
        String first = list.element();
        if (!"first".equals(first)) {
            throw new AssertionError("Expected 'first' but got: " + first);
        }
    }

    private static void testElementOnEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        try {
            list.element();
            throw new AssertionError("Expected NoSuchElementException for empty list");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }
}
