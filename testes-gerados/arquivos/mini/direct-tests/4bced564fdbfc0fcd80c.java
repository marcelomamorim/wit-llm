/*
 * @test
 * @summary Test LinkedBlockingDeque.contains(Object) method behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();

        // Test contains on empty deque returns false
        if (deque.contains("test")) {
            throw new Error("Fail: contains should return false on empty deque");
        }

        // Test contains with null returns false
        if (deque.contains(null)) {
            throw new Error("Fail: contains should return false for null argument");
        }

        // Add elements and test contains returns true for present elements
        deque.add("one");
        deque.add("two");
        deque.add("three");

        if (!deque.contains("one")) {
            throw new Error("Fail: contains should return true for element 'one'");
        }
        if (!deque.contains("two")) {
            throw new Error("Fail: contains should return true for element 'two'");
        }
        if (!deque.contains("three")) {
            throw new Error("Fail: contains should return true for element 'three'");
        }

        // Test contains returns false for element not present
        if (deque.contains("four")) {
            throw new Error("Fail: contains should return false for element not present");
        }

        // Test contains with an object equal to an element (equals method)
        String s = new String("two"); // different instance but equals
        if (!deque.contains(s)) {
            throw new Error("Fail: contains should return true for equal element");
        }

        System.out.println("PASS");
    }
}
