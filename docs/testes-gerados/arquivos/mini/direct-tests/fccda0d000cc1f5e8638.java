/*
 * @test
 * @summary Test LinkedBlockingDeque.push(E) method behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.NoSuchElementException;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        testPushAddsElementAtFront();
        testPushNullThrowsNPE();
    }

    static void testPushAddsElementAtFront() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.push("first");
        deque.push("second");

        // push adds elements at front, so "second" is first element
        String first = deque.peekFirst();
        if (!"second".equals(first)) {
            throw new AssertionError("Expected 'second' at front but got: " + first);
        }

        // The last element should be "first"
        String last = deque.peekLast();
        if (!"first".equals(last)) {
            throw new AssertionError("Expected 'first' at last but got: " + last);
        }

        // pop elements to verify order
        String pop1 = deque.pop();
        if (!"second".equals(pop1)) {
            throw new AssertionError("Expected pop to return 'second' but got: " + pop1);
        }
        String pop2 = deque.pop();
        if (!"first".equals(pop2)) {
            throw new AssertionError("Expected pop to return 'first' but got: " + pop2);
        }

        if (!deque.isEmpty()) {
            throw new AssertionError("Deque should be empty after popping all elements");
        }
    }

    static void testPushNullThrowsNPE() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.push(null);
            throw new AssertionError("Expected NullPointerException when pushing null");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
