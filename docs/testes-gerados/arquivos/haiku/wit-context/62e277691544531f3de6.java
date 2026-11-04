/*
 * @test
 * @summary Witup-generated behavioral checks for LinkedBlockingDeque.addLast
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testAddLastAppendsAtTail();
        testAddLastOnFullDequeThrowsIllegalStateException();
        testAddLastNullThrowsNullPointerException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAddLastAppendsAtTail() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.addLast("a");
        deque.addLast("b");
        deque.addLast("c");
        check(deque.size() == 3, "size after three addLast calls");
        check("a".equals(deque.pollFirst()), "first element should be a");
        check("b".equals(deque.pollFirst()), "second element should be b");
        check("c".equals(deque.pollFirst()), "third element should be c");
        check(deque.isEmpty(), "deque should be empty");
    }

    static void testAddLastOnFullDequeThrowsIllegalStateException() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(1);
        deque.addLast("a");
        boolean thrown = false;
        try {
            deque.addLast("b");
        } catch (IllegalStateException expected) {
            thrown = true;
        }
        check(thrown, "addLast on full deque should throw IllegalStateException");
        check(deque.size() == 1, "failed addLast must not change size");
        check("a".equals(deque.peekLast()), "failed addLast must not change contents");
    }

    static void testAddLastNullThrowsNullPointerException() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean thrown = false;
        try {
            deque.addLast(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        check(thrown, "addLast(null) should throw NullPointerException");
        check(deque.isEmpty(), "addLast(null) must not insert an element");
    }
}
