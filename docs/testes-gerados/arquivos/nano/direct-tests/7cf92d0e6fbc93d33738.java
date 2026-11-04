/* @test    @summary Test removeFirstOccurrence method of LinkedBlockingDeque    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testRemoveFirstOccurrenceWithExistingElement();
        testRemoveFirstOccurrenceWithNonExistingElement();
        testRemoveFirstOccurrenceWithNull();
    }

    private static void testRemoveFirstOccurrenceWithExistingElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("element1");
        deque.add("element2");
        deque.add("element3");
        boolean removed = deque.removeFirstOccurrence("element2");
        assert removed : "Expected to remove existing element";
        assert !deque.contains("element2") : "Element should be removed from deque";
    }

    private static void testRemoveFirstOccurrenceWithNonExistingElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("element1");
        deque.add("element3");
        boolean removed = deque.removeFirstOccurrence("element2");
        assert !removed : "Expected not to remove non-existing element";
        assert deque.contains("element1") : "Element1 should still be in deque";
        assert deque.contains("element3") : "Element3 should still be in deque";
    }

    private static void testRemoveFirstOccurrenceWithNull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("element1");
        boolean removed = deque.removeFirstOccurrence(null);
        assert !removed : "Expected not to remove null element";
        assert deque.contains("element1") : "Element1 should still be in deque";
    }
}
