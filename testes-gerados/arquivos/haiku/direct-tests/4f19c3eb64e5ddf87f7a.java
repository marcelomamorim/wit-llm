/*
 * @test
 * @summary Black-box checks of LinkedBlockingDeque.pollFirst() observable behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testPollFirstOnEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        check(deque.pollFirst() == null, "pollFirst on empty deque must return null");
        check(deque.size() == 0, "size must remain 0 after pollFirst on empty deque");
        check(deque.isEmpty(), "deque must still be empty");
    }

    static void testPollFirstReturnsElementsInFifoOrderWhenOfferedLast() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.offerLast("a");
        deque.offerLast("b");
        deque.offerLast("c");
        check("a".equals(deque.pollFirst()), "first pollFirst must return a");
        check("b".equals(deque.pollFirst()), "second pollFirst must return b");
        check("c".equals(deque.pollFirst()), "third pollFirst must return c");
        check(deque.pollFirst() == null, "pollFirst after draining must return null");
    }

    static void testPollFirstReturnsLastOfferedFirst() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.offerFirst("a");
        deque.offerFirst("b");
        check("b".equals(deque.pollFirst()), "pollFirst must return most recently offered-first element");
        check("a".equals(deque.pollFirst()), "pollFirst must then return a");
    }

    static void testPollFirstOnCollectionConstructedDeque() {
        LinkedBlockingDeque<String> deque =
            new LinkedBlockingDeque<>(Arrays.asList("x", "y"));
        check("x".equals(deque.pollFirst()), "pollFirst must return first element of source collection");
        check(deque.size() == 1, "size must decrease by one after pollFirst");
        check("y".equals(deque.pollFirst()), "second pollFirst must return y");
        check(deque.pollFirst() == null, "pollFirst must return null when empty");
    }

    static void testPollFirstFreesCapacityInBoundedDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(1);
        check(deque.offerLast("a"), "offerLast into empty bounded deque must succeed");
        check(!deque.offerLast("b"), "offerLast into full bounded deque must fail");
        check("a".equals(deque.pollFirst()), "pollFirst must return a");
        check(deque.pollFirst() == null, "pollFirst on empty bounded deque must return null");
        check(deque.offerLast("b"), "offerLast must succeed again after pollFirst freed capacity");
        check("b".equals(deque.pollFirst()), "pollFirst must return b");
    }

    public static void main(String[] args) {
        testPollFirstOnEmptyDeque();
        testPollFirstReturnsElementsInFifoOrderWhenOfferedLast();
        testPollFirstReturnsLastOfferedFirst();
        testPollFirstOnCollectionConstructedDeque();
        testPollFirstFreesCapacityInBoundedDeque();
    }
}
