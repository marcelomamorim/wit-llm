/*
 * @test
 * @summary Deterministic tests for LinkedBlockingDeque.addLast(E) covering exception paths
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        testAddLastNull();
        testAddLastFullDeque();
    }

    static void testAddLastNull() {
        LinkedBlockingDeque<Object> deque = new LinkedBlockingDeque<>();
        try {
            deque.addLast(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception for null element
        }
    }

    static void testAddLastFullDeque() {
        final int capacity = 1;
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(capacity);
        boolean added = deque.offerLast("first");
        if (!added) {
            throw new Error("Setup failed: could not add element to empty deque");
        }
        try {
            deque.addLast("second");
            throw new Error("Expected IllegalStateException not thrown when adding to full deque");
        } catch (IllegalStateException expected) {
            // Expected exception for full deque
        }
    }
}
