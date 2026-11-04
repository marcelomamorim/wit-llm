/* @test    @summary Test clear() method of LinkedBlockingDeque for correct behavior and state reset    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.NoSuchElementException;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testClearOnEmptyDeque();
        testClearOnNonEmptyDeque();
        testClearMultipleTimes();
    }

    static void testClearOnEmptyDeque() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.clear();
        assert deque.isEmpty() : "Deque should be empty after clear()";
        try {
            deque.element();
            assert false : "element() should throw NoSuchElementException on empty deque";
        } catch (NoSuchElementException e) {
            // expected
        }
    }

    static void testClearOnNonEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");
        deque.clear();
        assert deque.isEmpty() : "Deque should be empty after clear()";
        try {
            deque.peek();
            assert deque.peek() == null : "peek() should return null after clear()";
        } catch (Exception e) {
            assert false : "peek() should not throw after clear()";
        }
    }

    static void testClearMultipleTimes() {
        LinkedBlockingDeque<Double> deque = new LinkedBlockingDeque<>();
        deque.add(1.1);
        deque.add(2.2);
        deque.clear();
        deque.clear();
        deque.clear();
        assert deque.isEmpty() : "Deque should remain empty after multiple clears";
    }
}
