/* @test    @summary Test removeLastOccurrence method of LinkedBlockingDeque    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testRemoveLastOccurrenceWithExistingElement();
        testRemoveLastOccurrenceWithNonExistingElement();
        testRemoveLastOccurrenceWithNull();
    }

    private static void testRemoveLastOccurrenceWithExistingElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("a");
        boolean removed = deque.removeLastOccurrence("a");
        assert removed : "Expected to remove last occurrence of 'a'";
        assert deque.size() == 2 : "Deque size should be 2 after removal";
        assert deque.peekLast().equals("b") : "Last element should be 'b'";
    }

    private static void testRemoveLastOccurrenceWithNonExistingElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("x");
        deque.add("y");
        boolean removed = deque.removeLastOccurrence("z");
        assert !removed : "Should not remove non-existing element";
        assert deque.size() == 2 : "Deque size should remain 2";
    }

    private static void testRemoveLastOccurrenceWithNull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("test");
        boolean removed = deque.removeLastOccurrence(null);
        assert !removed : "Should not remove null element";
        assert deque.size() == 1 : "Deque size should remain 1";
    }
}
