/*
 * @test
 * @summary Test LinkedBlockingQueue.toArray(T[] a) method behavior
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.lang.reflect.Array;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

        // Test toArray with empty queue and array length 0
        String[] emptyArray = new String[0];
        String[] result = queue.toArray(emptyArray);
        if (result.length != 0) {
            throw new AssertionError("Expected empty array result for empty queue");
        }

        // Test toArray with array smaller than queue size
        queue.put("one");
        queue.put("two");
        String[] smallArray = new String[1];
        String[] result2 = queue.toArray(smallArray);
        if (result2.length != 2) {
            throw new AssertionError("Expected new array of size 2 when input array too small");
        }
        if (!"one".equals(result2[0]) || !"two".equals(result2[1])) {
            throw new AssertionError("Array contents do not match queue elements");
        }

        // Test toArray with array larger than queue size
        String[] largeArray = new String[5];
        for (int i = 0; i < largeArray.length; i++) largeArray[i] = "fill" + i;
        String[] result3 = queue.toArray(largeArray);
        if (result3 != largeArray) {
            throw new AssertionError("Expected same array instance when input array is large enough");
        }
        if (!"one".equals(result3[0]) || !"two".equals(result3[1])) {
            throw new AssertionError("Array contents do not match queue elements");
        }
        if (result3[2] != null) {
            throw new AssertionError("Expected null element after last queue element");
        }

        System.out.println("All tests passed.");
    }
}
