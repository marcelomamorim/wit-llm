/*
 * @test
 * @summary WITUP-derived tests for ArrayDeque.add(E): null rejection and basic append behavior
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testAddNullThrowsNullPointerException();
        testAddNullDoesNotModifyDeque();
        testAddReturnsTrueAndAppendsElement();
        testAddPreservesInsertionOrder();
        System.out.println("PASSED");
    }

    static void testAddNullThrowsNullPointerException() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        boolean thrown = false;
        try {
            deque.add(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("add(null) did not throw NullPointerException");
        }
    }

    static void testAddNullDoesNotModifyDeque() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("a");
        try {
            deque.add(null);
        } catch (NullPointerException e) {
            // expected
        }
        if (deque.size() != 1) {
            throw new RuntimeException("size changed after rejected null: " + deque.size());
        }
        if (!"a".equals(deque.peekLast())) {
            throw new RuntimeException("last element changed after rejected null");
        }
    }

    static void testAddReturnsTrueAndAppendsElement() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        if (!deque.add("x")) {
            throw new RuntimeException("add returned false");
        }
        if (deque.size() != 1) {
            throw new RuntimeException("unexpected size: " + deque.size());
        }
        if (!"x".equals(deque.getLast())) {
            throw new RuntimeException("element not appended at tail");
        }
    }

    static void testAddPreservesInsertionOrder() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < 100; i++) {
            if (!deque.add(i)) {
                throw new RuntimeException("add returned false at " + i);
            }
        }
        if (deque.size() != 100) {
            throw new RuntimeException("unexpected size: " + deque.size());
        }
        int expected = 0;
        for (Integer value : deque) {
            if (value.intValue() != expected) {
                throw new RuntimeException("order mismatch: expected " + expected + " got " + value);
            }
            expected++;
        }
    }
}
