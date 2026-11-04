/*
 * @test
 * @summary Test java.util.LinkedList.getLast() method behavior
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        testGetLastOnEmptyList();
        testGetLastOnNonEmptyList();
    }

    private static void testGetLastOnEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        try {
            list.getLast();
            throw new RuntimeException("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }

    private static void testGetLastOnNonEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        list.add("first");
        list.add("second");
        String last = list.getLast();
        if (!"second".equals(last)) {
            throw new RuntimeException("getLast() returned wrong element: " + last);
        }
    }
}
