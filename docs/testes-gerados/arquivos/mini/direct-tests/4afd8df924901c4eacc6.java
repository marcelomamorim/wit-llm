/*
 * @test
 * @summary Test LinkedBlockingDeque.peekFirst() behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();

        // Test peekFirst on empty deque returns null
        if (deque.peekFirst() != null) {
            throw new AssertionError("peekFirst() on empty deque should return null");
        }

        // Test peekFirst on deque with one element returns that element
        String element = "test";
        deque.addFirst(element);
        String peeked = deque.peekFirst();
        if (!element.equals(peeked)) {
            throw new AssertionError("peekFirst() should return the first element added");
        }

        // Test peekFirst does not remove the element
        String peekedAgain = deque.peekFirst();
        if (!element.equals(peekedAgain)) {
            throw new AssertionError("peekFirst() should not remove the element");
        }

        // Test peekFirst on deque with multiple elements returns the first
        String secondElement = "second";
        deque.addLast(secondElement);
        String peekedMultiple = deque.peekFirst();
        if (!element.equals(peekedMultiple)) {
            throw new AssertionError("peekFirst() should return the first element in deque with multiple elements");
        }

        System.out.println("All tests passed.");
    }
}
