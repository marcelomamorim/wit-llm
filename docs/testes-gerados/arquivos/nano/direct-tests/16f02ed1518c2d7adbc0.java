/* @test    @summary Test for LinkedBlockingDeque.remove() method    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.NoSuchElementException;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testRemoveWithElement();
        testRemoveWithoutElement();
        testRemoveEmptyDeque();
    }

    private static void testRemoveWithElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("element");
        deque.remove();
        assert deque.isEmpty() : "Deque should be empty after removal";
    }

    private static void testRemoveWithoutElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.remove();
            assert false : "Expected IllegalStateException when removing from empty deque";
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }

    private static void testRemoveEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.remove();
            assert false : "Expected IllegalStateException when removing from empty deque";
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }
}
