/*
 * @test
 * @summary Test LinkedBlockingDeque.removeLastOccurrence behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();

        // Test removeLastOccurrence with null argument returns false
        if (deque.removeLastOccurrence(null) != false) {
            throw new Error("removeLastOccurrence(null) should return false");
        }

        // Test removeLastOccurrence on empty deque returns false
        if (deque.removeLastOccurrence("test") != false) {
            throw new Error("removeLastOccurrence on empty deque should return false");
        }

        // Add elements and test removeLastOccurrence removes last occurrence
        deque.add("a");
        deque.add("b");
        deque.add("a");

        // Remove last occurrence of "a" should return true
        if (deque.removeLastOccurrence("a") != true) {
            throw new Error("removeLastOccurrence should return true when element removed");
        }

        // After removal, last element should not be "a" anymore
        String last = deque.peekLast();
        if ("a".equals(last)) {
            throw new Error("Last element should not be 'a' after removeLastOccurrence");
        }

        // Remove last occurrence of "a" again should return true (removes first "a")
        if (deque.removeLastOccurrence("a") != true) {
            throw new Error("removeLastOccurrence should return true when element removed");
        }

        // Now no "a" remains, removeLastOccurrence should return false
        if (deque.removeLastOccurrence("a") != false) {
            throw new Error("removeLastOccurrence should return false when element not found");
        }

        // Remove last occurrence of "b" should return true
        if (deque.removeLastOccurrence("b") != true) {
            throw new Error("removeLastOccurrence should return true when element removed");
        }

        // Now deque is empty, removeLastOccurrence should return false
        if (deque.removeLastOccurrence("b") != false) {
            throw new Error("removeLastOccurrence should return false when element not found");
        }

        System.out.println("All tests passed.");
    }
}
