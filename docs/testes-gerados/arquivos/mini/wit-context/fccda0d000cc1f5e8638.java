/*
 * @test
 * @summary Test LinkedBlockingDeque.push(E) for null argument and normal usage
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();

        // Test push with non-null element: should succeed without exception
        String element = "test";
        deque.push(element);
        if (!element.equals(deque.peekFirst())) {
            throw new AssertionError("push did not add the element at the front");
        }

        // Test push with null element: should throw NullPointerException
        try {
            deque.push(null);
            throw new AssertionError("push(null) did not throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
