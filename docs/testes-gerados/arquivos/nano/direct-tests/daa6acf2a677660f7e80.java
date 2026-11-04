/*
 * @test
 * @summary Test the add method of LinkedTransferQueue for basic functionality.
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testAddWithNonNullElement();
        testAddWithNullElement();
    }

    static void testAddWithNonNullElement() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        boolean result = queue.add("element");
        if (!result) {
            throw new AssertionError("add() should return true for non-null element");
        }
        if (queue.isEmpty()) {
            throw new AssertionError("Queue should not be empty after add()");
        }
        String head = queue.peek();
        if (!"element".equals(head)) {
            throw new AssertionError("Head of queue should be 'element' after add(), but was: " + head);
        }
    }

    static void testAddWithNullElement() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        try {
            queue.add(null);
            throw new AssertionError("add(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected exception
        }
        if (!queue.isEmpty()) {
            throw new AssertionError("Queue should remain empty after failed add(null)");
        }
    }
}
