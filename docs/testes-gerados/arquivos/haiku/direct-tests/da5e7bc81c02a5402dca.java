/*
 * @test
 * @summary Verifies PriorityQueue iterator remove() semantics: ISE when no element is pending, double-remove ISE, correct removal after next(), full drain via iterator, and CME on concurrent modification.
 * @run main PriorityQueueWitupTest
 */

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;

public class PriorityQueueWitupTest {

    public static void main(String[] args) {
        testRemoveWithoutNextThrowsISE();
        testDoubleRemoveThrowsISE();
        testRemoveAfterNextRemovesElement();
        testRemoveAllViaIteratorEmptiesQueue();
        testConcurrentModificationDetected();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRemoveWithoutNextThrowsISE() {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Arrays.asList(3, 1, 2));
        Iterator<Integer> it = pq.iterator();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "remove() before next() must throw IllegalStateException");
        check(pq.size() == 3, "queue must be unchanged");
    }

    static void testDoubleRemoveThrowsISE() {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Arrays.asList(3, 1, 2));
        Iterator<Integer> it = pq.iterator();
        it.next();
        it.remove();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "second consecutive remove() must throw IllegalStateException");
        check(pq.size() == 2, "size must be 2 after one removal");
    }

    static void testRemoveAfterNextRemovesElement() {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Arrays.asList(5, 1, 3));
        Iterator<Integer> it = pq.iterator();
        Integer first = it.next();
        it.remove();
        check(!pq.contains(first), "removed element must no longer be in the queue");
        check(pq.size() == 2, "size must decrease by one");
        int previous = Integer.MIN_VALUE;
        while (!pq.isEmpty()) {
            int v = pq.poll();
            check(v > previous, "remaining elements must still poll in ascending order");
            previous = v;
        }
    }

    static void testRemoveAllViaIteratorEmptiesQueue() {
        PriorityQueue<Integer> pq = new PriorityQueue<>(
                Arrays.asList(9, 4, 7, 1, 8, 2, 6, 3, 5, 0));
        HashSet<Integer> seen = new HashSet<>();
        int removed = 0;
        Iterator<Integer> it = pq.iterator();
        while (it.hasNext()) {
            seen.add(it.next());
            it.remove();
            removed++;
        }
        check(removed == 10, "expected 10 removals, got " + removed);
        check(seen.size() == 10, "every element must be returned exactly once");
        check(pq.isEmpty(), "queue must be empty after removing all elements");
    }

    static void testConcurrentModificationDetected() {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Arrays.asList(2, 1));
        Iterator<Integer> it = pq.iterator();
        it.next();
        pq.add(10);
        boolean thrown = false;
        try {
            it.remove();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "remove() after external modification must throw ConcurrentModificationException");
    }
}
