/*
 * @test
 * @summary Tests for LinkedBlockingQueue.removeAll(Collection)
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testRemoveAllWithNull();
        testRemoveAllWithNonNull();
    }

    private static void testRemoveAllWithNull() {
        LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<>();
        boolean exceptionThrown = false;
        try {
            queue.removeAll(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to removeAll");
        }
    }

    private static void testRemoveAllWithNonNull() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        Collection<Integer> c = java.util.Arrays.asList(2, 3);
        boolean result = queue.removeAll(c);
        if (!result) {
            throw new AssertionError("removeAll should return true when elements are removed");
        }
        if (queue.contains(2) || queue.contains(3)) {
            throw new AssertionError("Queue should not contain removed elements");
        }
        if (!queue.contains(1)) {
            throw new AssertionError("Queue should still contain element 1");
        }
    }
}
