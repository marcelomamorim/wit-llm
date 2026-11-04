/*
 * @test
 * @summary Witup-derived checks for LinkedBlockingDeque.getFirst on empty and non-empty deques
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectNoSuchElement(LinkedBlockingDeque<String> deque, String message) {
        try {
            deque.getFirst();
        } catch (NoSuchElementException expected) {
            return;
        }
        throw new RuntimeException(message);
    }

    static void testEmptyDequeThrowsNoSuchElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<String>();
        expectNoSuchElement(deque, "getFirst on empty deque must throw NoSuchElementException");
    }

    static void testEmptyBoundedDequeThrowsNoSuchElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<String>(4);
        expectNoSuchElement(deque, "getFirst on empty bounded deque must throw NoSuchElementException");
    }

    static void testReturnsHeadElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<String>();
        deque.addFirst("a");
        check("a".equals(deque.getFirst()), "getFirst must return the single element");
    }

    static void testReturnsMostRecentlyAddedFirst() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<String>();
        deque.addLast("x");
        deque.addFirst("y");
        check("y".equals(deque.getFirst()), "getFirst must return the head after addFirst");
    }

    static void testDoesNotRemoveElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<String>();
        deque.addLast("only");
        String first = deque.getFirst();
        String second = deque.getFirst();
        check("only".equals(first), "first getFirst must return the element");
        check("only".equals(second), "second getFirst must return the same element");
        check(deque.size() == 1, "getFirst must not remove the element");
    }

    static void testCollectionConstructorHeadIsFirstElement() {
        List<String> source = new ArrayList<String>();
        source.add("p");
        source.add("q");
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<String>(source);
        check("p".equals(deque.getFirst()), "getFirst must return the first element of the source collection");
    }

    static void testThrowsAfterDrainingDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<String>(2);
        deque.addLast("e1");
        check("e1".equals(deque.pollFirst()), "pollFirst must return the element");
        expectNoSuchElement(deque, "getFirst after draining must throw NoSuchElementException");
    }

    public static void main(String[] args) {
        testEmptyDequeThrowsNoSuchElement();
        testEmptyBoundedDequeThrowsNoSuchElement();
        testReturnsHeadElement();
        testReturnsMostRecentlyAddedFirst();
        testDoesNotRemoveElement();
        testCollectionConstructorHeadIsFirstElement();
        testThrowsAfterDrainingDeque();
    }
}
