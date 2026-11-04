/*
 * @test
 * @summary WITUP-derived checks for LinkedBlockingDeque.putLast(E)
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) throws Exception {
        testPutLastNullThrowsNPE();
        testPutLastNullDoesNotModifyDeque();
        testPutLastAddsToTail();
        testPutLastPreservesOrder();
        testPutLastWithBoundedCapacity();
        System.out.println("PASS");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testPutLastNullThrowsNPE() throws Exception {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.putLast(null);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    static void testPutLastNullDoesNotModifyDeque() throws Exception {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.putLast("x");
        try {
            deque.putLast(null);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
        check(deque.size() == 1, "size changed after rejected null: " + deque.size());
        check("x".equals(deque.peekLast()), "tail changed after rejected null");
    }

    static void testPutLastAddsToTail() throws Exception {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.putLast("a");
        check(deque.size() == 1, "size after one putLast: " + deque.size());
        check("a".equals(deque.peekFirst()), "first element mismatch");
        check("a".equals(deque.peekLast()), "last element mismatch");
    }

    static void testPutLastPreservesOrder() throws Exception {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.putLast("a");
        deque.putLast("b");
        check(deque.size() == 2, "size after two putLast: " + deque.size());
        check("a".equals(deque.peekFirst()), "head should be first inserted");
        check("b".equals(deque.peekLast()), "tail should be last inserted");
    }

    static void testPutLastWithBoundedCapacity() throws Exception {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(2);
        deque.putLast(1);
        deque.putLast(2);
        check(deque.size() == 2, "size should be 2");
        check(deque.remainingCapacity() == 0, "remaining capacity should be 0");
        check(deque.peekFirst() == 1, "first element mismatch");
        check(deque.peekLast() == 2, "last element mismatch");
    }
}
