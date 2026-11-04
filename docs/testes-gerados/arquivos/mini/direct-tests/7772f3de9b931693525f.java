/*
 * @test
 * @summary Test ArrayBlockingQueue.toArray() correctness and wrap-around behavior
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.Arrays;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testEmptyQueue();
        testSingleElement();
        testMultipleElementsNoWrap();
        testMultipleElementsWithWrap();
    }

    static void testEmptyQueue() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(10);
        Object[] arr = q.toArray();
        if (arr.length != 0) {
            throw new RuntimeException("Expected empty array for empty queue, got length " + arr.length);
        }
    }

    static void testSingleElement() throws InterruptedException {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(10);
        q.put("one");
        Object[] arr = q.toArray();
        if (arr.length != 1 || !"one".equals(arr[0])) {
            throw new RuntimeException("Expected array with single element 'one', got " + Arrays.toString(arr));
        }
    }

    static void testMultipleElementsNoWrap() throws InterruptedException {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(5);
        q.put(1);
        q.put(2);
        q.put(3);
        Object[] arr = q.toArray();
        if (arr.length != 3 || !arr[0].equals(1) || !arr[1].equals(2) || !arr[2].equals(3)) {
            throw new RuntimeException("Expected array [1, 2, 3], got " + Arrays.toString(arr));
        }
    }

    static void testMultipleElementsWithWrap() throws InterruptedException {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(5);
        // Fill queue
        q.put(1);
        q.put(2);
        q.put(3);
        q.put(4);
        q.put(5);
        // Remove two elements to advance takeIndex
        q.take(); // removes 1
        q.take(); // removes 2
        // Add two more elements to cause wrap-around
        q.put(6);
        q.put(7);
        // Now queue elements in order: 3,4,5,6,7 with wrap-around
        Object[] arr = q.toArray();
        Object[] expected = {3,4,5,6,7};
        if (arr.length != expected.length) {
            throw new RuntimeException("Expected array length " + expected.length + ", got " + arr.length);
        }
        for (int i = 0; i < expected.length; i++) {
            if (!arr[i].equals(expected[i])) {
                throw new RuntimeException("Expected element " + expected[i] + " at index " + i + ", got " + arr[i]);
            }
        }
    }
}
