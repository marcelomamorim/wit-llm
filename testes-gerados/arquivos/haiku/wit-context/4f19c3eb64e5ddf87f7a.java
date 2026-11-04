/*
 * @test
 * @summary Witup-generated behavioral checks for LinkedBlockingDeque.pollFirst
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        emptyDequeReturnsNull();
        returnsHeadElementsInOrder();
        pollFirstFreesCapacityOnBoundedDeque();
    }

    static void emptyDequeReturnsNull() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        check(deque.pollFirst() == null, "pollFirst on empty deque must return null");
        check(deque.size() == 0, "deque must remain empty after pollFirst");
    }

    static void returnsHeadElementsInOrder() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.offerLast(Integer.valueOf(1));
        deque.offerLast(Integer.valueOf(2));
        check(Integer.valueOf(1).equals(deque.pollFirst()), "first pollFirst must return first inserted element");
        check(Integer.valueOf(2).equals(deque.pollFirst()), "second pollFirst must return next element");
        check(deque.pollFirst() == null, "pollFirst after draining must return null");

        deque.offerFirst(Integer.valueOf(3));
        check(Integer.valueOf(3).equals(deque.pollFirst()), "pollFirst must return element added at head");
        check(deque.pollFirst() == null, "deque must be empty again");
    }

    static void pollFirstFreesCapacityOnBoundedDeque() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(1);
        check(deque.offerLast(Integer.valueOf(10)), "offerLast must succeed when capacity is free");
        check(!deque.offerLast(Integer.valueOf(11)), "offerLast must fail when deque is full");
        check(Integer.valueOf(10).equals(deque.pollFirst()), "pollFirst must return the stored element");
        check(deque.offerLast(Integer.valueOf(11)), "offerLast must succeed after pollFirst frees capacity");
        check(Integer.valueOf(11).equals(deque.pollFirst()), "pollFirst must return the newly added element");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
