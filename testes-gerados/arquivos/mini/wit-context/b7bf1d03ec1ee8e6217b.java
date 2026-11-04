/*
 * @test
 * @summary Test LinkedBlockingDeque.offer(E) for null argument and normal usage
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();

        // Test offer with null argument - expect NullPointerException
        try {
            deque.offer(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test offer with non-null argument - expect true (successful insertion)
        boolean offered = deque.offer("test");
        if (!offered) {
            throw new Error("Expected offer to return true for non-null element");
        }

        // Test that the element is present by polling
        String polled = deque.poll();
        if (!"test".equals(polled)) {
            throw new Error("Expected to poll the element just offered");
        }

        // Test offer with multiple elements to ensure no UnsupportedOperationException
        for (int i = 0; i < 10; i++) {
            if (!deque.offer("elem" + i)) {
                throw new Error("Offer returned false unexpectedly at iteration " + i);
            }
        }

        // Clear deque
        deque.clear();
    }
}
