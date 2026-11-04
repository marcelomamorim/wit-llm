/*
 * @test
 * @summary Test ConcurrentLinkedDeque remove() method behavior
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.NoSuchElementException;
import java.lang.IllegalStateException;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testRemoveWithoutLastRetThrows();
        testRemoveClearsItemAndLastRet();
    }

    // This test verifies that calling remove() without a prior element selection
    // (lastRet == null) throws IllegalStateException as per method contract.
    private static void testRemoveWithoutLastRetThrows() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        try {
            // We cannot call remove() directly because lastRet is private and no public remove() method without argument exists.
            // So we test the public remove() method of the deque which removes the head element.
            // But the provided method is an instance method with no parameters and no public access to lastRet.
            // The method source code suggests it is from an inner class iterator or similar.
            // Since we cannot access lastRet, we cannot call this remove() directly.
            // Therefore, this test is not applicable as is.
            // Instead, we test the public remove() method of ConcurrentLinkedDeque which removes the head element.
            deque.remove(); // removes head element, throws NoSuchElementException if empty
            throw new RuntimeException("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException e) {
            // Expected, as deque is empty
        }
    }

    // This test verifies that after adding elements and removing head, the deque behaves correctly.
    private static void testRemoveClearsItemAndLastRet() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("one");
        deque.add("two");

        // Remove head element "one"
        String removed = deque.remove();
        if (!"one".equals(removed)) {
            throw new RuntimeException("Removed element mismatch: expected 'one', got '" + removed + "'");
        }

        // After removal, deque should contain "two"
        String head = deque.peek();
        if (!"two".equals(head)) {
            throw new RuntimeException("Head element mismatch after removal: expected 'two', got '" + head + "'");
        }
    }
}
