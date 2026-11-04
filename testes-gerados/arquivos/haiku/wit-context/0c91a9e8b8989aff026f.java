/*
 * @test
 * @summary Witup-derived checks for LinkedBlockingDeque.add(E): null rejection, successful insertion order, and failed insertion on a full deque
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testAddNullThrowsNullPointerException();
        testAddNonNullReturnsTrueAndKeepsOrder();
        testAddToFullDequeThrowsIllegalStateException();
        testFailedAddDoesNotModifyDeque();
    }

    static void testAddNullThrowsNullPointerException() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean thrown = false;
        try {
            deque.add(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "add(null) must throw NullPointerException");
        check(deque.isEmpty(), "deque must remain empty after add(null)");
    }

    static void testAddNonNullReturnsTrueAndKeepsOrder() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        check(deque.add("a"), "add must return true for successful insertion");
        check(deque.add("b"), "add must return true for successful insertion");
        check(deque.size() == 2, "size must be 2 after two adds");
        check("a".equals(deque.peekFirst()), "first element must be the first added");
        check("b".equals(deque.peekLast()), "last element must be the last added");
    }

    static void testAddToFullDequeThrowsIllegalStateException() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(1);
        check(deque.add("x"), "first add into capacity 1 must succeed");
        boolean thrown = false;
        try {
            deque.add("y");
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "add into full deque must throw IllegalStateException");
    }

    static void testFailedAddDoesNotModifyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(1);
        deque.add("x");
        try {
            deque.add("y");
        } catch (IllegalStateException e) {
            // expected; state is checked below
        }
        check(deque.size() == 1, "size must remain 1 after failed add");
        check("x".equals(deque.peekFirst()), "original element must be preserved after failed add");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
