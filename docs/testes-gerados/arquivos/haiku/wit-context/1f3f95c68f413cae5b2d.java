/*
 * @test
 * @summary Witup-derived tests for LinkedBlockingDeque.addFirst
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testAddFirstOrdering();
        testAddFirstWhenFull();
        testAddFirstNull();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAddFirstOrdering() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.addFirst("a");
        deque.addFirst("b");
        check(deque.size() == 2, "size should be 2");
        check("b".equals(deque.peekFirst()), "first should be b");
        check("a".equals(deque.peekLast()), "last should be a");
    }

    static void testAddFirstWhenFull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(1);
        deque.addFirst("a");
        boolean thrown = false;
        try {
            deque.addFirst("b");
        } catch (IllegalStateException expected) {
            thrown = true;
        }
        check(thrown, "addFirst on full deque should throw IllegalStateException");
        check(deque.size() == 1, "size should remain 1 after failed addFirst");
        check("a".equals(deque.peekFirst()), "first should remain a");
    }

    static void testAddFirstNull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean thrown = false;
        try {
            deque.addFirst(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        check(thrown, "addFirst(null) should throw NullPointerException");
        check(deque.isEmpty(), "deque should remain empty after addFirst(null)");
    }
}
