/*
 * @test
 * @summary Test for LinkedList.getLast() method to ensure correct element retrieval and exception throwing.
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testGetLastWithElements();
        testGetLastOnEmptyList();
    }

    private static void testGetLastWithElements() {
        LinkedList<String> list = new LinkedList<>();
        list.add("first");
        list.add("second");
        String lastElement = list.getLast();
        if (!"second".equals(lastElement)) {
            throw new AssertionError("Expected 'second', but got: " + lastElement);
        }
    }

    private static void testGetLastOnEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        boolean exceptionThrown = false;
        try {
            list.getLast();
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NoSuchElementException to be thrown when calling getLast() on empty list.");
        }
    }
}
