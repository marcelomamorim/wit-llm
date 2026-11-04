/*
 * @test
 * @summary Verifies observable behavior of LinkedBlockingDeque.peek(), which delegates to peekFirst()
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) throws Exception {
        testPeekOnEmptyDequeReturnsNull();
        testPeekReturnsHeadWithoutRemoving();
        testPeekReturnsFirstAfterAddFirst();
        testPeekAfterPollFirstReturnsNewHead();
        testPeekOnBoundedDeque();
        testPeekOnDequeFromCollection();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testPeekOnEmptyDequeReturnsNull() {
        LinkedBlockingDeque<Integer> q = new LinkedBlockingDeque<>();
        check(q.peek() == null, 'peek() on empty deque should return null');
        check(q.size() == 0, 'peek() on empty deque should not change size');
    }

    static void testPeekReturnsHeadWithoutRemoving() {
        LinkedBlockingDeque<Integer> q = new LinkedBlockingDeque<>();
        q.offerLast(10);
        q.offerLast(20);
        check(Integer.valueOf(10).equals(q.peek()), 'peek() should return the head element');
        check(q.size() == 2, 'peek() should not remove the head element');
        check(Integer.valueOf(10).equals(q.peek()), 'repeated peek() should return the same head');
    }

    static void testPeekReturnsFirstAfterAddFirst() {
        LinkedBlockingDeque<Integer> q = new LinkedBlockingDeque<>();
        q.offerLast(2);
        q.addFirst(1);
        check(Integer.valueOf(1).equals(q.peek()), 'peek() should return element inserted at front');
    }

    static void testPeekAfterPollFirstReturnsNewHead() {
        LinkedBlockingDeque<Integer> q = new LinkedBlockingDeque<>();
        q.offerLast(1);
        q.offerLast(2);
        q.pollFirst();
        check(Integer.valueOf(2).equals(q.peek()), 'peek() should return new head after pollFirst()');
        q.pollFirst();
        check(q.peek() == null, 'peek() should return null after last element is removed');
    }

    static void testPeekOnBoundedDeque() {
        LinkedBlockingDeque<Integer> q = new LinkedBlockingDeque<>(2);
        check(q.offerLast(5), 'offerLast should succeed below capacity');
        check(q.offerLast(6), 'offerLast should succeed at capacity-1');
        check(!q.offerLast(7), 'offerLast should fail when full');
        check(Integer.valueOf(5).equals(q.peek()), 'peek() should return head of bounded deque');
        check(q.size() == 2, 'peek() should not change size of bounded deque');
    }

    static void testPeekOnDequeFromCollection() {
        LinkedBlockingDeque<Integer> q = new LinkedBlockingDeque<>(Arrays.asList(3, 4, 5));
        check(Integer.valueOf(3).equals(q.peek()), 'peek() should return first element of collection-based deque');
        check(q.size() == 3, 'peek() should not remove elements from collection-based deque');
    }
}
