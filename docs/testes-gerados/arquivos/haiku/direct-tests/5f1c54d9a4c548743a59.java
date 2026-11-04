/*
 * @test
 * @summary Tests for ArrayBlockingQueue.retainAll observable behavior
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testNullArgument();
        testRetainSubset();
        testNothingRemoved();
        testEmptyCollectionClears();
        testEmptyQueue();
        testDuplicatesKept();
        testFairQueueSameResult();
        testFifoOrderPreserved();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static List<Integer> contents(ArrayBlockingQueue<Integer> q) {
        List<Integer> l = new ArrayList<>();
        for (Integer i : q) {
            l.add(i);
        }
        return l;
    }

    static ArrayBlockingQueue<Integer> queueOf(boolean fair, Integer... xs) {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(10, fair);
        for (Integer x : xs) {
            q.add(x);
        }
        return q;
    }

    static void testNullArgument() {
        ArrayBlockingQueue<Integer> q = queueOf(false, 1, 2);
        boolean thrown = false;
        try {
            q.retainAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "NPE expected for null collection");
        check(contents(q).equals(Arrays.asList(1, 2)), "queue modified after NPE");
    }

    static void testRetainSubset() {
        ArrayBlockingQueue<Integer> q = queueOf(false, 1, 2, 3, 4);
        boolean changed = q.retainAll(Arrays.asList(2, 4, 6));
        check(changed, "retainAll should report change");
        check(contents(q).equals(Arrays.asList(2, 4)), "expected [2, 4]");
        check(q.size() == 2, "size should be 2");
    }

    static void testNothingRemoved() {
        ArrayBlockingQueue<Integer> q = queueOf(false, 1, 2);
        boolean changed = q.retainAll(Arrays.asList(1, 2, 3));
        check(!changed, "retainAll should return false when nothing removed");
        check(contents(q).equals(Arrays.asList(1, 2)), "contents changed unexpectedly");
    }

    static void testEmptyCollectionClears() {
        ArrayBlockingQueue<Integer> q = queueOf(false, 1, 2, 3);
        boolean changed = q.retainAll(Collections.emptyList());
        check(changed, "retainAll with empty collection should report change");
        check(q.isEmpty(), "queue should be empty");
    }

    static void testEmptyQueue() {
        ArrayBlockingQueue<Integer> q = queueOf(false);
        boolean changed = q.retainAll(Arrays.asList(1, 2));
        check(!changed, "empty queue should report no change");
        check(q.isEmpty(), "empty queue should remain empty");
    }

    static void testDuplicatesKept() {
        ArrayBlockingQueue<Integer> q = queueOf(false, 1, 1, 2);
        boolean changed = q.retainAll(Arrays.asList(1));
        check(changed, "retainAll should report change");
        check(contents(q).equals(Arrays.asList(1, 1)), "expected duplicates [1, 1]");
    }

    static void testFairQueueSameResult() {
        ArrayBlockingQueue<Integer> q = queueOf(true, 1, 2, 3);
        boolean changed = q.retainAll(Arrays.asList(1, 3));
        check(changed, "fair queue retainAll should report change");
        check(contents(q).equals(Arrays.asList(1, 3)), "fair queue expected [1, 3]");
    }

    static void testFifoOrderPreserved() {
        ArrayBlockingQueue<Integer> q = queueOf(false, 5, 3, 1, 4);
        q.retainAll(Arrays.asList(4, 5, 1));
        check(contents(q).equals(Arrays.asList(5, 1, 4)), "FIFO order not preserved");
        check(q.poll() == 5, "head should be 5");
        check(q.poll() == 1, "next head should be 1");
        check(q.poll() == 4, "tail should be 4");
        check(q.poll() == null, "queue should be empty");
    }
}
