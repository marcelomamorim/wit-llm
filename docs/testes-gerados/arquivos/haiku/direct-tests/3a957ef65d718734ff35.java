/*
 * @test
 * @summary Behavioral tests for LinkedTransferQueue.removeAll(Collection)
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testRemovesMatchingElements();
        testNoMatchReturnsFalse();
        testEmptyArgumentReturnsFalse();
        testEmptyQueueReturnsFalse();
        testRemovesAllDuplicates();
        testNullArgumentThrowsNPE();
        testQueueUsableAfterRemoveAll();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static List<Integer> contents(LinkedTransferQueue<Integer> q) {
        return new ArrayList<Integer>(q);
    }

    static void testRemovesMatchingElements() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        q.add(1);
        q.add(2);
        q.add(3);
        q.add(4);
        boolean changed = q.removeAll(Arrays.asList(2, 4));
        check(changed, "removeAll should return true when elements are removed");
        check(contents(q).equals(Arrays.asList(1, 3)),
              "expected [1, 3] but was " + contents(q));
        check(q.size() == 2, "size should be 2");
    }

    static void testNoMatchReturnsFalse() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        q.add(1);
        q.add(2);
        boolean changed = q.removeAll(Arrays.asList(7, 8));
        check(!changed, "removeAll should return false when nothing matches");
        check(contents(q).equals(Arrays.asList(1, 2)),
              "queue contents should be unchanged");
    }

    static void testEmptyArgumentReturnsFalse() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        q.add(1);
        q.add(2);
        boolean changed = q.removeAll(Collections.emptyList());
        check(!changed, "removeAll with empty collection should return false");
        check(contents(q).equals(Arrays.asList(1, 2)),
              "queue contents should be unchanged");
    }

    static void testEmptyQueueReturnsFalse() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        boolean changed = q.removeAll(Arrays.asList(1, 2));
        check(!changed, "removeAll on empty queue should return false");
        check(q.isEmpty(), "queue should remain empty");
    }

    static void testRemovesAllDuplicates() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        q.add(5);
        q.add(6);
        q.add(5);
        q.add(7);
        boolean changed = q.removeAll(Arrays.asList(5));
        check(changed, "removeAll should return true");
        check(contents(q).equals(Arrays.asList(6, 7)),
              "expected [6, 7] but was " + contents(q));
    }

    static void testNullArgumentThrowsNPE() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        q.add(1);
        boolean thrown = false;
        try {
            q.removeAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "removeAll(null) should throw NullPointerException");
        check(contents(q).equals(Arrays.asList(1)),
              "queue should be unchanged after failed removeAll");
    }

    static void testQueueUsableAfterRemoveAll() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        q.add(1);
        q.add(2);
        q.add(3);
        q.removeAll(Arrays.asList(1, 2, 3));
        check(q.isEmpty(), "queue should be empty after removing all elements");
        q.offer(9);
        check(q.poll() == 9, "queue should accept and return new elements");
        check(q.poll() == null, "queue should be empty again");
    }
}
