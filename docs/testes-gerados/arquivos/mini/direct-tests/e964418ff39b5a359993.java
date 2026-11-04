/*
 * @test
 * @summary Test LinkedBlockingQueue.toArray(T[] a) method behavior
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.Arrays;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testToArrayWithSmallerArray();
        testToArrayWithExactSizeArray();
        testToArrayWithLargerArray();
        testToArrayEmptyQueue();
    }

    static void testToArrayWithSmallerArray() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");

        String[] input = new String[1]; // smaller than queue size
        String[] result = queue.toArray(input);

        if (result.length != 3) {
            throw new RuntimeException("Expected array length 3, got " + result.length);
        }
        if (!"a".equals(result[0]) || !"b".equals(result[1]) || !"c".equals(result[2])) {
            throw new RuntimeException("Array contents do not match queue elements");
        }
    }

    static void testToArrayWithExactSizeArray() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);

        Integer[] input = new Integer[3]; // exact size
        Integer[] result = queue.toArray(input);

        if (result != input) {
            throw new RuntimeException("Expected returned array to be the same instance as input");
        }
        if (result[0] != 1 || result[1] != 2 || result[2] != 3) {
            throw new RuntimeException("Array contents do not match queue elements");
        }
    }

    static void testToArrayWithLargerArray() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("x");
        queue.add("y");

        String[] input = new String[5]; // larger than queue size
        Arrays.fill(input, "fill");
        String[] result = queue.toArray(input);

        if (result != input) {
            throw new RuntimeException("Expected returned array to be the same instance as input");
        }
        if (!"x".equals(result[0]) || !"y".equals(result[1])) {
            throw new RuntimeException("Array contents do not match queue elements");
        }
        if (result[2] != null) {
            throw new RuntimeException("Expected null at position after last element");
        }
        for (int i = 3; i < result.length; i++) {
            if (!"fill".equals(result[i])) {
                throw new RuntimeException("Expected unchanged elements beyond null position");
            }
        }
    }

    static void testToArrayEmptyQueue() {
        LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<>();
        Object[] input = new Object[2];
        input[0] = "foo";
        input[1] = "bar";

        Object[] result = queue.toArray(input);

        if (result != input) {
            throw new RuntimeException("Expected returned array to be the same instance as input");
        }
        if (result[0] != null) {
            throw new RuntimeException("Expected null at position 0 for empty queue");
        }
        if (!"bar".equals(result[1])) {
            throw new RuntimeException("Expected unchanged element at position 1");
        }
    }
}
