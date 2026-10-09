/*
 * @test
 * @summary Test getFirst() method of LinkedList for normal and empty list
 * @run main LinkedListWitupTest_P5d46cbc0329f
 */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest_P5d46cbc0329f {

    public static void main(String[] args) {
        testGetFirstOnNonEmptyList();
        testGetFirstOnEmptyList();
    }

    static void testGetFirstOnNonEmptyList() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(10);
        list.add(20);
        Integer result = list.getFirst();
        if (result == null || result != 10) {
            throw new AssertionError("Expected getFirst() to return 10, but got " + result);
        }
    }

    static void testGetFirstOnEmptyList() {
        LinkedList<Integer> list = new LinkedList<>();
        boolean exceptionThrown = false;
        try {
            list.getFirst();
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NoSuchElementException when calling getFirst() on empty list");
        }
    }
}
