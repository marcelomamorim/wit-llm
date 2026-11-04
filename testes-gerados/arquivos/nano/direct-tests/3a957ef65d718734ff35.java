/*
 * @test    @summary Test removeAll(Collection<?>) method of LinkedTransferQueue
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.Collection;
import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testRemoveAllWithNull();
        testRemoveAllWithEmptyCollection();
        testRemoveAllWithMatchingElements();
        testRemoveAllWithNonMatchingElements();
    }

    static void testRemoveAllWithNull() {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        boolean exceptionThrown = false;
        try {
            queue.removeAll(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NullPointerException when passing null";
    }

    static void testRemoveAllWithEmptyCollection() {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        queue.add(1);
        queue.add(2);
        boolean result = queue.removeAll(java.util.Collections.emptyList());
        assert result == false : "removeAll with empty collection should return false";
        assert queue.contains(1) : "Queue should still contain 1";
        assert queue.contains(2) : "Queue should still contain 2";
    }

    static void testRemoveAllWithMatchingElements() {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        boolean result = queue.removeAll(java.util.Arrays.asList(2, 3));
        assert result : "removeAll with matching elements should return true";
        assert queue.contains(1) : "Queue should still contain 1";
        assert !queue.contains(2) : "Queue should not contain 2";
        assert !queue.contains(3) : "Queue should not contain 3";
    }

    static void testRemoveAllWithNonMatchingElements() {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        queue.add(1);
        queue.add(2);
        boolean result = queue.removeAll(java.util.Arrays.asList(4, 5));
        assert result == false : "removeAll with non-matching elements should return false";
        assert queue.contains(1) : "Queue should still contain 1";
        assert queue.contains(2) : "Queue should still contain 2";
    }
}
