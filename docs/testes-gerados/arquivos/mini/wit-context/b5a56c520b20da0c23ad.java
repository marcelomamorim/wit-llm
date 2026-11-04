/*
 * @test
 * @summary Test ConcurrentLinkedDeque.remove(Object) for null argument and normal removal
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;

public class ConcurrentLinkedDequeWitupTest {
    public static void main(String[] args) {
        testRemoveNull();
        testRemoveExistingElement();
        testRemoveNonExistingElement();
    }

    private static void testRemoveNull() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        boolean npeThrown = false;
        try {
            deque.remove(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when removing null");
        }
    }

    private static void testRemoveExistingElement() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");
        boolean removed = deque.remove("b");
        if (!removed) {
            throw new AssertionError("Expected to remove existing element 'b'");
        }
        if (deque.contains("b")) {
            throw new AssertionError("Element 'b' should no longer be in deque");
        }
    }

    private static void testRemoveNonExistingElement() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("x");
        deque.add("y");
        boolean removed = deque.remove("z");
        if (removed) {
            throw new AssertionError("Expected remove to return false for non-existing element 'z'");
        }
    }
}
