/* @test    @summary Test for LinkedList.next() method to ensure correct element retrieval and exception handling.    @run main LinkedListWitupTest */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testNextReturnsElement();
        testNextThrowsNoSuchElementException();
    }

    private static void testNextReturnsElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add("first");
        list.add("second");
        list.add("third");
        java.util.Iterator<String> iterator = list.iterator();
        String firstElement = iterator.next();
        assert "first".equals(firstElement) : "Expected 'first', got " + firstElement;
    }

    private static void testNextThrowsNoSuchElementException() {
        LinkedList<String> list = new LinkedList<>();
        list.add("only");
        java.util.Iterator<String> iterator = list.iterator();
        iterator.next(); // consumes the only element
        boolean exceptionThrown = false;
        try {
            iterator.next();
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NoSuchElementException to be thrown when calling next() on exhausted iterator";
    }
}
