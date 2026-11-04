/*
 * @test
 * @summary Test deterministic behavior of ArrayBlockingQueue.toArray()
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.Arrays;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testToArrayEmptyQueue();
        testToArrayPartialFill();
        testToArrayWrapAround();
    }

    private static void testToArrayEmptyQueue() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);
        Object[] arr = queue.toArray();
        if (arr.length != 0) {
            throw new AssertionError("Expected empty array from empty queue");
        }
    }

    private static void testToArrayPartialFill() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(5);
        queue.add("a");
        queue.add("b");
        queue.add("c");
        Object[] arr = queue.toArray();
        if (arr.length != 3) {
            throw new AssertionError("Expected array length 3, got " + arr.length);
        }
        if (!"a".equals(arr[0]) || !"b".equals(arr[1]) || !"c".equals(arr[2])) {
            throw new AssertionError("Array contents do not match queue elements");
        }
    }

    private static void testToArrayWrapAround() throws Exception {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);
        // Fill queue
        for (int i = 1; i <= 5; i++) {
            queue.put(i);
        }
        // Remove two elements to cause wrap-around
        queue.take();
        queue.take();
        // Add two more elements to wrap around
        queue.put(6);
        queue.put(7);

        Object[] arr = queue.toArray();
        // Expected elements: 3,4,5,6,7 in order
        int[] expected = {3,4,5,6,7};
        if (arr.length != expected.length) {
            throw new AssertionError("Expected array length " + expected.length + ", got " + arr.length);
        }
        for (int i = 0; i < expected.length; i++) {
            if (!(arr[i] instanceof Integer) || ((Integer)arr[i]) != expected[i]) {
                throw new AssertionError("Element at index " + i + " expected " + expected[i] + ", got " + arr[i]);
            }
        }
    }
}
