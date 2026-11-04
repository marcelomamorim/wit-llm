/*
 * @test
 * @summary Black-box checks for PriorityBlockingQueue.drainTo(Collection, int)
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullCollectionThrowsNPE() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.offer(1);
        boolean thrown = false;
        try {
            q.drainTo(null, 1);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "drainTo(null, n) must throw NullPointerException");
        check(q.size() == 1, "queue must be unchanged after NPE");
    }

    static void testSelfDrainThrowsIAE() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.offer(1);
        q.offer(2);
        boolean thrown = false;
        try {
            q.drainTo(q, 2);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "drainTo(this, n) must throw IllegalArgumentException");
        check(q.size() == 2, "queue must be unchanged after IAE");
    }

    static void testNonPositiveMaxReturnsZero() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.offer(3);
        q.offer(1);
        List<Integer> out = new ArrayList<>();
        check(q.drainTo(out, 0) == 0, "maxElements=0 must return 0");
        check(q.drainTo(out, -5) == 0, "negative maxElements must return 0");
        check(out.isEmpty(), "collection must not receive elements");
        check(q.size() == 2, "queue must be unchanged");
    }

    static void testEmptyQueueReturnsZero() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        List<Integer> out = new ArrayList<>();
        check(q.drainTo(out, 10) == 0, "empty queue must drain 0 elements");
        check(out.isEmpty(), "collection must stay empty");
    }

    static void testDrainAllInPriorityOrder() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.offer(5);
        q.offer(1);
        q.offer(3);
        List<Integer> out = new ArrayList<>();
        int n = q.drainTo(out, 100);
        check(n == 3, "expected 3 drained, got " + n);
        check(out.equals(List.of(1, 3, 5)), "expected [1, 3, 5], got " + out);
        check(q.isEmpty(), "queue must be empty after full drain");
    }

    static void testPartialDrainRespectsLimit() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.offer(5);
        q.offer(1);
        q.offer(3);
        q.offer(2);
        List<Integer> out = new ArrayList<>();
        int n = q.drainTo(out, 2);
        check(n == 2, "expected 2 drained, got " + n);
        check(out.equals(List.of(1, 2)), "expected [1, 2], got " + out);
        check(q.size() == 2, "expected 2 remaining, got " + q.size());
        check(q.poll() == 3, "next element must be 3");
        check(q.poll() == 5, "last element must be 5");
    }

    static void testAppendsToExistingCollection() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.offer(2);
        q.offer(1);
        Collection<Integer> out = new ArrayList<>();
        out.add(9);
        int n = q.drainTo(out, 5);
        check(n == 2, "expected 2 drained, got " + n);
        check(out.equals(List.of(9, 1, 2)), "expected [9, 1, 2], got " + out);
    }

    public static void main(String[] args) {
        testNullCollectionThrowsNPE();
        testSelfDrainThrowsIAE();
        testNonPositiveMaxReturnsZero();
        testEmptyQueueReturnsZero();
        testDrainAllInPriorityOrder();
        testPartialDrainRespectsLimit();
        testAppendsToExistingCollection();
    }
}
