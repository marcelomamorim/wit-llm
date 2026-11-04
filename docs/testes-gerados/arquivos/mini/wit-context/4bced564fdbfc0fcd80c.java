/*
 * @test
 * @summary Test LinkedBlockingDeque.contains(Object) method behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();

        // Test contains with null returns false
        if (deque.contains(null)) {
            throw new Error("contains(null) should return false");
        }

        // Test contains on empty deque returns false
        if (deque.contains("test")) {
            throw new Error("contains on empty deque should return false");
        }

        // Add an element and test contains returns true
        deque.add("test");
        if (!deque.contains("test")) {
            throw new Error("contains should return true for existing element");
        }

        // Test contains returns false for non-existing element
        if (deque.contains("absent")) {
            throw new Error("contains should return false for non-existing element");
        }

        // Test contains with different but equal object
        String s1 = new String("test");
        if (!deque.contains(s1)) {
            throw new Error("contains should return true for equal object");
        }

        System.out.println("All tests passed.");
    }
}
