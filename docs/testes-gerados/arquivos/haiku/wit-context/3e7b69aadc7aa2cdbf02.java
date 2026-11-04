/*
 * @test
 * @summary Witup-derived checks for LinkedBlockingDeque.removeLast on empty and non-empty deques
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testEmptyDequeThrowsNoSuchElement();
        testLifoOrderAfterAddLast();
        testAddFirstThenRemoveLastReturnsTail();
        testBoundedCapacityConstructor();
        testCollectionConstructor();
        testRemoveLastDrainsToEmpty();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectNoSuchElement(LinkedBlockingDeque<String> deque) {
        try {
            deque.removeLast();
        } catch (NoSuchElementException e) {
            return;
        }
        throw new RuntimeException("expected NoSuchElementException");
    }

    static void testEmptyDequeThrowsNoSuchElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        expectNoSuchElement(deque);
        check(deque.isEmpty(), "deque should remain empty");
    }

    static void testLifoOrderAfterAddLast() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.addLast("1");
        deque.addLast("2");
        deque.addLast("3");
        check("3".equals(deque.removeLast()), "first removeLast should return 3");
        check("2".equals(deque.removeLast()), "second removeLast should return 2");
        check("1".equals(deque.removeLast()), "third removeLast should return 1");
        expectNoSuchElement(deque);
    }

    static void testAddFirstThenRemoveLastReturnsTail() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.addFirst("b");
        deque.addFirst("a");
        check("b".equals(deque.removeLast()), "removeLast should return the tail element");
        check("a".equals(deque.removeLast()), "removeLast should return remaining element");
        expectNoSuchElement(deque);
    }

    static void testBoundedCapacityConstructor() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(2);
        deque.addLast("x");
        deque.addLast("y");
        check("y".equals(deque.removeLast()), "removeLast should return last inserted element");
        check("x".equals(deque.removeLast()), "removeLast should return previous element");
        expectNoSuchElement(deque);
    }

    static void testCollectionConstructor() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(Arrays.asList("a", "b"));
        check("b".equals(deque.removeLast()), "removeLast should return last element of source collection");
        check("a".equals(deque.removeLast()), "removeLast should return first element after tail removal");
        expectNoSuchElement(deque);
    }

    static void testRemoveLastDrainsToEmpty() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.offerLast("p");
        check("p".equals(deque.removeLast()), "removeLast should return the only element");
        check(deque.size() == 0, "deque should be empty after draining");
        expectNoSuchElement(deque);
    }
}
