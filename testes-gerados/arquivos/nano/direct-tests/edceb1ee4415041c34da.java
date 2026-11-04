/*
 * @test
 * @summary Test getLast() method of LinkedBlockingDeque for correct behavior.
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.NoSuchElementException;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testGetLastWithElements();
        testGetLastEmptyDeque();
    }

    static void testGetLastWithElements() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("first");
        deque.add("second");
        String last = deque.getLast();
        if (!"second".equals(last)) {
            throw new AssertionError("Expected 'second', but got: " + last);
        }
    }

    static void testGetLastEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.getLast();
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NoSuchElementException to be thrown for empty deque");
        }
    }
}
