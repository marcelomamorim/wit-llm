/*
 * @test
 * @summary Witup-generated checks for ArrayDeque.addFirst null rejection, ordering and growth
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testNullThrowsNPE();
        testAddFirstOrderAndSize();
        testAddFirstAfterGrowth();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullThrowsNPE() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        try {
            deque.addFirst(null);
            throw new RuntimeException("addFirst(null) did not throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
        check(deque.size() == 0, "deque changed after rejected null element");
        check(deque.peekFirst() == null, "peekFirst not null after rejected null element");
    }

    static void testAddFirstOrderAndSize() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.addFirst("a");
        check("a".equals(deque.peekFirst()), "first element not a");
        check("a".equals(deque.peekLast()), "last element not a");
        check(deque.size() == 1, "size after one addFirst is not 1");

        deque.addFirst("b");
        check("b".equals(deque.peekFirst()), "first element not b after second addFirst");
        check("a".equals(deque.peekLast()), "last element not a after second addFirst");
        check(deque.size() == 2, "size after two addFirst is not 2");
    }

    static void testAddFirstAfterGrowth() {
        ArrayDeque<Integer> deque = new ArrayDeque<>(1);
        for (int i = 0; i < 100; i++) {
            deque.addFirst(i);
        }
        check(deque.size() == 100, "size after 100 addFirst is not 100");
        check(deque.peekFirst() == 99, "first element after growth is not 99");
        check(deque.peekLast() == 0, "last element after growth is not 0");
        for (int i = 99; i >= 0; i--) {
            Integer value = deque.pollFirst();
            check(value != null && value == i, "unexpected element order at " + i);
        }
        check(deque.isEmpty(), "deque not empty after draining");
    }
}
