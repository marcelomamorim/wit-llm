/*
 * @test
 * @summary Verifies LinkedList.removeLast returns and unlinks the last element and throws NoSuchElementException when empty
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testEmptyListThrows();
        testRemoveLastReturnsAndUnlinksLast();
        testSingleElementList();
        testNullElementIsReturned();
        testListFromCollectionConstructor();
    }

    static void testEmptyListThrows() {
        LinkedList<String> list = new LinkedList<>();
        boolean thrown = false;
        try {
            list.removeLast();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "removeLast on empty list must throw NoSuchElementException");
        check(list.isEmpty(), "list must remain empty after failed removeLast");
    }

    static void testRemoveLastReturnsAndUnlinksLast() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        check("c".equals(list.removeLast()), "removeLast must return last element");
        check(list.size() == 2, "size must decrease by one");
        check("b".equals(list.getLast()), "new last element must be b");
        check("b".equals(list.removeLast()), "second removeLast must return b");
        check("a".equals(list.removeLast()), "third removeLast must return a");
        check(list.isEmpty(), "list must be empty after removing all elements");
    }

    static void testSingleElementList() {
        LinkedList<String> list = new LinkedList<>();
        list.add("only");
        check("only".equals(list.removeLast()), "single element must be returned");
        check(list.size() == 0, "list must be empty after removing single element");
        boolean thrown = false;
        try {
            list.removeLast();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "removeLast after draining must throw NoSuchElementException");
    }

    static void testNullElementIsReturned() {
        LinkedList<String> list = new LinkedList<>();
        list.add("x");
        list.add(null);
        check(list.removeLast() == null, "null last element must be returned as null");
        check(list.size() == 1, "size must be 1 after removing null element");
        check("x".equals(list.removeLast()), "remaining element must be x");
    }

    static void testListFromCollectionConstructor() {
        LinkedList<Integer> source = new LinkedList<>();
        source.add(Integer.valueOf(1));
        source.add(Integer.valueOf(2));
        source.add(Integer.valueOf(3));
        LinkedList<Integer> copy = new LinkedList<>(source);
        check(Integer.valueOf(3).equals(copy.removeLast()), "removeLast on copy must return 3");
        check(copy.size() == 2, "copy size must be 2 after removeLast");
        check(Integer.valueOf(3).equals(source.getLast()), "source list must not be affected");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
