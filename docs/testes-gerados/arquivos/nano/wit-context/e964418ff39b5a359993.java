/*
 * @test
 * @summary Tests for LinkedBlockingQueue.toArray(T[] a)
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.Arrays;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testToArrayWithNullArray();
        testToArrayWithSmallArray();
        testToArrayWithExactArray();
        testToArrayWithLargerArray();
    }

    static void testToArrayWithNullArray() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.add(1);
        queue.add(2);
        Integer[] result = queue.toArray((Integer[]) null); // Should throw NullPointerException
        // Expect exception, so no further assertions
    }

    static void testToArrayWithSmallArray() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.add(10);
        queue.add(20);
        Integer[] a = new Integer[1]; // smaller than size
        Integer[] result = queue.toArray(a);
        assert result.length >= 2 : "Array should be resized to fit elements";
        assert result[0] == 10 : "First element mismatch";
        assert result[1] == 20 : "Second element mismatch";
        assert result[result.length - 1] == null : "Remaining slot should be null";
    }

    static void testToArrayWithExactArray() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.add(5);
        queue.add(15);
        Integer[] a = new Integer[2]; // exact size
        Integer[] result = queue.toArray(a);
        assert result == a : "Should return the same array instance";
        assert result[0] == 5 : "First element mismatch";
        assert result[1] == 15 : "Second element mismatch";
    }

    static void testToArrayWithLargerArray() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.add(7);
        Integer[] a = new Integer[5]; // larger than size
        Integer[] result = queue.toArray(a);
        assert result == a : "Should return the same array instance";
        assert result[0] == 7 : "First element mismatch";
        assert result[1] == null : "Remaining slots should be null";
    }
}
