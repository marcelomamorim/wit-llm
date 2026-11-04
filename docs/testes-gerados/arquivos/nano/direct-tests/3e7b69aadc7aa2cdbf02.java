/* @test    @summary Test removeLast() method of LinkedBlockingDeque for correct behavior and exception handling    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.NoSuchElementException;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testRemoveLastWithElements();
        testRemoveLastWhenEmpty();
    }

    static void testRemoveLastWithElements() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("first");
        deque.add("second");
        String last = deque.removeLast();
        assert "second".equals(last) : "Expected 'second', got " + last;
        assert deque.size() == 1 : "Expected size 1, got " + deque.size();
        last = deque.removeLast();
        assert "first".equals(last) : "Expected 'first', got " + last;
        assert deque.isEmpty() : "Deque should be empty";
    }

    static void testRemoveLastWhenEmpty() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.removeLast();
            assert false : "Expected NoSuchElementException";
        } catch (NoSuchElementException e) {
            // expected
        }
    }
}
