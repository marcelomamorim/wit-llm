/*
 * @test
 * @summary Checks ArrayBlockingQueue iterator forEachRemaining observable behavior
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testForEachRemainingFreshIterator() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(10);
        q.add(1);
        q.add(2);
        q.add(3);
        Iterator<Integer> it = q.iterator();
        List<Integer> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.equals(List.of(1, 2, 3)), "expected [1, 2, 3] but got " + seen);
        check(!it.hasNext(), "iterator should be exhausted after forEachRemaining");
    }

    static void testForEachRemainingAfterNext() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(10);
        q.add(1);
        q.add(2);
        q.add(3);
        Iterator<Integer> it = q.iterator();
        check(it.next() == 1, "first next() should be 1");
        List<Integer> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.equals(List.of(2, 3)), "expected [2, 3] but got " + seen);
    }

    static void testForEachRemainingEmptyQueue() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(5);
        Iterator<Integer> it = q.iterator();
        List<Integer> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.isEmpty(), "empty queue should produce no elements");
    }

    static void testForEachRemainingNullAction() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(5);
        q.add(1);
        Iterator<Integer> it = q.iterator();
        try {
            it.forEachRemaining(null);
            throw new AssertionError("expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    public static void main(String[] args) {
        testForEachRemainingFreshIterator();
        testForEachRemainingAfterNext();
        testForEachRemainingEmptyQueue();
        testForEachRemainingNullAction();
    }
}
