/*
 * @test
 * @summary Test LinkedBlockingQueue.toArray() method behavior
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

        // Test toArray on empty queue returns empty array
        Object[] emptyArray = queue.toArray();
        if (emptyArray == null) {
            throw new AssertionError("toArray() returned null on empty queue");
        }
        if (emptyArray.length != 0) {
            throw new AssertionError("toArray() returned non-empty array on empty queue");
        }

        // Add elements and test toArray returns them in FIFO order
        queue.put("first");
        queue.put("second");
        queue.put("third");

        Object[] arr = queue.toArray();
        if (arr == null) {
            throw new AssertionError("toArray() returned null on non-empty queue");
        }
        if (arr.length != 3) {
            throw new AssertionError("toArray() returned array of wrong length: " + arr.length);
        }
        if (!"first".equals(arr[0]) || !"second".equals(arr[1]) || !"third".equals(arr[2])) {
            throw new AssertionError("toArray() returned elements in wrong order or wrong elements");
        }

        // Test that toArray does not throw UnsupportedOperationException
        // (based on WIT expath, but current code does not throw)
        try {
            queue.toArray();
        } catch (UnsupportedOperationException e) {
            throw new AssertionError("toArray() should not throw UnsupportedOperationException", e);
        }

        System.out.println("LinkedBlockingQueue.toArray() tests passed");
    }
}
