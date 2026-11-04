/*
 * @test    @summary Test ArrayDeque.remove() method for correct behavior and exception handling
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testRemoveOnNonEmptyDeque();
        testRemoveOnEmptyDeque();
    }

    static void testRemoveOnNonEmptyDeque() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        try {
            deque.remove(); // should remove element 1
            assert deque.size() == 2 : "Size should be 2 after one remove";
            assert deque.peek() == 2 : "Next element should be 2";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception during remove on non-empty deque", e);
        }
    }

    static void testRemoveOnEmptyDeque() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        try {
            deque.remove(); // should throw NoSuchElementException
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (java.util.NoSuchElementException e) {
            // expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
