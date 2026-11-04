/*
 * @test
 * @summary Test LinkedBlockingDeque.getFirst() behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        testGetFirstOnEmptyDeque();
        testGetFirstOnNonEmptyDeque();
    }

    static void testGetFirstOnEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.getFirst();
            throw new RuntimeException("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testGetFirstOnNonEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("first");
        String first = deque.getFirst();
        if (!"first".equals(first)) {
            throw new RuntimeException("getFirst() returned wrong element: " + first);
        }
    }
}
