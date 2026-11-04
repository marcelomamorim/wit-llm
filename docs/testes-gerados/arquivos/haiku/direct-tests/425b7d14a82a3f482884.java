/*
 * @test
 * @summary Witup-style tests for LinkedBlockingDeque.putFirst
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) throws Exception {
        testNullThrowsNPE();
        testPutFirstUnbounded();
        testPutFirstBoundedWithCapacity();
        testInterruptedWhenFull();
    }

    static void check(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException(msg);
        }
    }

    static void testNullThrowsNPE() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        try {
            d.putFirst(null);
            throw new RuntimeException("putFirst(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
        check(d.isEmpty(), "deque should remain empty after null insertion attempt");
    }

    static void testPutFirstUnbounded() throws InterruptedException {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        d.putFirst("a");
        d.putFirst("b");
        check(d.size() == 2, "size should be 2");
        check("b".equals(d.peekFirst()), "last putFirst element should be first");
        check("a".equals(d.peekLast()), "first putFirst element should be last");
    }

    static void testPutFirstBoundedWithCapacity() throws InterruptedException {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>(2);
        d.putFirst("x");
        d.putFirst("y");
        check(d.remainingCapacity() == 0, "remaining capacity should be 0 when full");
        check("y".equals(d.peekFirst()), "front should be y");
        check("x".equals(d.peekLast()), "back should be x");
    }

    static void testInterruptedWhenFull() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>(1);
        try {
            d.putFirst("a");
        } catch (InterruptedException e) {
            throw new RuntimeException("unexpected InterruptedException on setup", e);
        }
        Thread.currentThread().interrupt();
        try {
            d.putFirst("b");
            throw new RuntimeException("putFirst on full deque with interrupt should throw");
        } catch (InterruptedException expected) {
            // expected
        }
        check(d.size() == 1, "size should remain 1");
        check("a".equals(d.peekFirst()), "original element should be unchanged");
    }
}
