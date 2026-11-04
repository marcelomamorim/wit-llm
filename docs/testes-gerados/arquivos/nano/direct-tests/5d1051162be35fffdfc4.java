/* @test    @summary Test removeLastOccurrence method of ConcurrentLinkedDeque    @run main ConcurrentLinkedDequeWitupTest */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.Objects;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testRemoveLastOccurrenceWithExistingElement();
        testRemoveLastOccurrenceWithNonExistingElement();
        testRemoveLastOccurrenceWithNull();
    }

    private static void testRemoveLastOccurrenceWithExistingElement() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("a");
        boolean removed = deque.removeLastOccurrence("a");
        assert removed : "Expected to remove last occurrence of 'a'";
        assert deque.size() == 2 : "Deque size should be 2 after removal";
        assert deque.contains("a") : "Deque should still contain 'a'";
        assert deque.peekLast().equals("b") : "Last element should be 'b'";
    }

    private static void testRemoveLastOccurrenceWithNonExistingElement() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("x");
        deque.add("y");
        boolean removed = deque.removeLastOccurrence("z");
        assert !removed : "Expected not to remove any element";
        assert deque.size() == 2 : "Deque size should remain 2";
    }

    private static void testRemoveLastOccurrenceWithNull() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("test");
        try {
            deque.removeLastOccurrence(null);
            assert false : "Expected NullPointerException when passing null";
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
