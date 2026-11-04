/*
 * @test
 * @summary Test for getLast() method in LinkedBlockingDeque
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
            throw new AssertionError("Expected 'second', got: " + last);
        }
    }

    static void testGetLastEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.getLast();
            throw new AssertionError("Expected NoSuchElementException");
        } catch (NoSuchElementException e) {
            // expected
        }
    }
}
