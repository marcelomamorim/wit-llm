/*
 * @test
 * @summary Tests for ArrayBlockingQueue.removeIf null-filter rejection and observable filtering behavior
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.Predicate;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testNullFilterThrowsNullPointerException();
        testNullFilterLeavesQueueUnchanged();
        testRemoveIfRemovesMatchingElements();
        testRemoveIfWithNoMatchReturnsFalse();
        testRemoveIfOnEmptyQueueReturnsFalse();
        testRemoveIfRemovesAllElements();
    }

    static void testNullFilterThrowsNullPointerException() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(5);
        q.add(1);
        try {
            q.removeIf(null);
            fail("expected NullPointerException for null filter");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNullFilterLeavesQueueUnchanged() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(5);
        q.add(1);
        q.add(2);
        try {
            q.removeIf(null);
            fail("expected NullPointerException for null filter");
        } catch (NullPointerException expected) {
            // expected
        }
        check(q.size() == 2, "size changed after null filter: " + q.size());
        check(q.poll() == 1, "head changed after null filter");
        check(q.poll() == 2, "second element changed after null filter");
    }

    static void testRemoveIfRemovesMatchingElements() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(10);
        for (int i = 1; i <= 5; i++) {
            q.add(i);
        }
        Predicate<Integer> even = x -> x % 2 == 0;
        boolean changed = q.removeIf(even);
        check(changed, "removeIf should report change when matches exist");
        check(q.size() == 3, "expected size 3, was " + q.size());
        check(q.poll() == 1, "expected head 1");
        check(q.poll() == 3, "expected 3 next");
        check(q.poll() == 5, "expected 5 next");
        check(q.poll() == null, "queue should be empty");
    }

    static void testRemoveIfWithNoMatchReturnsFalse() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(5);
        q.add(1);
        q.add(3);
        boolean changed = q.removeIf(x -> x > 100);
        check(!changed, "removeIf should return false when nothing matches");
        check(q.size() == 2, "size should remain 2, was " + q.size());
        check(q.poll() == 1, "head should remain 1");
        check(q.poll() == 3, "second should remain 3");
    }

    static void testRemoveIfOnEmptyQueueReturnsFalse() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        boolean changed = q.removeIf(x -> true);
        check(!changed, "removeIf on empty queue should return false");
        check(q.isEmpty(), "queue should remain empty");
    }

    static void testRemoveIfRemovesAllElements() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(4);
        q.add(7);
        q.add(8);
        q.add(9);
        boolean changed = q.removeIf(x -> true);
        check(changed, "removeIf should report change when all removed");
        check(q.isEmpty(), "queue should be empty after removing all");
        check(q.size() == 0, "size should be 0");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void fail(String message) {
        throw new RuntimeException(message);
    }
}
