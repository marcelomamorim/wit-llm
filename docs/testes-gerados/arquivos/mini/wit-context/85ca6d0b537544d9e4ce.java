/*
 * @test
 * @summary Test LinkedList.getLast() behavior including NoSuchElementException when empty
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        testGetLastOnNonEmptyList();
        testGetLastOnEmptyListThrows();
        System.out.println("PASS");
    }

    private static void testGetLastOnNonEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        list.add("first");
        list.add("second");
        String last = list.getLast();
        if (!"second".equals(last)) {
            throw new AssertionError("Expected last element to be 'second' but was: " + last);
        }
    }

    private static void testGetLastOnEmptyListThrows() {
        LinkedList<Object> list = new LinkedList<>();
        try {
            list.getLast();
            throw new AssertionError("Expected NoSuchElementException when calling getLast() on empty list");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }
}
