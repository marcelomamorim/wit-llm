/*
 * @test
 * @summary Verify LinkedBlockingDeque.getFirst returns the head without removing it, and throws NoSuchElementException when the deque is empty
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testEmptyDequeThrows();
        testReturnsHeadWithoutRemoving();
        testAddFirstBecomesHead();
        testAfterDrainingThrows();
        testConstructedFromCollection();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testEmptyDequeThrows() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.getFirst();
            throw new AssertionError("expected NoSuchElementException on empty deque");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testReturnsHeadWithoutRemoving() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.offerLast("a");
        deque.offerLast("b");
        check("a".equals(deque.getFirst()), "getFirst should return head 'a'");
        check(deque.size() == 2, "getFirst must not remove the element");
        check("a".equals(deque.getFirst()), "second getFirst should still return 'a'");
    }

    static void testAddFirstBecomesHead() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.offerLast("b");
        deque.addFirst("a");
        check("a".equals(deque.getFirst()), "getFirst should reflect addFirst");
    }

    static void testAfterDrainingThrows() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(2);
        deque.offerLast("x");
        check("x".equals(deque.pollFirst()), "pollFirst should return 'x'");
        try {
            deque.getFirst();
            throw new AssertionError("expected NoSuchElementException after draining");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testConstructedFromCollection() {
        LinkedBlockingDeque<String> deque =
            new LinkedBlockingDeque<>(Arrays.asList("p", "q", "r"));
        check("p".equals(deque.getFirst()), "getFirst should return first element of source collection");
        check(deque.size() == 3, "size should match source collection");
    }
}
