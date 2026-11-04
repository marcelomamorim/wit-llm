/*
 * @test
 * @summary Test LinkedBlockingDeque.getLast() behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        testGetLastEmptyDeque();
        testGetLastNonEmptyDeque();
    }

    static void testGetLastEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.getLast();
            throw new RuntimeException("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testGetLastNonEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.addLast("first");
        deque.addLast("second");
        String last = deque.getLast();
        if (!"second".equals(last)) {
            throw new RuntimeException("getLast() returned wrong element: " + last);
        }
    }
}
