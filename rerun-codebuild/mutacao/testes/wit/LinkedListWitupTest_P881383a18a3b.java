/* @test    @summary Test getFirst() method of LinkedList for normal and exceptional behavior    @run main LinkedListWitupTest_P881383a18a3b */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest_P881383a18a3b {

    public static void main(String[] args) {
        testGetFirstWithElements();
        testGetFirstEmpty();
    }

    private static void testGetFirstWithElements() {
        LinkedList<String> list = new LinkedList<>();
        list.add("element1");
        list.add("element2");
        String first = list.getFirst();
        if (!"element1".equals(first)) {
            throw new AssertionError("Expected 'element1', but got: " + first);
        }
    }

    private static void testGetFirstEmpty() {
        LinkedList<String> list = new LinkedList<>();
        try {
            list.getFirst();
            throw new AssertionError("Expected NoSuchElementException to be thrown");
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }
}
