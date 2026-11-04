/*
 * @test
 * @summary Witup-generated behavioral tests for LinkedBlockingDeque.takeLast
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) throws Exception {
        testReturnsAndRemovesLastElement();
        testBlocksUntilElementAvailable();
        testInterruptedWhenEmpty();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testReturnsAndRemovesLastElement() throws InterruptedException {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        d.addFirst("a");
        d.addLast("b");
        check("b".equals(d.takeLast()), "takeLast should return the last element");
        check(d.size() == 1, "takeLast should remove exactly one element");
        check("a".equals(d.peekLast()), "remaining element should be the former first");
    }

    static void testBlocksUntilElementAvailable() throws InterruptedException {
        final LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        final Object[] result = new Object[1];
        Thread t = new Thread(() -> {
            try {
                result[0] = d.takeLast();
            } catch (InterruptedException e) {
                result[0] = "interrupted";
            }
        });
        t.start();
        d.putLast("x");
        t.join(10000);
        check(!t.isAlive(), "takeLast should complete after an element is added");
        check("x".equals(result[0]), "takeLast should return the added element");
    }

    static void testInterruptedWhenEmpty() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        boolean thrown = false;
        Thread.currentThread().interrupt();
        try {
            d.takeLast();
        } catch (InterruptedException e) {
            thrown = true;
        }
        Thread.interrupted();
        check(thrown, "takeLast on an empty deque should throw InterruptedException when interrupted");
    }
}
