/*
 * @test
 * @summary Test LinkedBlockingDeque.poll() method behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();

        // Test poll() on empty deque returns null
        if (deque.poll() != null) {
            throw new AssertionError("poll() on empty deque should return null");
        }

        // Test poll() after adding one element returns that element
        String element = "testElement";
        deque.add(element);
        String polled = deque.poll();
        if (!element.equals(polled)) {
            throw new AssertionError("poll() should return the element added");
        }

        // After poll, deque should be empty again
        if (deque.poll() != null) {
            throw new AssertionError("poll() after removing element should return null");
        }

        System.out.println("All tests passed.");
    }
}
