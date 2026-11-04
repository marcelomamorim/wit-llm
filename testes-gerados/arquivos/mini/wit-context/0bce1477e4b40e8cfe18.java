/*
 * @test
 * @summary Test LinkedBlockingDeque.offerFirst(E) for null input and normal insertion
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();

        // Test that offerFirst throws NullPointerException when null is passed
        boolean npeThrown = false;
        try {
            deque.offerFirst(null);
        } catch (NullPointerException ex) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("offerFirst(null) did not throw NullPointerException");
        }

        // Test normal insertion returns true and element is retrievable
        String element = "testElement";
        boolean offered = deque.offerFirst(element);
        if (!offered) {
            throw new AssertionError("offerFirst returned false for valid element");
        }

        String first = deque.peekFirst();
        if (!element.equals(first)) {
            throw new AssertionError("peekFirst did not return the inserted element");
        }

        // Test that the deque size is 1 after insertion
        if (deque.size() != 1) {
            throw new AssertionError("Deque size expected to be 1 after one insertion, but was " + deque.size());
        }
    }
}
