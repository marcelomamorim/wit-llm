/*
 * @test
 * @summary Testa o método remove de LinkedBlockingQueue para entradas válidas e inválidas.
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.NoSuchElementException;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testRemoveExistingElement();
        testRemoveNonExistingElement();
        testRemoveNullElement();
    }

    static void testRemoveExistingElement() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        String element = "test";
        queue.offer(element);
        boolean removed = queue.remove(element);
        if (!removed) {
            throw new AssertionError("Expected to remove existing element, but did not.");
        }
        if (queue.contains(element)) {
            throw new AssertionError("Queue should not contain the element after removal.");
        }
    }

    static void testRemoveNonExistingElement() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.offer("a");
        boolean removed = queue.remove("b");
        if (removed) {
            throw new AssertionError("Removing non-existing element should return false.");
        }
    }

    static void testRemoveNullElement() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.offer("a");
        boolean removed = queue.remove(null);
        if (removed) {
            throw new AssertionError("Removing null should return false.");
        }
        if (!queue.contains("a")) {
            throw new AssertionError("Queue should still contain the original element.");
        }
    }
}
