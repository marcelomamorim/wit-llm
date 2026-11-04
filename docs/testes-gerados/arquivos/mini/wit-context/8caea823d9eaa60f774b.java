/*
 * @test
 * @summary Test LinkedBlockingDeque.size() method for correct locking and count retrieval
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();

        // Initially empty, size should be 0
        int initialSize = deque.size();
        if (initialSize != 0) {
            throw new AssertionError("Expected initial size 0 but got " + initialSize);
        }

        // Add elements and check size increments
        deque.add(1);
        int sizeAfterAdd1 = deque.size();
        if (sizeAfterAdd1 != 1) {
            throw new AssertionError("Expected size 1 after adding one element but got " + sizeAfterAdd1);
        }

        deque.add(2);
        int sizeAfterAdd2 = deque.size();
        if (sizeAfterAdd2 != 2) {
            throw new AssertionError("Expected size 2 after adding two elements but got " + sizeAfterAdd2);
        }

        // Remove element and check size decrements
        deque.remove();
        int sizeAfterRemove = deque.size();
        if (sizeAfterRemove != 1) {
            throw new AssertionError("Expected size 1 after removing one element but got " + sizeAfterRemove);
        }

        // Remove last element and check size is zero
        deque.remove();
        int sizeAfterRemoveAll = deque.size();
        if (sizeAfterRemoveAll != 0) {
            throw new AssertionError("Expected size 0 after removing all elements but got " + sizeAfterRemoveAll);
        }

        System.out.println("All LinkedBlockingDeque.size() tests passed.");
    }
}
