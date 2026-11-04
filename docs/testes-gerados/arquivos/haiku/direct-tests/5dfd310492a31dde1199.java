/*
 * @test
 * @summary ConcurrentLinkedQueue.retainAll keeps only elements contained in the given collection and rejects null
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueWitupTest {

    public static void main(String[] args) {
        testNullArgumentThrowsNpe();
        testRetainSubset();
        testRetainAllPresentReturnsFalse();
        testEmptyQueueReturnsFalse();
        testRetainNothingEmptiesQueue();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static List<Integer> contents(ConcurrentLinkedQueue<Integer> q) {
        return new ArrayList<>(q);
    }

    static void testNullArgumentThrowsNpe() {
        ConcurrentLinkedQueue<Integer> q = new ConcurrentLinkedQueue<>(Arrays.asList(1, 2));
        boolean thrown = false;
        try {
            q.retainAll(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        check(thrown, "retainAll(null) must throw NullPointerException");
        check(contents(q).equals(Arrays.asList(1, 2)), "queue must be unchanged after NPE");
    }

    static void testRetainSubset() {
        ConcurrentLinkedQueue<Integer> q = new ConcurrentLinkedQueue<>(Arrays.asList(1, 2, 3));
        boolean modified = q.retainAll(Arrays.asList(2, 3, 4));
        check(modified, "retainAll must return true when elements are removed");
        check(contents(q).equals(Arrays.asList(2, 3)), "expected [2, 3] but was " + contents(q));
        check(q.size() == 2, "size must be 2");
    }

    static void testRetainAllPresentReturnsFalse() {
        ConcurrentLinkedQueue<Integer> q = new ConcurrentLinkedQueue<>(Arrays.asList(1, 2));
        boolean modified = q.retainAll(Arrays.asList(1, 2, 3));
        check(!modified, "retainAll must return false when nothing is removed");
        check(contents(q).equals(Arrays.asList(1, 2)), "queue must still be [1, 2]");
    }

    static void testEmptyQueueReturnsFalse() {
        ConcurrentLinkedQueue<Integer> q = new ConcurrentLinkedQueue<>();
        boolean modified = q.retainAll(Arrays.asList(1, 2));
        check(!modified, "retainAll on empty queue must return false");
        check(q.isEmpty(), "queue must remain empty");
    }

    static void testRetainNothingEmptiesQueue() {
        ConcurrentLinkedQueue<Integer> q = new ConcurrentLinkedQueue<>(Arrays.asList(1, 2));
        boolean modified = q.retainAll(Collections.emptyList());
        check(modified, "retainAll with empty collection must return true for non-empty queue");
        check(q.isEmpty(), "queue must be empty after retaining nothing");
    }
}
