/* @test    @summary Test peekLast() method of LinkedBlockingDeque for correct behavior and null handling    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testPeekLastWithElements();
        testPeekLastEmptyDeque();
    }

    static void testPeekLastWithElements() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.offerLast("element1");
        deque.offerLast("element2");
        String last = deque.peekLast();
        if (last == null || !last.equals("element2")) {
            throw new AssertionError("Expected last element to be 'element2', but was: " + last);
        }
    }

    static void testPeekLastEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        String last = deque.peekLast();
        if (last != null) {
            throw new AssertionError("Expected null for empty deque, but got: " + last);
        }
    }
}
