/*
 * @test
 * @summary Verifies observable behavior of LinkedBlockingDeque.pollLast()
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testPollLastOnEmptyReturnsNull();
        testPollLastReturnsTailInOrder();
        testPollLastRemovesOnlyTail();
        testPollLastOnCollectionConstructedDeque();
        testPollLastFreesCapacity();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testPollLastOnEmptyReturnsNull() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<String>();
        check(d.pollLast() == null, "pollLast on empty deque should return null");
        check(d.isEmpty(), "deque should remain empty");
    }

    static void testPollLastReturnsTailInOrder() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<String>();
        d.offerLast("a");
        d.offerLast("b");
        d.offerLast("c");
        check("c".equals(d.pollLast()), "first pollLast should return c");
        check("b".equals(d.pollLast()), "second pollLast should return b");
        check("a".equals(d.pollLast()), "third pollLast should return a");
        check(d.pollLast() == null, "pollLast on drained deque should return null");
        check(d.isEmpty(), "deque should be empty after draining");
    }

    static void testPollLastRemovesOnlyTail() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<String>();
        d.offerFirst("x");
        d.offerLast("y");
        check("y".equals(d.pollLast()), "pollLast should return y");
        check(d.size() == 1, "size should be 1 after pollLast");
        check("x".equals(d.peekFirst()), "head element x should remain");
    }

    static void testPollLastOnCollectionConstructedDeque() {
        LinkedBlockingDeque<String> d =
            new LinkedBlockingDeque<String>(Arrays.asList("1", "2", "3"));
        check("3".equals(d.pollLast()), "pollLast should return 3");
        check("2".equals(d.pollLast()), "pollLast should return 2");
        check("1".equals(d.pollFirst()), "pollFirst should return remaining 1");
        check(d.pollLast() == null, "pollLast on empty deque should return null");
    }

    static void testPollLastFreesCapacity() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<String>(2);
        check(d.offerLast("a"), "offer a should succeed");
        check(d.offerLast("b"), "offer b should succeed");
        check(!d.offerLast("c"), "offer c should fail when full");
        check("b".equals(d.pollLast()), "pollLast should return b");
        check(d.offerLast("c"), "offer c should succeed after pollLast freed capacity");
        check("c".equals(d.pollLast()), "pollLast should return c");
        check("a".equals(d.pollLast()), "pollLast should return a");
    }
}
