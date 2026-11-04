/*
 * @test
 * @summary Test LinkedBlockingDeque.remove(Object) method behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();

        // Test remove on empty deque returns false
        boolean removedEmpty = deque.remove("test");
        if (removedEmpty) {
            throw new RuntimeException("remove returned true on empty deque");
        }

        // Add element and remove it
        deque.add("element");
        boolean removedPresent = deque.remove("element");
        if (!removedPresent) {
            throw new RuntimeException("remove returned false for present element");
        }

        // Remove again should return false
        boolean removedAgain = deque.remove("element");
        if (removedAgain) {
            throw new RuntimeException("remove returned true for non-present element");
        }

        // Remove null element should return false (no exception expected)
        boolean removedNull = deque.remove(null);
        if (removedNull) {
            throw new RuntimeException("remove returned true for null element");
        }

        System.out.println("All tests passed.");
    }
}
