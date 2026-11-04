/*
 * @test
 * @summary Black-box checks for ArrayDeque.push(E), which delegates to addFirst(E)
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.Iterator;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testPushPlacesElementAtHead();
        testPushMultipleIsLifo();
        testPushAfterPollFirst();
        testPushGrowsBeyondInitialCapacity();
        testPushOnZeroCapacityDeque();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testPushPlacesElementAtHead() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.push("a");
        check(deque.size() == 1, "size after one push should be 1");
        check("a".equals(deque.peekFirst()), "peekFirst should return pushed element");
        check("a".equals(deque.peekLast()), "peekLast should return the only element");
        check(!deque.isEmpty(), "deque should not be empty after push");
    }

    static void testPushMultipleIsLifo() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.push(1);
        deque.push(2);
        deque.push(3);
        check(deque.size() == 3, "size after three pushes should be 3");
        check(deque.peek() == 3, "peek should return last pushed element");
        check(deque.pop() == 3, "pop should return 3");
        check(deque.pop() == 2, "pop should return 2");
        check(deque.pop() == 1, "pop should return 1");
        check(deque.isEmpty(), "deque should be empty after popping all elements");
    }

    static void testPushAfterPollFirst() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.addLast("x");
        deque.addLast("y");
        check("x".equals(deque.pollFirst()), "pollFirst should return x");
        deque.push("z");
        check("z".equals(deque.peekFirst()), "push should place z at head");
        check("y".equals(deque.peekLast()), "tail should remain y");
        check(deque.size() == 2, "size should be 2");
    }

    static void testPushGrowsBeyondInitialCapacity() {
        ArrayDeque<Integer> deque = new ArrayDeque<>(1);
        int n = 100;
        for (int i = 0; i < n; i++) {
            deque.push(i);
        }
        check(deque.size() == n, "size should equal number of pushes");
        check(deque.peekFirst() == n - 1, "head should be last pushed value");
        check(deque.peekLast() == 0, "tail should be first pushed value");
        Iterator<Integer> it = deque.iterator();
        int expected = n - 1;
        while (it.hasNext()) {
            int actual = it.next();
            check(actual == expected, "iteration order should be head to tail");
            expected--;
        }
        check(expected == -1, "iterator should visit all elements");
    }

    static void testPushOnZeroCapacityDeque() {
        ArrayDeque<Integer> deque = new ArrayDeque<>(0);
        deque.push(7);
        deque.push(8);
        check(deque.size() == 2, "size should be 2 after two pushes");
        check(deque.pop() == 8, "pop should return 8");
        check(deque.pop() == 7, "pop should return 7");
    }
}
