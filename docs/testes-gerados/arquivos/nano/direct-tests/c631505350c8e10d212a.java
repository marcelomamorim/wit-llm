/* @test    @summary Test peek() method of LinkedBlockingDeque for correct behavior    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testPeekEmptyDeque();
        testPeekWithElements();
    }

    static void testPeekEmptyDeque() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        assert deque.peek() == null : "peek() on empty deque should return null";
    }

    static void testPeekWithElements() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.offer("first");
        deque.offer("second");
        assert "first".equals(deque.peek()) : "peek() should return the first element";
        deque.poll();
        assert "second".equals(deque.peek()) : "peek() should return the new first element after poll";
    }
}
