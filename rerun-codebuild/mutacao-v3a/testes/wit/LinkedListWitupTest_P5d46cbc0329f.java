/* @test    @summary Test getFirst() method of LinkedList for null element case    @run main LinkedListWitupTest_P5d46cbc0329f */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest_P5d46cbc0329f {

    public static void main(String[] args) {
        testGetFirstEmptyList();
        testGetFirstNonEmptyList();
    }

    private static void testGetFirstEmptyList() {
        LinkedList<Object> list = new LinkedList<>();
        boolean threwException = false;
        try {
            list.getFirst();
        } catch (NoSuchElementException e) {
            threwException = true;
        }
        if (!threwException) {
            throw new AssertionError("Expected NoSuchElementException when calling getFirst() on empty list.");
        }
    }

    private static void testGetFirstNonEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        list.add("element1");
        String result = list.getFirst();
        if (!"element1".equals(result)) {
            throw new AssertionError("getFirst() did not return the expected element. Expected 'element1', got: " + result);
        }
    }
}
