/*
 * @test
 * @summary Test LinkedBlockingQueue.toArray() method correctness
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.Arrays;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testToArrayEmptyQueue();
        testToArrayWithElements();
        testToArrayWithNullElements();
    }

    static void testToArrayEmptyQueue() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        Object[] arr = queue.toArray();
        if (arr.length != 0) {
            throw new AssertionError("Expected empty array for empty queue, got length " + arr.length);
        }
    }

    static void testToArrayWithElements() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("one");
        queue.add("two");
        queue.add("three");
        Object[] arr = queue.toArray();
        if (arr.length != 3) {
            throw new AssertionError("Expected array length 3, got " + arr.length);
        }
        if (!"one".equals(arr[0]) || !"two".equals(arr[1]) || !"three".equals(arr[2])) {
            throw new AssertionError("Array elements do not match queue elements: " + Arrays.toString(arr));
        }
    }

    static void testToArrayWithNullElements() throws Exception {
        // LinkedBlockingQueue does not allow null elements, so this test
        // verifies that toArray does not fail if queue contains nulls internally.
        // We simulate this by reflection to insert a null node (not public API).
        // Since reflection is not allowed per instructions, we skip this test.
        // Instead, we verify that toArray never returns null elements if queue is used normally.
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("a");
        queue.add("b");
        Object[] arr = queue.toArray();
        for (Object o : arr) {
            if (o == null) {
                throw new AssertionError("toArray returned null element unexpectedly");
            }
        }
    }
}
