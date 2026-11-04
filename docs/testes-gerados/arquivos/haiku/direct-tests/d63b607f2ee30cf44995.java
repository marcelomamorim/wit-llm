/*
 * @test
 * @summary Checks LinkedBlockingQueue iterator forEachRemaining behavior
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) {
        testRemainingElementsInOrder();
        testEmptyQueue();
        testBatchBoundary();
        testNullActionThrowsNpe();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRemainingElementsInOrder() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        for (int i = 1; i <= 5; i++) {
            q.add(i);
        }
        Iterator<Integer> it = q.iterator();
        check(it.next() == 1, "first element should be 1");
        List<Integer> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.equals(Arrays.asList(2, 3, 4, 5)),
              "remaining elements in order, got " + seen);
        check(!it.hasNext(), "iterator should be exhausted");
        check(q.size() == 5, "forEachRemaining must not remove elements");
    }

    static void testEmptyQueue() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        int[] calls = new int[1];
        q.iterator().forEachRemaining(s -> calls[0]++);
        check(calls[0] == 0, "no action calls expected on empty queue");
    }

    static void testBatchBoundary() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        for (int i = 0; i < 200; i++) {
            q.add(i);
        }
        Iterator<Integer> it = q.iterator();
        check(it.next() == 0, "first element should be 0");
        List<Integer> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.size() == 199, "expected 199 remaining, got " + seen.size());
        for (int i = 0; i < seen.size(); i++) {
            check(seen.get(i) == i + 1, "order mismatch at index " + i);
        }
    }

    static void testNullActionThrowsNpe() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.add(1);
        q.add(2);
        Iterator<Integer> it = q.iterator();
        it.next();
        boolean thrown = false;
        try {
            it.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null action should throw NullPointerException");
    }
}
