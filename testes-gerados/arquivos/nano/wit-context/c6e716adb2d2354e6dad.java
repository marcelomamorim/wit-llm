/*
 * @test
 * @summary Test for LinkedBlockingDeque.pop() method
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testPopNormal();
        testPopEmpty();
    }

    private static void testPopNormal() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(1);
        deque.add(2);
        Integer result = deque.pop();
        if (result == null || result != 1) {
            throw new AssertionError("Expected pop() to return 1, but got: " + result);
        }
        if (deque.size() != 1) {
            throw new AssertionError("Expected size 1 after pop(), but got: " + deque.size());
        }
    }

    private static void testPopEmpty() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        try {
            Integer result = deque.pop();
            if (result != null) {
                throw new AssertionError("Expected pop() on empty deque to return null, but got: " + result);
            }
        } catch (NoSuchElementException e) {
            // Expected exception, test passes
        }
    }
}
