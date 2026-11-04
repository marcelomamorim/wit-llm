/*
 * @test
 * @summary Tests PriorityQueue spliterator tryAdvance behaviour for empty queue, single element, null action and concurrent modification
 * @run main PriorityQueueWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Spliterator;

public class PriorityQueueWitupTest {

    public static void main(String[] args) {
        testEmptyQueueReturnsFalse();
        testSingleElementConsumedOnce();
        testNullActionThrowsNullPointerException();
        testModificationDuringTraversalThrowsConcurrentModificationException();
    }

    static void testEmptyQueueReturnsFalse() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        Spliterator<Integer> s = pq.spliterator();
        List<Integer> seen = new ArrayList<>();
        boolean advanced = s.tryAdvance(x -> seen.add(x));
        if (advanced) {
            throw new AssertionError("tryAdvance on empty queue must return false");
        }
        if (!seen.isEmpty()) {
            throw new AssertionError("action must not be invoked on empty queue");
        }
    }

    static void testSingleElementConsumedOnce() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(5);
        Spliterator<Integer> s = pq.spliterator();
        List<Integer> seen = new ArrayList<>();
        if (!s.tryAdvance(x -> seen.add(x))) {
            throw new AssertionError("first tryAdvance must return true");
        }
        if (seen.size() != 1 || seen.get(0) != 5) {
            throw new AssertionError("expected single element 5, got " + seen);
        }
        if (s.tryAdvance(x -> seen.add(x))) {
            throw new AssertionError("second tryAdvance must return false");
        }
        if (seen.size() != 1) {
            throw new AssertionError("action invoked more than once: " + seen);
        }
    }

    static void testNullActionThrowsNullPointerException() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(1);
        Spliterator<Integer> s = pq.spliterator();
        try {
            s.tryAdvance(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new AssertionError("tryAdvance(null) must throw NullPointerException");
    }

    static void testModificationDuringTraversalThrowsConcurrentModificationException() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(1);
        pq.offer(2);
        Spliterator<Integer> s = pq.spliterator();
        List<Integer> seen = new ArrayList<>();
        if (!s.tryAdvance(x -> seen.add(x))) {
            throw new AssertionError("first tryAdvance must return true");
        }
        pq.offer(3);
        try {
            s.tryAdvance(x -> seen.add(x));
        } catch (ConcurrentModificationException expected) {
            return;
        }
        throw new AssertionError("expected ConcurrentModificationException after structural modification");
    }
}
