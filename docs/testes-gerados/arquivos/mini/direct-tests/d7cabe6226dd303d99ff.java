/*
 * @test
 * @summary Test PriorityBlockingQueue.contains(Object) method behavior
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();

        // Test contains on empty queue
        if (queue.contains("test")) {
            throw new Error("contains returned true on empty queue");
        }

        // Add element and test contains
        queue.add("element1");
        if (!queue.contains("element1")) {
            throw new Error("contains returned false for existing element");
        }

        // Test contains for element not present
        if (queue.contains("element2")) {
            throw new Error("contains returned true for non-existing element");
        }

        // Test contains with null (should return false, no NullPointerException)
        if (queue.contains(null)) {
            throw new Error("contains returned true for null element");
        }

        // Add multiple elements and test contains
        queue.add("element2");
        queue.add("element3");
        if (!queue.contains("element2") || !queue.contains("element3")) {
            throw new Error("contains returned false for existing elements");
        }

        System.out.println("PASS");
    }
}
