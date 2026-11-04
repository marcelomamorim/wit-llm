/*
 * @test
 * @summary ConcurrentLinkedQueue iterator forEachRemaining observable behavior
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueWitupTest {

    public static void main(String[] args) {
        testRemainingElementsInOrder();
        testEmptyQueueDoesNotInvokeAction();
        testExhaustedIteratorDoesNotInvokeAction();
        testNullActionThrowsNPEAndKeepsElements();
        testNullActionOnExhaustedIteratorThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRemainingElementsInOrder() {
        ConcurrentLinkedQueue<String> q =
            new ConcurrentLinkedQueue<>(Arrays.asList("a", "b", "c", "d"));
        Iterator<String> it = q.iterator();
        check("a".equals(it.next()), "first element should be a");
        List<String> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.equals(Arrays.asList("b", "c", "d")),
              "forEachRemaining should visit remaining elements in order, got " + seen);
        check(!it.hasNext(), "iterator should be exhausted after forEachRemaining");
        check(q.size() == 4, "forEachRemaining must not remove elements from the queue");
    }

    static void testEmptyQueueDoesNotInvokeAction() {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        Iterator<String> it = q.iterator();
        int[] count = new int[1];
        it.forEachRemaining(x -> count[0]++);
        check(count[0] == 0, "action should not be invoked for an empty queue");
        check(!it.hasNext(), "empty iterator should have no next element");
    }

    static void testExhaustedIteratorDoesNotInvokeAction() {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>(Arrays.asList("x"));
        Iterator<String> it = q.iterator();
        it.next();
        int[] count = new int[1];
        it.forEachRemaining(x -> count[0]++);
        check(count[0] == 0, "no elements remain, action should not be invoked");
        it.forEachRemaining(x -> count[0]++);
        check(count[0] == 0, "second forEachRemaining on exhausted iterator should do nothing");
    }

    static void testNullActionThrowsNPEAndKeepsElements() {
        ConcurrentLinkedQueue<String> q =
            new ConcurrentLinkedQueue<>(Arrays.asList("a", "b"));
        Iterator<String> it = q.iterator();
        boolean thrown = false;
        try {
            it.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null action should throw NullPointerException");
        check(it.hasNext(), "null action must be rejected before consuming elements");
        check("a".equals(it.next()), "element a should still be available after NPE");
    }

    static void testNullActionOnExhaustedIteratorThrowsNPE() {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>(Arrays.asList("z"));
        Iterator<String> it = q.iterator();
        it.forEachRemaining(x -> { });
        boolean thrown = false;
        try {
            it.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null action should throw NullPointerException even when exhausted");
    }
}
