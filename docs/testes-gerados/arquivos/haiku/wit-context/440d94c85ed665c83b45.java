/*
 * @test
 * @summary Verifies observable behavior of LinkedBlockingDeque.pollLast()
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testPollLastOnEmptyDequeReturnsNull();
        testPollLastOnRepeatedEmptyDequeKeepsReturningNull();
        testPollLastReturnsElementsInReverseInsertionOrder();
        testPollLastRemovesOnlyLastElement();
        testPollLastFromCollectionConstructor();
        testPollLastOnCapacityBoundedDeque();
    }

    static void testPollLastOnEmptyDequeReturnsNull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        check(deque.pollLast() == null, "pollLast on empty deque must return null");
        check(deque.size() == 0, "size must remain 0 after pollLast on empty deque");
    }

    static void testPollLastOnRepeatedEmptyDequeKeepsReturningNull() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        for (int i = 0; i < 3; i++) {
            check(deque.pollLast() == null, "repeated pollLast on empty deque must return null");
        }
        check(deque.isEmpty(), "deque must stay empty");
    }

    static void testPollLastReturnsElementsInReverseInsertionOrder() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.offerLast(1);
        deque.offerLast(2);
        deque.offerLast(3);
        check(deque.pollLast() == 3, "first pollLast must return 3");
        check(deque.pollLast() == 2, "second pollLast must return 2");
        check(deque.pollLast() == 1, "third pollLast must return 1");
        check(deque.pollLast() == null, "pollLast after draining must return null");
    }

    static void testPollLastRemovesOnlyLastElement() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.offerLast(1);
        deque.offerLast(2);
        check(deque.pollLast() == 2, "pollLast must return last element");
        check(deque.size() == 1, "size must be 1 after pollLast");
        check(deque.pollFirst() == 1, "remaining element must be the first one");
        check(deque.isEmpty(), "deque must be empty");
    }

    static void testPollLastFromCollectionConstructor() {
        LinkedBlockingDeque<String> deque =
                new LinkedBlockingDeque<>(Arrays.asList("a", "b", "c"));
        check("c".equals(deque.pollLast()), "pollLast must return last element of source collection");
        check(deque.size() == 2, "size must be 2 after pollLast");
    }

    static void testPollLastOnCapacityBoundedDeque() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(2);
        check(deque.offerLast(1), "offerLast within capacity must succeed");
        check(deque.offerLast(2), "offerLast within capacity must succeed");
        check(!deque.offerLast(3), "offerLast beyond capacity must return false");
        check(deque.pollLast() == 2, "pollLast must return 2");
        check(deque.offerLast(3), "offerLast must succeed after pollLast freed space");
        check(deque.pollLast() == 3, "pollLast must return 3");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
