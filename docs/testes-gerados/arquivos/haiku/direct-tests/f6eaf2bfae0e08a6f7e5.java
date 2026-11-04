/*
 * @test
 * @summary Basic behavioral checks for PriorityBlockingQueue spliterator tryAdvance
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.function.Consumer;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testEmptyQueueReturnsFalseWithoutCallingAction();
        testTryAdvanceDeliversSingleElement();
        testTryAdvanceVisitsEachElementOnce();
        testExhaustedSpliteratorStaysFalse();
        testNullActionThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyQueueReturnsFalseWithoutCallingAction() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        Spliterator<Integer> s = q.spliterator();
        final int[] calls = {0};
        boolean r = s.tryAdvance(x -> calls[0]++);
        check(!r, "tryAdvance on empty queue should return false");
        check(calls[0] == 0, "action must not be called on empty queue");
    }

    static void testTryAdvanceDeliversSingleElement() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(42);
        Spliterator<Integer> s = q.spliterator();
        List<Integer> seen = new ArrayList<>();
        boolean r = s.tryAdvance(x -> seen.add(x));
        check(r, "tryAdvance should return true when an element is available");
        check(seen.size() == 1, "action should be called exactly once");
        check(seen.get(0) == 42, "action should receive the queued element");
    }

    static void testTryAdvanceVisitsEachElementOnce() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(5);
        q.add(1);
        q.add(4);
        q.add(2);
        q.add(3);
        Spliterator<Integer> s = q.spliterator();
        List<Integer> seen = new ArrayList<>();
        while (s.tryAdvance(x -> seen.add(x))) {
            // keep advancing until exhausted
        }
        check(seen.size() == 5, "all 5 elements should be visited exactly once, got " + seen.size());
        int sum = 0;
        for (int v : seen) {
            sum += v;
        }
        check(sum == 15, "sum of visited elements should be 15, got " + sum);
        for (int expected = 1; expected <= 5; expected++) {
            check(seen.contains(expected), "missing element " + expected);
        }
    }

    static void testExhaustedSpliteratorStaysFalse() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(7);
        Spliterator<Integer> s = q.spliterator();
        final int[] calls = {0};
        check(s.tryAdvance(x -> calls[0]++), "first tryAdvance should succeed");
        check(!s.tryAdvance(x -> calls[0]++), "tryAdvance after exhaustion should return false");
        check(!s.tryAdvance(x -> calls[0]++), "repeated tryAdvance after exhaustion should return false");
        check(calls[0] == 1, "action should be called only once, got " + calls[0]);
    }

    static void testNullActionThrowsNPE() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(1);
        Spliterator<Integer> s = q.spliterator();
        Consumer<Integer> nullAction = null;
        boolean thrown = false;
        try {
            s.tryAdvance(nullAction);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "tryAdvance(null) should throw NullPointerException");
    }
}
