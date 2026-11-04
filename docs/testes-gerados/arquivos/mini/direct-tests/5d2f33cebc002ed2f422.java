/*
 * @test
 * @summary Test LinkedBlockingDeque.poll() method behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        testPollEmptyDeque();
        testPollNonEmptyDeque();
    }

    static void testPollEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        String result = deque.poll();
        if (result != null) {
            throw new AssertionError("Expected null when polling empty deque, got: " + result);
        }
    }

    static void testPollNonEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("first");
        deque.add("second");
        String firstPolled = deque.poll();
        if (!"first".equals(firstPolled)) {
            throw new AssertionError("Expected 'first' when polling, got: " + firstPolled);
        }
        String secondPolled = deque.poll();
        if (!"second".equals(secondPolled)) {
            throw new AssertionError("Expected 'second' when polling, got: " + secondPolled);
        }
        String thirdPolled = deque.poll();
        if (thirdPolled != null) {
            throw new AssertionError("Expected null after polling all elements, got: " + thirdPolled);
        }
    }
}
