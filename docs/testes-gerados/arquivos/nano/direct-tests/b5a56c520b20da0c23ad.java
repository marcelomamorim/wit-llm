/*
 * @test
 * @summary Test the remove(Object o) method of ConcurrentLinkedDeque.
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testRemoveExistingElement();
        testRemoveNonExistingElement();
        testRemoveNullElement();
    }

    static void testRemoveExistingElement() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("element1");
        deque.add("element2");
        boolean removed = deque.remove("element1");
        if (!removed) {
            throw new AssertionError("Failed to remove existing element");
        }
        if (deque.contains("element1")) {
            throw new AssertionError("Element still present after removal");
        }
    }

    static void testRemoveNonExistingElement() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("element1");
        boolean removed = deque.remove("nonexistent");
        if (removed) {
            throw new AssertionError("Removed non-existing element");
        }
    }

    static void testRemoveNullElement() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("element1");
        boolean removed = deque.remove(null);
        if (removed) {
            throw new AssertionError("Removed null element which should not be present");
        }
        if (deque.contains(null)) {
            throw new AssertionError("Null element present after removal attempt");
        }
    }
}
