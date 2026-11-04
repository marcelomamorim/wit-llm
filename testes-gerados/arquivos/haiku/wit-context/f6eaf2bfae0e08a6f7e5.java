/*
 * @test
 * @summary WITUP-derived checks for PriorityBlockingQueue spliterator tryAdvance
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.Spliterator;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testEmptyQueueTryAdvanceReturnsFalse();
        testTryAdvanceConsumesAllElements();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(1);
        Spliterator<Integer> s = q.spliterator();
        boolean thrown = false;
        try {
            s.tryAdvance(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "tryAdvance(null) must throw NullPointerException");
        final int[] count = new int[1];
        boolean advanced = s.tryAdvance(e -> count[0]++);
        check(advanced, "element must not be consumed after rejected null action");
        check(count[0] == 1, "consumer must be invoked exactly once");
    }

    static void testEmptyQueueTryAdvanceReturnsFalse() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        Spliterator<Integer> s = q.spliterator();
        final int[] count = new int[1];
        boolean result = s.tryAdvance(e -> count[0]++);
        check(!result, "tryAdvance on empty queue must return false");
        check(count[0] == 0, "consumer must not be invoked on empty queue");
    }

    static void testTryAdvanceConsumesAllElements() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(3);
        q.add(1);
        q.add(2);
        Spliterator<Integer> s = q.spliterator();
        final int[] count = new int[1];
        final int[] sum = new int[1];
        while (s.tryAdvance(e -> { count[0]++; sum[0] += e; })) {
            // drain
        }
        check(count[0] == 3, "expected 3 elements consumed, got " + count[0]);
        check(sum[0] == 6, "expected sum 6, got " + sum[0]);
        check(!s.tryAdvance(e -> count[0]++), "tryAdvance must return false after exhaustion");
        check(count[0] == 3, "no extra element may be consumed after exhaustion");
    }
}
