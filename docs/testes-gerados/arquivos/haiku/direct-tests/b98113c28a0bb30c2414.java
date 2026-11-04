/*
 * @test
 * @summary Verifies LinkedList.descendingIterator() next() returns elements in reverse order and fails when exhausted
 * @run main LinkedListWitupTest
 */

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        Iterator<String> it = list.descendingIterator();
        check(it.hasNext(), "descending iterator should have elements");
        check("c".equals(it.next()), "first next() should return last element");
        check("b".equals(it.next()), "second next() should return middle element");
        check("a".equals(it.next()), "third next() should return first element");
        check(!it.hasNext(), "iterator should be exhausted");

        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "next() on exhausted iterator should throw NoSuchElementException");

        Iterator<String> empty = new LinkedList<String>().descendingIterator();
        check(!empty.hasNext(), "empty list descending iterator should have no elements");
        thrown = false;
        try {
            empty.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "next() on empty list descending iterator should throw NoSuchElementException");

        LinkedList<String> withNull = new LinkedList<>(Arrays.asList(null, "x"));
        Iterator<String> nit = withNull.descendingIterator();
        check("x".equals(nit.next()), "next() should return non-null last element first");
        check(nit.next() == null, "next() should return null element");
        check(!nit.hasNext(), "iterator with null element should be exhausted");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
