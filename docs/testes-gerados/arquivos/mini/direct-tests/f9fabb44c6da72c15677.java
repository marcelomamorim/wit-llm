/*
 * @test
 * @summary Test LinkedBlockingQueue.contains(Object) method
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

        // Test contains returns false for null argument
        if (queue.contains(null)) {
            throw new Error("contains(null) should return false");
        }

        // Test contains returns false for element not in queue
        if (queue.contains("absent")) {
            throw new Error("contains should return false for element not present");
        }

        // Add element and test contains returns true
        queue.put("test");
        if (!queue.contains("test")) {
            throw new Error("contains should return true for present element");
        }

        // Add another element and test contains for both
        queue.put("another");
        if (!queue.contains("another")) {
            throw new Error("contains should return true for second present element");
        }
        if (!queue.contains("test")) {
            throw new Error("contains should still return true for first element");
        }

        // Test contains returns false for different but equal object
        // Using new String to ensure different instance
        if (!queue.contains(new String("test"))) {
            throw new Error("contains should return true for equal object");
        }

        System.out.println("PASS");
    }
}
