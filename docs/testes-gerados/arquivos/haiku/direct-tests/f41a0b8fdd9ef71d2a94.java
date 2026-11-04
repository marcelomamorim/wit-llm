/*
 * @test
 * @summary Tests LinkedTransferQueue.retainAll observable behavior
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testRetainAllNullThrowsNPE();
        testRetainSubsetKeepsOrder();
        testRetainAllSupersetReturnsFalse();
        testRetainAllOnEmptyQueueReturnsFalse();
        testRetainNoneClearsQueue();
        testRetainAllKeepsDuplicates();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static List<Integer> contents(LinkedTransferQueue<Integer> q) {
        return new ArrayList<>(q);
    }

    static void testRetainAllNullThrowsNPE() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>(Arrays.asList(1, 2, 3));
        boolean thrown = false;
        try {
            q.retainAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "retainAll(null) should throw NullPointerException");
        check(contents(q).equals(Arrays.asList(1, 2, 3)), "queue must be unchanged after NPE");
    }

    static void testRetainSubsetKeepsOrder() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>(Arrays.asList(1, 2, 3, 4, 5));
        boolean changed = q.retainAll(Arrays.asList(4, 2, 6));
        check(changed, "retainAll should return true when elements are removed");
        check(contents(q).equals(Arrays.asList(2, 4)),
              "remaining elements must be 2,4 in FIFO order but were " + contents(q));
    }

    static void testRetainAllSupersetReturnsFalse() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>(Arrays.asList(1, 2));
        boolean changed = q.retainAll(Arrays.asList(1, 2, 3));
        check(!changed, "retainAll should return false when nothing is removed");
        check(contents(q).equals(Arrays.asList(1, 2)), "queue contents must be unchanged");
    }

    static void testRetainAllOnEmptyQueueReturnsFalse() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        boolean changed = q.retainAll(Arrays.asList(1, 2));
        check(!changed, "retainAll on empty queue should return false");
        check(q.isEmpty(), "queue should remain empty");
    }

    static void testRetainNoneClearsQueue() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>(Arrays.asList(1, 2, 3));
        boolean changed = q.retainAll(new ArrayList<Integer>());
        check(changed, "retainAll with empty collection should return true");
        check(q.isEmpty(), "queue should be empty after retaining nothing");
    }

    static void testRetainAllKeepsDuplicates() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>(Arrays.asList(1, 1, 2));
        boolean changed = q.retainAll(Arrays.asList(1));
        check(changed, "retainAll should return true when 2 is removed");
        check(contents(q).equals(Arrays.asList(1, 1)),
              "duplicate retained elements must be preserved but were " + contents(q));
    }
}
