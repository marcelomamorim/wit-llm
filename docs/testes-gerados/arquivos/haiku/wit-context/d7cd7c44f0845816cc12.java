/*
 * @test
 * @summary Witup-generated behavioral tests for LinkedBlockingQueue.removeIf
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testNullFilterThrowsNullPointerException();
        testRemovesOnlyMatchingElementsAndKeepsOrder();
        testNoMatchReturnsFalseAndLeavesQueueUnchanged();
        testEmptyQueueReturnsFalse();
        testRemoveAllFreesCapacityOnBoundedQueue();
    }

    static void testNullFilterThrowsNullPointerException() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.add(1);
        q.add(2);
        boolean thrown = false;
        try {
            q.removeIf(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError();
        }
        assertEquals(2, q.size());
    }

    static void testRemovesOnlyMatchingElementsAndKeepsOrder() {
        LinkedBlockingQueue<Integer> q =
            new LinkedBlockingQueue<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        boolean changed = q.removeIf(x -> x % 2 == 0);
        if (!changed) {
            throw new AssertionError();
        }
        assertEquals(Arrays.asList(1, 3, 5), drain(q));
    }

    static void testNoMatchReturnsFalseAndLeavesQueueUnchanged() {
        LinkedBlockingQueue<Integer> q =
            new LinkedBlockingQueue<>(Arrays.asList(1, 3, 5));
        boolean changed = q.removeIf(x -> x > 10);
        if (changed) {
            throw new AssertionError();
        }
        assertEquals(Arrays.asList(1, 3, 5), drain(q));
    }

    static void testEmptyQueueReturnsFalse() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        boolean changed = q.removeIf(x -> true);
        if (changed) {
            throw new AssertionError();
        }
        assertEquals(0, q.size());
    }

    static void testRemoveAllFreesCapacityOnBoundedQueue() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(2);
        q.add(1);
        q.add(2);
        boolean changed = q.removeIf(x -> true);
        if (!changed) {
            throw new AssertionError();
        }
        assertEquals(0, q.size());
        if (!q.offer(3) || !q.offer(4)) {
            throw new AssertionError();
        }
        assertEquals(Arrays.asList(3, 4), drain(q));
    }

    static List<Integer> drain(LinkedBlockingQueue<Integer> q) {
        List<Integer> result = new ArrayList<>();
        Integer e;
        while ((e = q.poll()) != null) {
            result.add(e);
        }
        return result;
    }

    static void assertEquals(Object expected, Object actual) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new AssertionError("expected " + expected + " but was " + actual);
        }
    }

    static void assertEquals(int expected, int actual) {
        if (expected != actual) {
            throw new AssertionError("expected " + expected + " but was " + actual);
        }
    }
}
