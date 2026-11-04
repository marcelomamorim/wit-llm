/* @test    @summary Test getLast() method of LinkedList for null list case    @run main LinkedListWitupTest */

import java.util.LinkedList;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testGetLastEmpty();
        testGetLastNonEmpty();
    }

    private static void testGetLastEmpty() {
        LinkedList<Object> list = new LinkedList<>();
        try {
            list.getLast();
            System.err.println("Failed: Expected NoSuchElementException for empty list");
            System.exit(1);
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }

    private static void testGetLastNonEmpty() {
        LinkedList<String> list = new LinkedList<>();
        list.add("element");
        Object result = list.getLast();
        if (!"element".equals(result)) {
            System.err.println("Failed: getLast() did not return the last element");
            System.exit(1);
        }
    }
}
