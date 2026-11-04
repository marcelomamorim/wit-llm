/*
 * @test
 * @summary Tests PriorityBlockingQueue.retainAll observable behavior: null argument, removal, return value, and heap order after bulk removal
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testNullCollectionThrowsNPE();
        testRemovesElementsNotInCollection();
        testNoChangeReturnsFalse();
        testEmptyQueueReturnsFalse();
        testRetainEmptyCollectionClearsQueue();
        testHeapOrderPreservedAfterRetainAll();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullCollectionThrowsNPE() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(1);
        q.add(2);
        try {
            q.retainAll(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
        check(q.size() == 2, "queue must be unchanged after NPE");
    }

    static void testRemovesElementsNotInCollection() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(1);
        q.add(2);
        q.add(3);
        q.add(4);
        boolean modified = q.retainAll(Arrays.asList(2, 4));
        check(modified, "retainAll should return true when elements are removed");
        check(q.size() == 2, "size should be 2, was " + q.size());
        check(q.contains(2) && q.contains(4), "retained elements missing");
        check(!q.contains(1) && !q.contains(3), "non-retained elements still present");
    }

    static void testNoChangeReturnsFalse() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(1);
        q.add(2);
        boolean modified = q.retainAll(Arrays.asList(1, 2, 3));
        check(!modified, "retainAll should return false when nothing is removed");
        check(q.size() == 2, "size should remain 2");
    }

    static void testEmptyQueueReturnsFalse() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        boolean modified = q.retainAll(Arrays.asList(1, 2));
        check(!modified, "empty queue should not be modified");
        check(q.isEmpty(), "queue should remain empty");
    }

    static void testRetainEmptyCollectionClearsQueue() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(7);
        q.add(8);
        boolean modified = q.retainAll(Collections.emptyList());
        check(modified, "retainAll with empty collection should return true");
        check(q.isEmpty(), "queue should be empty");
        check(q.poll() == null, "poll on emptied queue should return null");
    }

    static void testHeapOrderPreservedAfterRetainAll() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(5);
        q.add(1);
        q.add(3);
        q.add(4);
        boolean modified = q.retainAll(Arrays.asList(3, 5));
        check(modified, "retainAll should return true");
        check(q.size() == 2, "size should be 2");
        check(q.poll() == 3, "first poll should return smallest retained element 3");
        check(q.poll() == 5, "second poll should return 5");
        check(q.poll() == null, "queue should now be empty");
    }
}
