/*
 * @test
 * @summary Test for pollLast method in LinkedBlockingDeque
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantLock;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testPollLastEmptyDeque();
        testPollLastWithElements();
    }

    private static void testPollLastEmptyDeque() {
        LinkedBlockingDeque<Object> deque = new LinkedBlockingDeque<>();
        Object result = deque.pollLast();
        if (result != null) {
            throw new AssertionError("Expected null when polling from empty deque, but got: " + result);
        }
    }

    private static void testPollLastWithElements() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("first");
        deque.add("second");
        Object last = deque.pollLast();
        if (!"second".equals(last)) {
            throw new AssertionError("Expected 'second', but got: " + last);
        }
        if (deque.size() != 1 || !"first".equals(deque.peekLast())) {
            throw new AssertionError("Deque state invalid after pollLast: size=" + deque.size());
        }
    }
}
