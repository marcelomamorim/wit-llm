/*
 * @test
 * @summary Verifies LinkedBlockingDeque.clear() removes all elements, is safe on an empty deque, and restores remaining capacity
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testClearNonEmptyDeque();
        testClearEmptyDeque();
        testClearRestoresRemainingCapacity();
        testDequeUsableAfterClear();
    }

    static void testClearNonEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("a");
        deque.addFirst("b");
        deque.addLast("c");
        check(deque.size() == 3, "precondition: size should be 3");

        deque.clear();

        check(deque.size() == 0, "size after clear should be 0");
        check(deque.isEmpty(), "deque should be empty after clear");
        check(deque.peekFirst() == null, "peekFirst should be null after clear");
        check(deque.peekLast() == null, "peekLast should be null after clear");
        check(!deque.contains("a"), "contains(a) should be false after clear");
        check(!deque.iterator().hasNext(), "iterator should have no elements after clear");
    }

    static void testClearEmptyDeque() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.clear();
        check(deque.isEmpty(), "clear on empty deque should leave it empty");
        check(deque.size() == 0, "size of empty deque after clear should be 0");

        deque.clear();
        check(deque.size() == 0, "repeated clear should keep deque empty");
    }

    static void testClearRestoresRemainingCapacity() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(3);
        deque.add(1);
        deque.add(2);
        check(deque.remainingCapacity() == 1, "precondition: remaining capacity should be 1");

        deque.clear();

        check(deque.remainingCapacity() == 3, "remaining capacity should be restored to 3 after clear");
        check(deque.offerLast(10), "offerLast should succeed after clear on bounded deque");
        check(deque.offerLast(11), "offerLast should succeed up to capacity after clear");
        check(deque.offerLast(12), "offerLast should succeed up to capacity after clear");
        check(!deque.offerLast(13), "offerLast should fail when capacity is reached after clear");
    }

    static void testDequeUsableAfterClear() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("x");
        deque.clear();

        deque.addFirst("y");
        deque.addLast("z");
        check(deque.size() == 2, "size should be 2 after adding post-clear");
        check("y".equals(deque.peekFirst()), "first element should be y after clear and reinsertion");
        check("z".equals(deque.peekLast()), "last element should be z after clear and reinsertion");
        check(!deque.contains("x"), "cleared element x should not be present");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
