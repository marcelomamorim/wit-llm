/* @test    @summary Test the poll() method of LinkedBlockingDeque for correct behavior.    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testPollReturnsNullOnEmpty();
        testPollReturnsElementOnNonEmpty();
    }

    static void testPollReturnsNullOnEmpty() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        assert deque.poll() == null : "Expected null when polling empty deque";
    }

    static void testPollReturnsElementOnNonEmpty() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.offer("element");
        String result = deque.poll();
        assert "element".equals(result) : "Expected 'element', got " + result;
        assert deque.isEmpty() : "Deque should be empty after poll";
    }
}
