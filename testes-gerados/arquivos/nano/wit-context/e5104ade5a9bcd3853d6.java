/*
 * @test    @summary Test removeAll method of PriorityBlockingQueue
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.Collection;
import java.util.Arrays;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testRemoveAllWithNullCollection();
        testRemoveAllWithNonNullCollection();
    }

    static void testRemoveAllWithNullCollection() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
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
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.addAll(Arrays.asList(1, 2, 3, 4, 5));
        Collection<Integer> c = Arrays.asList(2, 4);
        boolean result = false;
        try {
            result = queue.removeAll(c);
            if (result) {
                System.out.println("PASS: removeAll with non-null collection returned true");
            } else {
                System.out.println("PASS: removeAll with non-null collection returned false");
            }
            if (queue.contains(2) || queue.contains(4)) {
                System.out.println("FAIL: Queue still contains removed elements");
            } else {
                System.out.println("PASS: Queue no longer contains removed elements");
            }
        } catch (UnsupportedOperationException e) {
            System.out.println("FAIL: Unexpected UnsupportedOperationException: " + e);
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
