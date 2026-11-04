/*
 * @test
 * @summary Verifies that forEach on Collections.asLifoQueue visits elements in the backing deque's order
 * @run main CollectionsWitupTest
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Queue;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testForEachMatchesBackingDequeOrder();
        testForEachOnEmptyQueueVisitsNothing();
        testForEachReflectsBackingDequeChanges();
    }

    static void testForEachMatchesBackingDequeOrder() {
        Deque<Integer> deque = new ArrayDeque<>();
        Queue<Integer> lifo = Collections.asLifoQueue(deque);
        lifo.add(1);
        lifo.add(2);
        lifo.add(3);

        List<Integer> expected = new ArrayList<>();
        for (Integer x : deque) {
            expected.add(x);
        }

        List<Integer> seen = new ArrayList<>();
        lifo.forEach(seen::add);

        if (!expected.equals(seen)) {
            throw new RuntimeException("forEach order mismatch: expected " + expected + " but was " + seen);
        }
        if (seen.size() != 3) {
            throw new RuntimeException("forEach visited wrong count: " + seen.size());
        }
    }

    static void testForEachOnEmptyQueueVisitsNothing() {
        Queue<Integer> lifo = Collections.asLifoQueue(new ArrayDeque<Integer>());
        List<Integer> seen = new ArrayList<>();
        lifo.forEach(seen::add);
        if (!seen.isEmpty()) {
            throw new RuntimeException("forEach on empty queue visited elements: " + seen);
        }
    }

    static void testForEachReflectsBackingDequeChanges() {
        Deque<Integer> deque = new ArrayDeque<>();
        Queue<Integer> lifo = Collections.asLifoQueue(deque);
        lifo.add(5);
        deque.addLast(9);

        List<Integer> expected = new ArrayList<>();
        for (Integer x : deque) {
            expected.add(x);
        }

        List<Integer> seen = new ArrayList<>();
        lifo.forEach(seen::add);

        if (!expected.equals(seen)) {
            throw new RuntimeException("forEach did not reflect backing deque: expected " + expected + " but was " + seen);
        }
    }
}
