/*
 * @test
 * @summary Test LinkedBlockingDeque.removeFirstOccurrence behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();

        // Test removeFirstOccurrence with null argument returns false
        if (deque.removeFirstOccurrence(null) != false) {
            throw new AssertionError("removeFirstOccurrence(null) should return false");
        }

        // Test removeFirstOccurrence on empty deque returns false
        if (deque.removeFirstOccurrence("test") != false) {
            throw new AssertionError("removeFirstOccurrence on empty deque should return false");
        }

        // Add elements and test removeFirstOccurrence removes first matching element
        deque.add("a");
        deque.add("b");
        deque.add("a");

        boolean removed = deque.removeFirstOccurrence("a");
        if (!removed) {
            throw new AssertionError("removeFirstOccurrence should remove first occurrence of 'a'");
        }

        // After removal, first element should not be "a" anymore
        String first = deque.peekFirst();
        if ("a".equals(first)) {
            throw new AssertionError("First element should not be 'a' after removal");
        }

        // Removing an element not present returns false
        if (deque.removeFirstOccurrence("z") != false) {
            throw new AssertionError("removeFirstOccurrence of non-existent element should return false");
        }

        System.out.println("All tests passed.");
    }
}
