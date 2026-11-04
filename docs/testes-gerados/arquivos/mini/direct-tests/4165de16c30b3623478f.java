/*
 * @test
 * @summary Test PriorityBlockingQueue.toArray() returns correct snapshot array
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.Arrays;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testToArrayEmpty();
        testToArrayWithElements();
        testToArrayWithDuplicates();
    }

    static void testToArrayEmpty() {
        PriorityBlockingQueue<Integer> pbq = new PriorityBlockingQueue<>();
        Object[] arr = pbq.toArray();
        if (arr.length != 0) {
            throw new AssertionError("Expected empty array for empty queue, got length " + arr.length);
        }
    }

    static void testToArrayWithElements() {
        PriorityBlockingQueue<Integer> pbq = new PriorityBlockingQueue<>();
        pbq.add(10);
        pbq.add(5);
        pbq.add(20);
        Object[] arr = pbq.toArray();
        if (arr.length != 3) {
            throw new AssertionError("Expected array length 3, got " + arr.length);
        }
        // The array returned is a snapshot of the internal queue array, which is a heap,
        // so elements are not guaranteed sorted order.
        // Check that all elements are present
        boolean found5 = false, found10 = false, found20 = false;
        for (Object o : arr) {
            if (!(o instanceof Integer)) {
                throw new AssertionError("Array element not Integer: " + o);
            }
            int v = (Integer) o;
            if (v == 5) found5 = true;
            else if (v == 10) found10 = true;
            else if (v == 20) found20 = true;
            else throw new AssertionError("Unexpected element in array: " + v);
        }
        if (!found5 || !found10 || !found20) {
            throw new AssertionError("Missing expected elements in toArray result");
        }
    }

    static void testToArrayWithDuplicates() {
        PriorityBlockingQueue<String> pbq = new PriorityBlockingQueue<>();
        pbq.add("a");
        pbq.add("b");
        pbq.add("a");
        Object[] arr = pbq.toArray();
        if (arr.length != 3) {
            throw new AssertionError("Expected array length 3, got " + arr.length);
        }
        int countA = 0, countB = 0;
        for (Object o : arr) {
            if (!(o instanceof String)) {
                throw new AssertionError("Array element not String: " + o);
            }
            String s = (String) o;
            if (s.equals("a")) countA++;
            else if (s.equals("b")) countB++;
            else throw new AssertionError("Unexpected element in array: " + s);
        }
        if (countA != 2 || countB != 1) {
            throw new AssertionError("Unexpected counts in toArray result: a=" + countA + ", b=" + countB);
        }
    }
}
