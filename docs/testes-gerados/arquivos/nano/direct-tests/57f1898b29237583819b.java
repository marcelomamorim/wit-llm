/* @test    @summary Test getFirst() method of LinkedList for normal and empty list scenarios    @run main LinkedListWitupTest */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testGetFirstWithElements();
        testGetFirstEmptyList();
    }

    static void testGetFirstWithElements() {
        LinkedList<String> list = new LinkedList<>();
        list.add("element1");
        list.add("element2");
        String first = list.getFirst();
        if (!"element1".equals(first)) {
            throw new AssertionError("Expected 'element1', but got: " + first);
        }
    }

    static void testGetFirstEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        try {
            list.getFirst();
            throw new AssertionError("Expected NoSuchElementException to be thrown");
        } catch (NoSuchElementException e) {
            // expected exception
        }
    }
}
