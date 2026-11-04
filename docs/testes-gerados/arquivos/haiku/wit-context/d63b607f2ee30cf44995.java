/*
 * @test
 * @summary Checks Itr.forEachRemaining in LinkedBlockingQueue: null action rejection, ordering, partial consumption and batch boundaries
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testNullActionOnNonEmptyQueueThrowsNpe();
        testNullActionOnEmptyQueueThrowsNpe();
        testForEachRemainingVisitsAllElementsInOrder();
        testForEachRemainingAfterNextSkipsConsumedElement();
        testForEachRemainingOnEmptyQueueDoesNotInvokeAction();
        testForEachRemainingExhaustsIterator();
        testForEachRemainingAcrossBatchBoundary();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionOnNonEmptyQueueThrowsNpe() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.add(1);
        q.add(2);
        Iterator<Integer> it = q.iterator();
        try {
            it.forEachRemaining(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNullActionOnEmptyQueueThrowsNpe() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        Iterator<Integer> it = q.iterator();
        try {
            it.forEachRemaining(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachRemainingVisitsAllElementsInOrder() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        for (int i = 1; i <= 5; i++) {
            q.add(i);
        }
        List<Integer> seen = new ArrayList<>();
        q.iterator().forEachRemaining(seen::add);
        check(seen.size() == 5, "expected 5 elements, got " + seen.size());
        for (int i = 0; i < 5; i++) {
            check(seen.get(i) == i + 1, "wrong element at index " + i + ": " + seen.get(i));
        }
    }

    static void testForEachRemainingAfterNextSkipsConsumedElement() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        for (int i = 1; i <= 5; i++) {
            q.add(i);
        }
        Iterator<Integer> it = q.iterator();
        check(it.next() == 1, "first next() should return 1");
        List<Integer> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.size() == 4, "expected 4 remaining elements, got " + seen.size());
        for (int i = 0; i < 4; i++) {
            check(seen.get(i) == i + 2, "wrong remaining element at index " + i);
        }
    }

    static void testForEachRemainingOnEmptyQueueDoesNotInvokeAction() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        final int[] calls = {0};
        q.iterator().forEachRemaining(e -> calls[0]++);
        check(calls[0] == 0, "action invoked on empty queue");
    }

    static void testForEachRemainingExhaustsIterator() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.add(10);
        q.add(20);
        Iterator<Integer> it = q.iterator();
        it.forEachRemaining(e -> { });
        check(!it.hasNext(), "iterator should have no next element after forEachRemaining");
    }

    static void testForEachRemainingAcrossBatchBoundary() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        for (int i = 0; i < 200; i++) {
            q.add(i);
        }
        List<Integer> seen = new ArrayList<>();
        q.iterator().forEachRemaining(seen::add);
        check(seen.size() == 200, "expected 200 elements, got " + seen.size());
        for (int i = 0; i < 200; i++) {
            check(seen.get(i) == i, "wrong element at index " + i + ": " + seen.get(i));
        }
    }
}
