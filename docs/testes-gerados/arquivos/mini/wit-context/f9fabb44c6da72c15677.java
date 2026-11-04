/*
 * @test
 * @summary Test LinkedBlockingQueue.contains(Object) method behavior
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

        // Test contains(null) returns false without exception
        if (queue.contains(null)) {
            throw new Error("contains(null) should return false");
        }

        // Test contains on empty queue returns false
        if (queue.contains("test")) {
            throw new Error("contains on empty queue should return false");
        }

        // Add element and test contains returns true
        queue.add("test");
        if (!queue.contains("test")) {
            throw new Error("contains should return true for present element");
        }

        // Test contains returns false for absent element
        if (queue.contains("absent")) {
            throw new Error("contains should return false for absent element");
        }

        System.out.println("All LinkedBlockingQueue.contains tests passed.");
    }
}
