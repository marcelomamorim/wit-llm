/*
 * @test
 * @summary Test for LinkedBlockingDeque.poll() method
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testPollReturnsNullOnEmptyDeque();
        testPollReturnsElementWhenPresent();
    }

    private static void testPollReturnsNullOnEmptyDeque() {
        LinkedBlockingDeque<Object> deque = new LinkedBlockingDeque<>();
        Object result = deque.poll();
        if (result != null) {
            throw new AssertionError("Expected null when polling empty deque, but got: " + result);
        }
    }

    private static void testPollReturnsElementWhenPresent() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.offer("element");
        Object result = deque.poll();
        if (!"element".equals(result)) {
            throw new AssertionError("Expected 'element', but got: " + result);
        }
        if (!deque.isEmpty()) {
            throw new AssertionError("Deque should be empty after poll.");
        }
    }
}
