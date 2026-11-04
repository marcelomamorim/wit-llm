/*
 * @test
 * @summary Test ArrayDeque push method for correct behavior
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testPushAddsElementAtFront();
        testPushWithNullElement();
    }

    static void testPushAddsElementAtFront() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(1);
        deque.add(2);
        deque.push(3);
        if (deque.peekFirst() != 3) {
            throw new AssertionError("push did not add element at front");
        }
        if (deque.size() != 3) {
            throw new AssertionError("Size mismatch after push");
        }
    }

    static void testPushWithNullElement() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        try {
            deque.push(null);
            if (deque.peekFirst() != null) {
                throw new AssertionError("Null element not added correctly");
            }
            if (deque.size() != 1) {
                throw new AssertionError("Size mismatch after pushing null");
            }
        } catch (NullPointerException e) {
            throw new AssertionError("ArrayDeque should allow null elements", e);
        }
    }
}
