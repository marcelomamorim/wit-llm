/*
 * @test
 * @summary PriorityQueue.retainAll keeps only elements contained in the given collection, reports changes, and rejects null
 * @run main PriorityQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class PriorityQueueWitupTest {

    public static void main(String[] args) {
        testRemovesNonMembers();
        testNoChangeWhenAllElementsRetained();
        testEmptyCollectionClearsQueue();
        testNullArgumentThrowsNullPointerException();
        testEmptyQueueReturnsFalse();
        testDuplicatesAreRetained();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static List<Integer> drain(PriorityQueue<Integer> pq) {
        List<Integer> out = new ArrayList<>();
        while (!pq.isEmpty()) {
            out.add(pq.poll());
        }
        return out;
    }

    static void testRemovesNonMembers() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        Collections.addAll(pq, 5, 1, 3, 2, 4);
        boolean changed = pq.retainAll(Arrays.asList(1, 3));
        check(changed, "retainAll should report a change");
        check(pq.size() == 2, "size should be 2 but was " + pq.size());
        check(drain(pq).equals(Arrays.asList(1, 3)), "unexpected remaining elements");
    }

    static void testNoChangeWhenAllElementsRetained() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        Collections.addAll(pq, 1, 2, 3);
        boolean changed = pq.retainAll(Arrays.asList(1, 2, 3, 4));
        check(!changed, "retainAll should return false when nothing is removed");
        check(pq.size() == 3, "size should remain 3");
        check(drain(pq).equals(Arrays.asList(1, 2, 3)), "elements should be unchanged");
    }

    static void testEmptyCollectionClearsQueue() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        Collections.addAll(pq, 7, 8, 9);
        boolean changed = pq.retainAll(Collections.<Integer>emptyList());
        check(changed, "retainAll with empty collection should report a change");
        check(pq.isEmpty(), "queue should be empty");
        check(pq.poll() == null, "poll on empty queue should return null");
    }

    static void testNullArgumentThrowsNullPointerException() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        boolean thrown = false;
        try {
            pq.retainAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "retainAll(null) should throw NullPointerException");
        check(pq.size() == 1, "queue should be unchanged after NPE");
    }

    static void testEmptyQueueReturnsFalse() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        boolean changed = pq.retainAll(Arrays.asList(1, 2));
        check(!changed, "retainAll on empty queue should return false");
        check(pq.isEmpty(), "queue should remain empty");
    }

    static void testDuplicatesAreRetained() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        Collections.addAll(pq, 2, 2, 3, 5);
        boolean changed = pq.retainAll(Arrays.asList(2));
        check(changed, "retainAll should report a change");
        check(pq.size() == 2, "both duplicate 2 elements should be retained");
        check(drain(pq).equals(Arrays.asList(2, 2)), "unexpected remaining elements");
    }
}
