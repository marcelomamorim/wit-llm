/* @test    @summary Test pollLast() method of LinkedBlockingDeque for correct behavior    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.NoSuchElementException;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testPollLastEmptyDeque();
        testPollLastWithElements();
    }

    static void testPollLastEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        String result = deque.pollLast();
        if (result != null) {
            throw new AssertionError("Expected null when polling last from empty deque, but got: " + result);
        }
    }

    static void testPollLastWithElements() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("first");
        deque.add("second");
        String last = deque.pollLast();
        if (!"second".equals(last)) {
            throw new AssertionError("Expected 'second', but got: " + last);
        }
        String remaining = deque.peekLast();
        if (!"first".equals(remaining)) {
            throw new AssertionError("Expected remaining element 'first', but got: " + remaining);
        }
    }
}
