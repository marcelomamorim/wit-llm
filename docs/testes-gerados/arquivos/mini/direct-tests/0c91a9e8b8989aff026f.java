/*
 * @test
 * @summary Test LinkedBlockingDeque add(E) method behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.NoSuchElementException;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        testAddNonNullElement();
        testAddNullElementThrowsNPE();
    }

    static void testAddNonNullElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean added = deque.add("test");
        if (!added) {
            throw new AssertionError("add should return true when element is added");
        }
        String polled = deque.pollFirst();
        if (!"test".equals(polled)) {
            throw new AssertionError("Element added should be retrievable via pollFirst");
        }
    }

    static void testAddNullElementThrowsNPE() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.add(null);
            throw new AssertionError("add(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
