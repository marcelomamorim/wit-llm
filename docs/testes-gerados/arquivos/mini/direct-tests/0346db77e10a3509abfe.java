/*
 * @test
 * @summary Test LinkedBlockingDeque remove(Object) method delegates to removeFirstOccurrence
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();

        // Test remove on empty deque returns false
        if (deque.remove("test")) {
            throw new Error("remove returned true on empty deque");
        }

        // Add elements and test remove returns true when element present
        deque.add("a");
        deque.add("b");
        deque.add("c");

        if (!deque.remove("b")) {
            throw new Error("remove returned false for existing element");
        }

        // After removal, element should no longer be present
        if (deque.contains("b")) {
            throw new Error("element 'b' still present after remove");
        }

        // Remove element not present returns false
        if (deque.remove("z")) {
            throw new Error("remove returned true for non-existing element");
        }

        // Remove null element when null not present returns false
        if (deque.remove(null)) {
            throw new Error("remove returned true for null element not present");
        }

        // Add null element and remove it
        deque.add(null);
        if (!deque.remove(null)) {
            throw new Error("remove returned false for existing null element");
        }

        // Confirm null element removed
        if (deque.contains(null)) {
            throw new Error("null element still present after remove");
        }
    }
}
