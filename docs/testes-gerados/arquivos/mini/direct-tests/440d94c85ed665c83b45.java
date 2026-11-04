/*
 * @test
 * @summary Test LinkedBlockingDeque.pollLast() method behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        testPollLastEmptyDeque();
        testPollLastNonEmptyDeque();
    }

    static void testPollLastEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        String result = deque.pollLast();
        if (result != null) {
            throw new AssertionError("Expected null when polling last from empty deque, got: " + result);
        }
    }

    static void testPollLastNonEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.addFirst("first");
        deque.addLast("last");

        String last = deque.pollLast();
        if (!"last".equals(last)) {
            throw new AssertionError("Expected 'last' from pollLast(), got: " + last);
        }

        String nextLast = deque.pollLast();
        if (!"first".equals(nextLast)) {
            throw new AssertionError("Expected 'first' from pollLast() after removing last, got: " + nextLast);
        }

        String emptyPoll = deque.pollLast();
        if (emptyPoll != null) {
            throw new AssertionError("Expected null from pollLast() on empty deque, got: " + emptyPoll);
        }
    }
}
