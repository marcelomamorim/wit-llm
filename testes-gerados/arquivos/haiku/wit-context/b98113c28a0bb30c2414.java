/*
 * @test
 * @summary Verifies observable behavior of the descending iterator's next() method in LinkedList, including NoSuchElementException and ConcurrentModificationException paths
 * @run main LinkedListWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testDescendingOrder();
        testSingleElement();
        testEmptyListThrowsNoSuchElement();
        testExhaustedIteratorThrowsNoSuchElement();
        testStructuralModificationThrowsConcurrentModification();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testDescendingOrder() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        Iterator<String> it = list.descendingIterator();
        check(it.hasNext(), "descending iterator should have elements");
        check("c".equals(it.next()), "first descending element should be c");
        check("b".equals(it.next()), "second descending element should be b");
        check("a".equals(it.next()), "third descending element should be a");
        check(!it.hasNext(), "descending iterator should be exhausted");
    }

    static void testSingleElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add("only");
        Iterator<String> it = list.descendingIterator();
        check("only".equals(it.next()), "single element should be returned");
        check(!it.hasNext(), "no further elements expected after single element");
    }

    static void testEmptyListThrowsNoSuchElement() {
        Iterator<String> it = new LinkedList<String>().descendingIterator();
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "next() on empty list's descending iterator should throw NoSuchElementException");
    }

    static void testExhaustedIteratorThrowsNoSuchElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add("x");
        Iterator<String> it = list.descendingIterator();
        check("x".equals(it.next()), "first next() should return x");
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "next() on exhausted descending iterator should throw NoSuchElementException");
    }

    static void testStructuralModificationThrowsConcurrentModification() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        Iterator<String> it = list.descendingIterator();
        list.add("c");
        boolean thrown = false;
        try {
            it.next();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "next() after structural modification should throw ConcurrentModificationException");
    }
}
