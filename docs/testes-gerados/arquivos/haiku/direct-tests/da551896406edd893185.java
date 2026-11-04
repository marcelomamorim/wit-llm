/*
 * @test
 * @summary Tests PriorityQueue spliterator tryAdvance: null action, empty queue, element delivery and concurrent modification detection
 * @run main PriorityQueueWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Spliterator;

public class PriorityQueueWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testEmptyQueueReturnsFalse();
        testDeliversAllElements();
        testConcurrentModificationDetected();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        Spliterator<Integer> s = pq.spliterator();
        boolean npe = false;
        try {
            s.tryAdvance(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "tryAdvance(null) must throw NullPointerException");
    }

    static void testEmptyQueueReturnsFalse() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        Spliterator<Integer> s = pq.spliterator();
        List<Integer> seen = new ArrayList<>();
        check(!s.tryAdvance(seen::add), "empty queue must return false");
        check(seen.isEmpty(), "action must not be called on empty queue");
        check(!s.tryAdvance(seen::add), "repeated call on empty queue must return false");
        check(seen.isEmpty(), "action must not be called on repeated empty call");
    }

    static void testDeliversAllElements() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(5);
        pq.add(1);
        pq.add(3);
        Spliterator<Integer> s = pq.spliterator();
        List<Integer> seen = new ArrayList<>();
        int count = 0;
        while (s.tryAdvance(seen::add)) {
            count++;
            if (count > 3) {
                throw new RuntimeException("tryAdvance returned more elements than queue size");
            }
        }
        check(count == 3, "expected 3 elements, got " + count);
        check(!s.tryAdvance(seen::add), "exhausted spliterator must return false");
        seen.sort(null);
        check(seen.size() == 3, "wrong number of delivered elements");
        check(seen.get(0) == 1 && seen.get(1) == 3 && seen.get(2) == 5,
              "delivered elements do not match queue contents: " + seen);
    }

    static void testConcurrentModificationDetected() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        pq.add(2);
        pq.add(3);
        Spliterator<Integer> s = pq.spliterator();
        List<Integer> seen = new ArrayList<>();
        check(s.tryAdvance(seen::add), "first tryAdvance must succeed");
        pq.add(4);
        boolean cme = false;
        try {
            s.tryAdvance(seen::add);
        } catch (ConcurrentModificationException e) {
            cme = true;
        }
        check(cme, "modification after spliterator creation must be detected");
    }
}
