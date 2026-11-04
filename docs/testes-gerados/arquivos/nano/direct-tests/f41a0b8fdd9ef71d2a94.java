/*
 * @test    @summary Test retainAll method of LinkedTransferQueue
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.Collection;
import java.util.Arrays;
import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testRetainAllWithEmptyCollection();
        testRetainAllWithMatchingElements();
        testRetainAllWithNonMatchingElements();
        testRetainAllWithNullCollection();
    }

    static void testRetainAllWithEmptyCollection() {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        queue.addAll(Arrays.asList(1, 2, 3));
        boolean changed = queue.retainAll(Arrays.asList());
        assert !changed : "Expected no change when retaining with empty collection";
        assert queue.size() == 0 || queue.size() == 3 : "Queue size should be 0 or 3 after retainAll with empty collection";
    }

    static void testRetainAllWithMatchingElements() {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        queue.addAll(Arrays.asList(1, 2, 3, 4));
        boolean changed = queue.retainAll(Arrays.asList(2, 3));
        assert changed : "Expected change when retaining matching elements";
        assert queue.size() == 2 : "Queue should contain only 2 and 3";
        assert queue.contains(2) : "Queue should contain 2";
        assert queue.contains(3) : "Queue should contain 3";
    }

    static void testRetainAllWithNonMatchingElements() {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        queue.addAll(Arrays.asList(5, 6, 7));
        boolean changed = queue.retainAll(Arrays.asList(8, 9));
        assert changed : "Expected change when no elements match";
        assert queue.size() == 0 : "Queue should be empty after retainAll with non-matching elements";
    }

    static void testRetainAllWithNullCollection() {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        queue.addAll(Arrays.asList(1, 2, 3));
        try {
            queue.retainAll(null);
            assert false : "Expected NullPointerException when passing null";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
