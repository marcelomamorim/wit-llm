/*
 * @test
 * @summary Basic observable behavior of LinkedBlockingDeque.add(E)
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testAddReturnsTrueAndAppendsLast();
        testAddThrowsIllegalStateWhenFull();
        testAddSucceedsAfterCapacityFreed();
        testAddNullThrowsNullPointerException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAddReturnsTrueAndAppendsLast() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        check(deque.add("a"), "add should return true for first element");
        check(deque.add("b"), "add should return true for second element");
        check(deque.size() == 2, "size should be 2 after two adds");
        check("a".equals(deque.peekFirst()), "first element should be a");
        check("b".equals(deque.peekLast()), "last element should be b");
    }

    static void testAddThrowsIllegalStateWhenFull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(1);
        check(deque.add("x"), "add should succeed while capacity remains");
        try {
            deque.add("y");
            throw new RuntimeException("add should throw IllegalStateException when full");
        } catch (IllegalStateException expected) {
            // expected
        }
        check(deque.size() == 1, "size should remain 1 after failed add");
        check("x".equals(deque.peekFirst()), "original element should be preserved");
    }

    static void testAddSucceedsAfterCapacityFreed() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(1);
        deque.add("x");
        check("x".equals(deque.poll()), "poll should return x");
        check(deque.add("y"), "add should succeed after capacity is freed");
        check("y".equals(deque.peekLast()), "last element should be y");
    }

    static void testAddNullThrowsNullPointerException() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.add(null);
            throw new RuntimeException("add(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
        check(deque.isEmpty(), "deque should remain empty after failed add(null)");
    }
}
