/*
 * @test
 * @summary Test LinkedBlockingDeque.pollLast() for normal and empty cases
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();

        // Test pollLast on empty deque returns null
        if (deque.pollLast() != null) {
            throw new AssertionError("pollLast on empty deque should return null");
        }

        // Add one element and pollLast should return it
        String element = "testElement";
        deque.add(element);
        String polled = deque.pollLast();
        if (!element.equals(polled)) {
            throw new AssertionError("pollLast did not return the last element added");
        }

        // After pollLast, deque should be empty again
        if (deque.pollLast() != null) {
            throw new AssertionError("pollLast on deque after removal should return null");
        }

        System.out.println("All tests passed.");
    }
}
