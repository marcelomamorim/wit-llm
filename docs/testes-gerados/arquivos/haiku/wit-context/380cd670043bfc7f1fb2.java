/*
 * @test
 * @summary Verifies ArrayDeque.addLast rejects null elements without modifying the deque and preserves insertion order across growth
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testNullThrowsNullPointerException();
        testNullDoesNotModifyDeque();
        testOrderPreservedAcrossGrowth();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullThrowsNullPointerException() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        boolean thrown = false;
        try {
            deque.addLast(null);
        } catch (NullPointerException ex) {
            thrown = true;
        }
        check(thrown, "addLast(null) must throw NullPointerException");
    }

    static void testNullDoesNotModifyDeque() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.addLast("a");
        boolean thrown = false;
        try {
            deque.addLast(null);
        } catch (NullPointerException ex) {
            thrown = true;
        }
        check(thrown, "addLast(null) must throw NullPointerException");
        check(deque.size() == 1, "size must remain 1 after rejected null, was " + deque.size());
        check("a".equals(deque.peekLast()), "last element must remain 'a'");
        check("a".equals(deque.peekFirst()), "first element must remain 'a'");
    }

    static void testOrderPreservedAcrossGrowth() {
        ArrayDeque<Integer> deque = new ArrayDeque<>(1);
        final int n = 100;
        for (int i = 0; i < n; i++) {
            deque.addLast(i);
        }
        check(deque.size() == n, "size must be " + n + ", was " + deque.size());
        check(deque.peekFirst() == 0, "first element must be 0");
        check(deque.peekLast() == n - 1, "last element must be " + (n - 1));
        for (int i = 0; i < n; i++) {
            Integer value = deque.pollFirst();
            check(value != null && value == i, "expected " + i + " but got " + value);
        }
        check(deque.isEmpty(), "deque must be empty after draining");
    }
}
