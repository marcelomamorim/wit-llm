/* @test    @summary Test the pop() method of LinkedBlockingDeque for correct behavior and exception handling.    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.NoSuchElementException;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testPopOnEmptyDeque();
        testPopOnNonEmptyDeque();
    }

    static void testPopOnEmptyDeque() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.pop();
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NoSuchElementException when popping from empty deque");
        }
    }

    static void testPopOnNonEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("first");
        String result = deque.pop();
        if (!"first".equals(result)) {
            throw new AssertionError("pop() did not return the expected element");
        }
        if (deque.size() != 0) {
            throw new AssertionError("Deque should be empty after pop()");
        }
    }
}
