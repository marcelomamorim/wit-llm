/* @test    @summary Test the remove(Object o) method of LinkedBlockingDeque for correct removal behavior.    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testRemoveExistingElement();
        testRemoveNonExistingElement();
        testRemoveNullElement();
    }

    static void testRemoveExistingElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
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
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("element1");
        boolean removed = deque.remove("nonexistent");
        if (removed) {
            throw new AssertionError("Removed non-existing element");
        }
    }

    static void testRemoveNullElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("element1");
        boolean removed = deque.remove(null);
        if (removed) {
            throw new AssertionError("Removed null element which should not be present");
        }
    }
}
