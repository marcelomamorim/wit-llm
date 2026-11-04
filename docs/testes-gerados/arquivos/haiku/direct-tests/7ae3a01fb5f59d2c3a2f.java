/*
 * @test
 * @summary Tests ArrayDeque.addFirst: null rejection, front insertion order, growth and wrap-around behavior
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testNullThrowsNullPointerException();
        testAddFirstInsertsAtFront();
        testManyAddFirstPreservesOrder();
        testAddFirstAfterWrapAround();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullThrowsNullPointerException() {
        ArrayDeque<String> d = new ArrayDeque<>();
        try {
            d.addFirst(null);
            throw new RuntimeException("addFirst(null) did not throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
        check(d.isEmpty(), "deque must remain empty after rejected null");
        check(d.size() == 0, "size must remain 0 after rejected null");
    }

    static void testAddFirstInsertsAtFront() {
        ArrayDeque<Integer> d = new ArrayDeque<>();
        d.addFirst(1);
        check(d.size() == 1, "size after first addFirst");
        check(d.peekFirst() == 1, "peekFirst after addFirst(1)");
        check(d.peekLast() == 1, "peekLast after addFirst(1)");
        d.addFirst(2);
        d.addFirst(3);
        check(d.size() == 3, "size after three addFirst");
        check(d.peekFirst() == 3, "peekFirst must be last added");
        check(d.peekLast() == 1, "peekLast must be first added");
        check(d.pollFirst() == 3, "pollFirst order 1");
        check(d.pollFirst() == 2, "pollFirst order 2");
        check(d.pollFirst() == 1, "pollFirst order 3");
        check(d.isEmpty(), "deque must be empty at end");
    }

    static void testManyAddFirstPreservesOrder() {
        ArrayDeque<Integer> d = new ArrayDeque<>(1);
        final int n = 1000;
        for (int i = 0; i < n; i++) {
            d.addFirst(i);
        }
        check(d.size() == n, "size after " + n + " addFirst calls: " + d.size());
        check(d.peekFirst() == n - 1, "peekFirst after growth");
        check(d.peekLast() == 0, "peekLast after growth");
        for (int i = n - 1; i >= 0; i--) {
            Integer v = d.pollFirst();
            check(v != null && v == i, "order violated at expected " + i + " got " + v);
        }
        check(d.isEmpty(), "deque must be empty after draining");
    }

    static void testAddFirstAfterWrapAround() {
        ArrayDeque<Integer> d = new ArrayDeque<>(4);
        for (int i = 0; i < 20; i++) {
            d.addLast(i);
            d.pollFirst();
        }
        check(d.isEmpty(), "deque must be empty after cycling");
        d.addFirst(100);
        d.addFirst(200);
        d.addLast(300);
        check(d.size() == 3, "size after wrap-around inserts");
        check(d.pollFirst() == 200, "front after wrap-around addFirst 1");
        check(d.pollFirst() == 100, "front after wrap-around addFirst 2");
        check(d.pollFirst() == 300, "tail element after wrap-around");
        check(d.isEmpty(), "deque must be empty at end of wrap test");
    }
}
