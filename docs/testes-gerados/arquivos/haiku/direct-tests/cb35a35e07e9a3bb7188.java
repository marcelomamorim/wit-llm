/*
 * @test
 * @summary Checks PriorityQueue iterator next() behavior: element coverage, NoSuchElementException on empty queue, ConcurrentModificationException after structural change, and element accounting when remove() is used during iteration
 * @run main PriorityQueueWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Set;

public class PriorityQueueWitupTest {

    public static void main(String[] args) {
        testIterateAllElements();
        testEmptyNextThrows();
        testConcurrentModificationDetected();
        testRemoveDuringIteration();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testIterateAllElements() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int[] vals = {5, 3, 8, 1, 9, 2, 7};
        Set<Integer> expected = new HashSet<>();
        for (int v : vals) {
            pq.add(v);
            expected.add(v);
        }
        Iterator<Integer> it = pq.iterator();
        Set<Integer> seen = new HashSet<>();
        int count = 0;
        while (it.hasNext()) {
            seen.add(it.next());
            count++;
        }
        check(count == vals.length, "expected " + vals.length + " elements, got " + count);
        check(seen.equals(expected), "iterated elements differ from inserted elements");
    }

    static void testEmptyNextThrows() {
        Iterator<Integer> it = new PriorityQueue<Integer>().iterator();
        try {
            it.next();
            throw new RuntimeException("expected NoSuchElementException on empty queue");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testConcurrentModificationDetected() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        pq.add(2);
        pq.add(3);
        Iterator<Integer> it = pq.iterator();
        it.next();
        pq.add(4);
        try {
            it.next();
            throw new RuntimeException("expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }

    static void testRemoveDuringIteration() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        Set<Integer> expected = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            pq.add(i * 3 % 10);
            expected.add(i * 3 % 10);
        }
        int n = expected.size();
        Iterator<Integer> it = pq.iterator();
        Set<Integer> seen = new HashSet<>();
        int count = 0;
        while (it.hasNext()) {
            Integer x = it.next();
            seen.add(x);
            count++;
            it.remove();
        }
        check(count == n, "each element should be returned exactly once, expected " + n + " got " + count);
        check(seen.equals(expected), "returned elements differ from original elements");
        check(pq.isEmpty(), "queue should be empty after removing all iterated elements");
    }
}
