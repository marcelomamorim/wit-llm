/*
 * @test
 * @summary Tests the toArray(T[] a) method of LinkedBlockingQueue for correctness and edge cases.
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.Arrays;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testToArrayWithEmptyQueue();
        testToArrayWithExactCapacity();
        testToArrayWithLargerCapacity();
        testToArrayWithSmallerCapacity();
        testToArrayWithNullsInQueue();
    }

    static void testToArrayWithEmptyQueue() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        String[] arr = new String[0];
        String[] result = queue.toArray(arr);
        assert result != null : "Result should not be null";
        assert result.length == 0 : "Array length should be 0";
        assert result[0] == null : "First element should be null";
    }

    static void testToArrayWithExactCapacity() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.add(1);
        queue.add(2);
        Integer[] arr = new Integer[2];
        Integer[] result = queue.toArray(arr);
        assert result == arr : "Should return the same array instance";
        assert result[0] == 1 : "First element should be 1";
        assert result[1] == 2 : "Second element should be 2";
        assert result.length == 2 : "Array length should be 2";
        assert result[2] == null : "Third element should be null";
    }

    static void testToArrayWithLargerCapacity() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("a");
        queue.add("b");
        String[] arr = new String[4];
        String[] result = queue.toArray(arr);
        assert result == arr : "Should return the same array instance";
        assert result[0].equals("a") : "First element should be 'a'";
        assert result[1].equals("b") : "Second element should be 'b'";
        assert result[2] == null : "Third element should be null";
        assert result.length == 4 : "Array length should be 4";
    }

    static void testToArrayWithSmallerCapacity() {
        LinkedBlockingQueue<Double> queue = new LinkedBlockingQueue<>();
        queue.add(1.1);
        queue.add(2.2);
        Double[] arr = new Double[1];
        Double[] result = queue.toArray(arr);
        assert result != arr : "Should return a new array";
        assert result.length >= 2 : "Array length should be at least 2";
        assert result[0] == 1.1 : "First element should be 1.1";
        assert result[1] == 2.2 : "Second element should be 2.2";
        assert result[2] == null : "Third element should be null";
    }

    static void testToArrayWithNullsInQueue() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("x");
        queue.add(null);
        String[] arr = new String[2];
        String[] result = queue.toArray(arr);
        assert result == arr : "Should return the same array instance";
        assert result[0].equals("x") : "First element should be 'x'";
        assert result[1] == null : "Second element should be null";
    }
}
