/*
 * @test
 * @summary Verifies PriorityQueue.retainAll null handling and observable retention behavior
 * @run main PriorityQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class PriorityQueueWitupTest {

    public static void main(String[] args) {
        testNullCollectionThrowsNPE();
        testRetainAllKeepsOnlyContainedElements();
        testRetainAllReturnsFalseWhenNoChange();
        testRetainAllWithEmptyCollectionClearsQueue();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullCollectionThrowsNPE() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        boolean thrown = false;
        try {
            pq.retainAll(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        check(thrown, "retainAll(null) must throw NullPointerException");
        check(pq.size() == 1, "queue must be unchanged after failed retainAll(null)");
    }

    static void testRetainAllKeepsOnlyContainedElements() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.addAll(Arrays.asList(5, 1, 3, 2, 4));
        List<Integer> keep = Arrays.asList(2, 4, 9);
        boolean changed = pq.retainAll(keep);
        check(changed, "retainAll must return true when elements are removed");
        check(pq.size() == 2, "size must be 2 after retaining {2,4}");
        check(pq.contains(2) && pq.contains(4), "retained elements must be present");
        check(!pq.contains(1) && !pq.contains(3) && !pq.contains(5), "removed elements must be absent");
        check(pq.poll().equals(2), "head must be least retained element 2");
        check(pq.poll().equals(4), "next head must be 4");
        check(pq.poll() == null, "queue must be empty");
    }

    static void testRetainAllReturnsFalseWhenNoChange() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        pq.add(2);
        List<Integer> keep = new ArrayList<>(Arrays.asList(1, 2, 3));
        boolean changed = pq.retainAll(keep);
        check(!changed, "retainAll must return false when nothing is removed");
        check(pq.size() == 2, "size must remain 2");
    }

    static void testRetainAllWithEmptyCollectionClearsQueue() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.addAll(Arrays.asList(7, 8, 9));
        boolean changed = pq.retainAll(new ArrayList<Integer>());
        check(changed, "retainAll(empty) must return true for non-empty queue");
        check(pq.isEmpty(), "queue must be empty after retainAll(empty)");
    }
}
