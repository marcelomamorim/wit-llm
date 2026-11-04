/*
 * @test
 * @summary Test for ConcurrentLinkedQueue.retainAll(Collection<?>) method
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Collection;
import java.util.Arrays;

public class ConcurrentLinkedQueueWitupTest {

    public static void main(String[] args) {
        testRetainAllWithNullCollection();
        testRetainAllWithEmptyCollection();
        testRetainAllWithMatchingElements();
        testRetainAllWithNonMatchingElements();
    }

    static void testRetainAllWithNullCollection() {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        queue.addAll(Arrays.asList(1, 2, 3));
        try {
            queue.retainAll(null);
            System.out.println("FAIL: Expected NullPointerException for null collection");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    static void testRetainAllWithEmptyCollection() {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        queue.addAll(Arrays.asList(1, 2, 3));
        boolean changed = queue.retainAll(Arrays.asList());
        if (!changed && queue.isEmpty()) {
            System.out.println("PASS: retainAll with empty collection cleared the queue");
        } else {
            System.out.println("FAIL: retainAll with empty collection did not clear the queue");
        }
    }

    static void testRetainAllWithMatchingElements() {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        queue.addAll(Arrays.asList(1, 2, 3, 4, 5));
        boolean changed = queue.retainAll(Arrays.asList(2, 4));
        if (changed && queue.size() == 2 && queue.contains(2) && queue.contains(4)) {
            System.out.println("PASS: retainAll with matching elements retained only specified elements");
        } else {
            System.out.println("FAIL: retainAll with matching elements did not behave as expected");
        }
    }

    static void testRetainAllWithNonMatchingElements() {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        queue.addAll(Arrays.asList(1, 2, 3));
        boolean changed = queue.retainAll(Arrays.asList(4, 5));
        if (changed && queue.isEmpty()) {
            System.out.println("PASS: retainAll with non-matching elements cleared the queue");
        } else {
            System.out.println("FAIL: retainAll with non-matching elements did not behave as expected");
        }
    }
}
