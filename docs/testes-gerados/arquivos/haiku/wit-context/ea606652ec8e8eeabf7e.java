/*
 * @test
 * @summary WITUP-derived tests for PriorityBlockingQueue.drainTo(Collection, int)
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testNullCollectionThrowsNPE();
        testDrainToSelfThrowsIAE();
        testNonPositiveMaxReturnsZero();
        testDrainsInPriorityOrderLimited();
        testDrainsAllWhenMaxExceedsSize();
        testEmptyQueueReturnsZero();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullCollectionThrowsNPE() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(1);
        try {
            q.drainTo(null, 1);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
        check(q.size() == 1, "queue must be unchanged after NPE");
    }

    static void testDrainToSelfThrowsIAE() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(1);
        try {
            q.drainTo(q, 1);
            throw new RuntimeException("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
        check(q.size() == 1, "queue must be unchanged after IAE");
    }

    static void testNonPositiveMaxReturnsZero() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(1);
        q.add(2);
        List<Integer> c = new ArrayList<>();
        check(q.drainTo(c, 0) == 0, "maxElements 0 must return 0");
        check(q.drainTo(c, -1) == 0, "negative maxElements must return 0");
        check(c.isEmpty(), "collection must stay empty");
        check(q.size() == 2, "queue must be unchanged");
    }

    static void testDrainsInPriorityOrderLimited() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(5);
        q.add(1);
        q.add(3);
        q.add(2);
        q.add(4);
        List<Integer> c = new ArrayList<>();
        check(q.drainTo(c, 3) == 3, "must drain exactly 3 elements");
        check(Arrays.asList(1, 2, 3).equals(c), "drained elements must be in priority order");
        check(q.size() == 2, "two elements must remain");
        List<Integer> rest = new ArrayList<>();
        check(q.drainTo(rest, 10) == 2, "must drain remaining 2 elements");
        check(Arrays.asList(4, 5).equals(rest), "remaining elements must be in priority order");
        check(q.isEmpty(), "queue must be empty");
    }

    static void testDrainsAllWhenMaxExceedsSize() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(3);
        q.add(1);
        q.add(2);
        List<Integer> c = new ArrayList<>();
        check(q.drainTo(c, 100) == 3, "must drain all 3 elements");
        check(Arrays.asList(1, 2, 3).equals(c), "drained elements must be in priority order");
        check(q.isEmpty(), "queue must be empty");
    }

    static void testEmptyQueueReturnsZero() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        List<Integer> c = new ArrayList<>();
        check(q.drainTo(c, 5) == 0, "empty queue must drain 0 elements");
        check(c.isEmpty(), "collection must stay empty");
    }
}
