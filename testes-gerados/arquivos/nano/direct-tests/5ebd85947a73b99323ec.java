/* @test    @summary Test the element() method of LinkedList for correct return of first element.    @run main LinkedListWitupTest */

import java.util.LinkedList;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testElementReturnsFirstElement();
        testElementOnEmptyListReturnsNull();
    }

    private static void testElementReturnsFirstElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add("first");
        list.add("second");
        String result = list.element();
        if (!"first".equals(result)) {
            throw new AssertionError("Expected 'first', but got: " + result);
        }
    }

    private static void testElementOnEmptyListReturnsNull() {
        LinkedList<String> list = new LinkedList<>();
        try {
            list.element();
            throw new AssertionError("Expected NoSuchElementException to be thrown");
        } catch (java.util.NoSuchElementException e) {
            // expected exception
        }
    }
}
