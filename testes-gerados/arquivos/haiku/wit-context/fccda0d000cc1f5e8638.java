/*
 * @test
 * @summary Witup-derived behavioral checks for LinkedBlockingDeque.push
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testPushNullThrowsNPE();
        testPushInsertsAtHead();
        testPushOnFullBoundedDequeThrowsISE();
        System.out.println("PASS");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testPushNullThrowsNPE() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean thrown = false;
        try {
            deque.push(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        check(thrown, "push(null) must throw NullPointerException");
        check(deque.isEmpty(), "deque must remain empty after push(null)");
    }

    static void testPushInsertsAtHead() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.push("a");
        deque.push("b");
        check(deque.size() == 2, "size after two pushes must be 2");
        check("b".equals(deque.peekFirst()), "last pushed element must be at head");
        check("a".equals(deque.peekLast()), "first pushed element must be at tail");
    }

    static void testPushOnFullBoundedDequeThrowsISE() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(1);
        deque.push("x");
        boolean thrown = false;
        try {
            deque.push("y");
        } catch (IllegalStateException expected) {
            thrown = true;
        }
        check(thrown, "push on full bounded deque must throw IllegalStateException");
        check(deque.size() == 1, "size must stay at capacity after failed push");
        check("x".equals(deque.peekFirst()), "existing element must be preserved");
    }
}
