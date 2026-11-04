/*
 * @test
 * @summary Tests LinkedBlockingQueue iterator forEachRemaining behavior through the public iterator API
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testForEachRemainingConsumesAll();
        testForEachRemainingAfterNext();
        testForEachRemainingEmptyQueue();
        testForEachRemainingSecondCallIsNoOp();
        testForEachRemainingNullActionThrowsNpe();
        System.out.println("PASS");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testForEachRemainingConsumesAll() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(Arrays.asList(1, 2, 3));
        Iterator<Integer> it = q.iterator();
        List<Integer> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.equals(Arrays.asList(1, 2, 3)), "expected [1, 2, 3] but got " + seen);
        check(!it.hasNext(), "iterator should be exhausted");
        check(q.size() == 3, "iteration must not remove elements from the queue");
    }

    static void testForEachRemainingAfterNext() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(Arrays.asList(1, 2, 3));
        Iterator<Integer> it = q.iterator();
        check(it.next() == 1, "first next() should return 1");
        List<Integer> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.equals(Arrays.asList(2, 3)), "expected [2, 3] but got " + seen);
        check(!it.hasNext(), "iterator should be exhausted");
    }

    static void testForEachRemainingEmptyQueue() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        Iterator<Integer> it = q.iterator();
        List<Integer> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.isEmpty(), "action must not be called for an empty queue");
        check(!it.hasNext(), "iterator over empty queue should have no next");
    }

    static void testForEachRemainingSecondCallIsNoOp() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(Arrays.asList(10, 20));
        Iterator<Integer> it = q.iterator();
        List<Integer> first = new ArrayList<>();
        it.forEachRemaining(first::add);
        check(first.equals(Arrays.asList(10, 20)), "first pass expected [10, 20] but got " + first);
        List<Integer> second = new ArrayList<>();
        it.forEachRemaining(second::add);
        check(second.isEmpty(), "second forEachRemaining must not revisit elements");
    }

    static void testForEachRemainingNullActionThrowsNpe() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(Arrays.asList(1, 2));
        Iterator<Integer> it = q.iterator();
        boolean thrown = false;
        try {
            it.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null action should throw NullPointerException");
        check(it.hasNext(), "failed call should not exhaust the iterator");
        check(it.next() == 1, "element 1 should still be available after failed call");
    }
}
