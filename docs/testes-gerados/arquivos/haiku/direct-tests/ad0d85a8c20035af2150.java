/*
 * @test
 * @summary Black-box tests for PriorityBlockingQueue.removeIf covering removal, no-match, empty queue, null predicate and heap order after removal
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static PriorityBlockingQueue<Integer> newQueue(int... values) {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        for (int v : values) {
            q.add(v);
        }
        return q;
    }

    static List<Integer> drain(PriorityBlockingQueue<Integer> q) {
        List<Integer> out = new ArrayList<>();
        Integer x;
        while ((x = q.poll()) != null) {
            out.add(x);
        }
        return out;
    }

    static void testRemoveSomeReturnsTrueAndRemovesMatches() {
        PriorityBlockingQueue<Integer> q = newQueue(5, 2, 8, 1, 4, 7, 6, 3);
        boolean changed = q.removeIf(x -> x % 2 == 0);
        check(changed, "removeIf should return true when elements are removed");
        check(q.size() == 4, "expected size 4 but was " + q.size());
        check(!q.contains(2) && !q.contains(4) && !q.contains(6) && !q.contains(8),
              "even elements must be removed");
        List<Integer> remaining = drain(q);
        check(remaining.equals(Arrays.asList(1, 3, 5, 7)),
              "unexpected remaining order: " + remaining);
    }

    static void testNoMatchReturnsFalseAndKeepsElements() {
        PriorityBlockingQueue<Integer> q = newQueue(3, 1, 2);
        boolean changed = q.removeIf(x -> x > 100);
        check(!changed, "removeIf should return false when nothing matches");
        check(q.size() == 3, "size must remain 3");
        check(drain(q).equals(Arrays.asList(1, 2, 3)), "elements must be unchanged");
    }

    static void testEmptyQueueReturnsFalse() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        boolean changed = q.removeIf(x -> true);
        check(!changed, "removeIf on empty queue should return false");
        check(q.isEmpty(), "queue must remain empty");
    }

    static void testRemoveAllLeavesEmptyQueue() {
        PriorityBlockingQueue<Integer> q = newQueue(4, 9, 1);
        boolean changed = q.removeIf(x -> true);
        check(changed, "removeIf removing all elements should return true");
        check(q.isEmpty(), "queue should be empty");
        check(q.poll() == null, "poll on emptied queue should return null");
    }

    static void testNullPredicateThrowsNPE() {
        PriorityBlockingQueue<Integer> q = newQueue(1, 2);
        boolean thrown = false;
        try {
            q.removeIf(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "removeIf(null) must throw NullPointerException");
        check(q.size() == 2, "queue must be unchanged after failed call");
    }

    static void testHeapOrderPreservedAfterRemoval() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>(11, Comparator.reverseOrder());
        for (int i = 0; i < 20; i++) {
            q.add(i);
        }
        boolean changed = q.removeIf(x -> x % 3 == 0);
        check(changed, "removeIf should report removal");
        List<Integer> out = drain(q);
        List<Integer> expected = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            if (i % 3 != 0) {
                expected.add(i);
            }
        }
        Collections.sort(expected, Comparator.reverseOrder());
        check(out.equals(expected), "heap order broken after removeIf: " + out);
    }

    public static void main(String[] args) {
        testRemoveSomeReturnsTrueAndRemovesMatches();
        testNoMatchReturnsFalseAndKeepsElements();
        testEmptyQueueReturnsFalse();
        testRemoveAllLeavesEmptyQueue();
        testNullPredicateThrowsNPE();
        testHeapOrderPreservedAfterRemoval();
    }
}
