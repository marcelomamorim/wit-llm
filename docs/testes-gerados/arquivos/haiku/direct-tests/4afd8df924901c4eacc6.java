/*
 * @test
 * @summary Behavioral tests for LinkedBlockingDeque.peekFirst()
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.Arrays;
import java.util.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) throws Exception {
        testEmptyDequeReturnsNull();
        testPeekFirstReturnsHeadWithoutRemoving();
        testPeekFirstAfterAddLastOnEmpty();
        testPeekFirstReturnsMostRecentlyAddedFirst();
        testPeekFirstAfterPollFirst();
        testPeekFirstOnBoundedDeque();
        testPeekFirstOnCollectionConstructor();
        System.out.println("All peekFirst tests passed.");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyDequeReturnsNull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        check(deque.peekFirst() == null, "peekFirst on empty deque should return null");
        check(deque.size() == 0, "size should remain 0 after peekFirst on empty deque");
    }

    static void testPeekFirstReturnsHeadWithoutRemoving() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.addFirst("a");
        deque.addLast("b");
        check("a".equals(deque.peekFirst()), "peekFirst should return head element");
        check(deque.size() == 2, "peekFirst must not remove the element");
        check("a".equals(deque.peekFirst()), "second peekFirst should return same head");
        check("a".equals(deque.getFirst()), "getFirst should still see head after peekFirst");
    }

    static void testPeekFirstAfterAddLastOnEmpty() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.addLast("x");
        check("x".equals(deque.peekFirst()), "peekFirst should return the only element added via addLast");
        check(deque.size() == 1, "size should be 1");
    }

    static void testPeekFirstReturnsMostRecentlyAddedFirst() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.addFirst("a");
        deque.addFirst("b");
        deque.addFirst("c");
        check("c".equals(deque.peekFirst()), "peekFirst should return the last element added at front");
    }

    static void testPeekFirstAfterPollFirst() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.addLast("p");
        deque.addLast("q");
        check("p".equals(deque.pollFirst()), "pollFirst should return p");
        check("q".equals(deque.peekFirst()), "peekFirst should return q after p was polled");
        check("q".equals(deque.pollFirst()), "pollFirst should return q");
        check(deque.peekFirst() == null, "peekFirst should return null once deque is drained");
    }

    static void testPeekFirstOnBoundedDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(1);
        check(deque.peekFirst() == null, "peekFirst on empty bounded deque should return null");
        check(deque.offerFirst("a"), "offerFirst should succeed on empty bounded deque");
        check("a".equals(deque.peekFirst()), "peekFirst should return element in bounded deque");
        check(!deque.offerFirst("b"), "offerFirst should fail when capacity is reached");
        check("a".equals(deque.peekFirst()), "peekFirst should still return original head when full");
        check(deque.size() == 1, "size should remain 1");
    }

    static void testPeekFirstOnCollectionConstructor() {
        LinkedBlockingDeque<Integer> deque =
                new LinkedBlockingDeque<>(Arrays.asList(10, 20, 30));
        check(Integer.valueOf(10).equals(deque.peekFirst()),
                "peekFirst should return first element of the source collection");
        check(deque.size() == 3, "size should be 3 after peekFirst");
    }
}
