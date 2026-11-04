/*
 * @test
 * @summary Verifies observable behavior of PriorityBlockingQueue.drainTo(Collection)
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testEmptyQueueReturnsZero();
        testNaturalOrderDrain();
        testAppendsToExistingCollection();
        testQueueEmptyAfterDrain();
        testComparatorOrderDrain();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyQueueReturnsZero() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        List<Integer> out = new ArrayList<>();
        int n = q.drainTo(out);
        check(n == 0, "expected 0 drained from empty queue, got " + n);
        check(out.isEmpty(), "target should remain empty, got " + out);
    }

    static void testNaturalOrderDrain() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(5);
        q.add(1);
        q.add(3);
        List<Integer> out = new ArrayList<>();
        int n = q.drainTo(out);
        check(n == 3, "expected 3 drained, got " + n);
        check(out.equals(List.of(1, 3, 5)), "expected priority order [1, 3, 5], got " + out);
    }

    static void testAppendsToExistingCollection() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(2);
        q.add(1);
        List<Integer> out = new ArrayList<>();
        out.add(9);
        int n = q.drainTo(out);
        check(n == 2, "expected 2 drained, got " + n);
        check(out.equals(List.of(9, 1, 2)), "expected [9, 1, 2], got " + out);
    }

    static void testQueueEmptyAfterDrain() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(4);
        q.add(7);
        q.drainTo(new ArrayList<Integer>());
        check(q.isEmpty(), "queue should be empty after drainTo");
        check(q.size() == 0, "size should be 0 after drainTo, got " + q.size());
        int second = q.drainTo(new ArrayList<Integer>());
        check(second == 0, "second drain should return 0, got " + second);
    }

    static void testComparatorOrderDrain() {
        PriorityBlockingQueue<Integer> q =
            new PriorityBlockingQueue<Integer>(11, Collections.<Integer>reverseOrder());
        q.add(1);
        q.add(5);
        q.add(3);
        List<Integer> out = new ArrayList<>();
        int n = q.drainTo(out);
        check(n == 3, "expected 3 drained, got " + n);
        check(out.equals(List.of(5, 3, 1)), "expected comparator order [5, 3, 1], got " + out);
    }
}
