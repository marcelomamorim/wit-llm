/*
 * @test
 * @summary Test LinkedBlockingDeque.add(E) for null and non-null elements
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();

        // Test adding a non-null element: should return true
        String element = "test";
        boolean added = deque.add(element);
        if (!added) {
            throw new AssertionError("add(E) should return true when adding non-null element");
        }

        // Test adding null element: should throw NullPointerException
        try {
            deque.add(null);
            throw new AssertionError("add(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
