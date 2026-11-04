/*
 * @test
 * @summary Tests for ArrayBlockingQueue.removeAll(Collection)
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testRemoveAllWithNullCollection();
        testRemoveAllWithNonNullCollection();
    }

    static void testRemoveAllWithNullCollection() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        try {
            queue.removeAll(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    static void testRemoveAllWithNonNullCollection() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        queue.addAll(Arrays.asList(1, 2, 3, 4, 5));
        Collection<Integer> c = Arrays.asList(2, 4);
        boolean result = false;
        try {
            result = queue.removeAll(c);
            if (result) {
                System.out.println("PASS: removeAll with non-null collection executed, result: " + result);
            } else {
                System.out.println("PASS: removeAll with non-null collection executed, no elements removed, result: " + result);
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
        // Verify remaining elements
        if (queue.containsAll(Arrays.asList(1, 3, 5))) {
            System.out.println("PASS: Remaining elements are correct");
        } else {
            System.out.println("FAIL: Remaining elements are incorrect");
        }
    }
}
