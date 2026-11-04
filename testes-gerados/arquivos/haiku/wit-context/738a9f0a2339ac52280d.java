/*
 * @test
 * @summary Witup-generated tests for LinkedBlockingDeque.put
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) throws Exception {
        testNullThrowsNPE();
        testNullLeavesDequeUnchanged();
        testPutAddsLast();
        testPutOnBoundedNotFull();
        System.out.println("PASSED");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullThrowsNPE() throws Exception {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.put(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    static void testNullLeavesDequeUnchanged() throws Exception {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.put(null);
        } catch (NullPointerException expected) {
        }
        check(deque.isEmpty(), "deque must remain empty after put(null)");
        check(deque.size() == 0, "size must be 0 after put(null)");
    }

    static void testPutAddsLast() throws Exception {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.put("a");
        deque.put("b");
        check(deque.size() == 2, "size must be 2");
        check("a".equals(deque.peekFirst()), "first element must be a");
        check("b".equals(deque.peekLast()), "last element must be b");
    }

    static void testPutOnBoundedNotFull() throws Exception {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(2);
        deque.put("x");
        check(deque.remainingCapacity() == 1, "remaining capacity must be 1");
        deque.put("y");
        check(deque.remainingCapacity() == 0, "remaining capacity must be 0");
        check(!deque.offer("z"), "offer must return false when full");
        check(deque.size() == 2, "size must stay 2");
    }
}
