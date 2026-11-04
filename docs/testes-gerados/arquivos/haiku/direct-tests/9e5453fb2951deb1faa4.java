/*
 * @test
 * @summary Behavioral tests for LinkedBlockingDeque.putLast on a non-blocking path
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullRejected() {
        boolean thrown = false;
        try {
            new LinkedBlockingDeque<String>().putLast(null);
        } catch (NullPointerException expected) {
            thrown = true;
        } catch (InterruptedException e) {
            throw new RuntimeException("unexpected InterruptedException", e);
        }
        check(thrown, "putLast(null) must throw NullPointerException");
    }

    static void testAppendsAtTail() throws InterruptedException {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        d.putLast("a");
        d.putLast("b");
        check(d.size() == 2, "size should be 2");
        check("a".equals(d.peekFirst()), "first should be a");
        check("b".equals(d.peekLast()), "last should be b");
    }

    static void testBoundedInsertWhileNotFull() throws InterruptedException {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>(2);
        d.putLast(1);
        d.putLast(2);
        check(d.size() == 2, "size should be 2 after filling capacity");
        check(d.remainingCapacity() == 0, "remaining capacity should be 0");
        check(Integer.valueOf(2).equals(d.peekLast()), "last should be 2");
    }

    static void testInterruptWhenFull() throws InterruptedException {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>(1);
        d.putLast("x");
        Thread.currentThread().interrupt();
        boolean interrupted = false;
        try {
            d.putLast("y");
        } catch (InterruptedException expected) {
            interrupted = true;
        }
        check(interrupted, "putLast on full deque with interrupt set must throw InterruptedException");
        check(d.size() == 1, "size should remain 1 after interrupted put");
        check("x".equals(d.peekFirst()), "existing element should be unchanged");
    }

    public static void main(String[] args) throws Exception {
        testNullRejected();
        testAppendsAtTail();
        testBoundedInsertWhileNotFull();
        testInterruptWhenFull();
        System.out.println("PASSED");
    }
}
