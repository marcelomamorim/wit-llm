/*
 * @test
 * @summary Test deterministic behavior of PriorityBlockingQueue.toArray()
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.Arrays;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        testToArrayEmptyQueue();
        testToArrayWithElements();
    }

    private static void testToArrayEmptyQueue() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        Object[] arr = queue.toArray();
        if (arr == null) {
            throw new AssertionError("toArray() returned null on empty queue");
        }
        if (arr.length != 0) {
            throw new AssertionError("toArray() length expected 0 but was " + arr.length);
        }
    }

    private static void testToArrayWithElements() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(3);
        queue.add(1);
        queue.add(2);
        Object[] arr = queue.toArray();
        if (arr == null) {
            throw new AssertionError("toArray() returned null on non-empty queue");
        }
        if (arr.length != 3) {
            throw new AssertionError("toArray() length expected 3 but was " + arr.length);
        }
        // The order is not guaranteed, but all elements must be present
        boolean found1 = false, found2 = false, found3 = false;
        for (Object o : arr) {
            if (!(o instanceof Integer)) {
                throw new AssertionError("toArray() returned non-Integer element");
            }
            int val = (Integer) o;
            if (val == 1) found1 = true;
            else if (val == 2) found2 = true;
            else if (val == 3) found3 = true;
        }
        if (!found1 || !found2 || !found3) {
            throw new AssertionError("toArray() missing expected elements");
        }
    }
}
