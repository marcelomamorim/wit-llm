/*
 * @test
 * @summary Test LinkedList.ListItr.next() for expected exceptions and normal behavior
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        testNextThrowsNoSuchElementException();
        testNextThrowsConcurrentModificationException();
        testNextReturnsCorrectElement();
    }

    private static void testNextThrowsNoSuchElementException() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        ListIterator<String> it = list.listIterator();
        it.next(); // consume the only element
        try {
            it.next(); // should throw NoSuchElementException
            throw new RuntimeException("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException e) {
            // expected
        }
    }

    private static void testNextThrowsConcurrentModificationException() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        ListIterator<String> it = list.listIterator();
        list.add("b"); // structural modification after iterator creation
        try {
            it.next(); // should throw ConcurrentModificationException
            throw new RuntimeException("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException e) {
            // expected
        }
    }

    private static void testNextReturnsCorrectElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add("first");
        list.add("second");
        ListIterator<String> it = list.listIterator();
        String first = it.next();
        if (!"first".equals(first)) {
            throw new RuntimeException("Expected 'first' but got: " + first);
        }
        String second = it.next();
        if (!"second".equals(second)) {
            throw new RuntimeException("Expected 'second' but got: " + second);
        }
    }
}
