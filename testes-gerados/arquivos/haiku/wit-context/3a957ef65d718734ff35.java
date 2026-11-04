/*
 * @test
 * @summary Witup-generated checks for LinkedTransferQueue.removeAll(Collection)
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testNullArgumentThrowsNPE();
        testRemovesMatchingElements();
        testNoMatchReturnsFalse();
        testEmptyCollectionRemovesNothing();
    }

    static void testNullArgumentThrowsNPE() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>(Arrays.asList("a", "b"));
        boolean thrown = false;
        try {
            q.removeAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "removeAll(null) must throw NullPointerException");
        check(q.size() == 2, "queue must be unchanged after NPE");
    }

    static void testRemovesMatchingElements() {
        LinkedTransferQueue<String> q =
                new LinkedTransferQueue<>(Arrays.asList("a", "b", "c", "a"));
        boolean changed = q.removeAll(Arrays.asList("a", "c"));
        check(changed, "removeAll must return true when elements are removed");
        check(!q.contains("a"), "all 'a' elements must be removed");
        check(!q.contains("c"), "'c' must be removed");
        check(q.contains("b"), "'b' must remain");
        check(q.size() == 1, "only one element must remain, was " + q.size());
    }

    static void testNoMatchReturnsFalse() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>(Arrays.asList("a", "b"));
        boolean changed = q.removeAll(Arrays.asList("x"));
        check(!changed, "removeAll must return false when nothing is removed");
        check(q.size() == 2, "queue size must stay 2");
        check(q.contains("a") && q.contains("b"), "original elements must remain");
    }

    static void testEmptyCollectionRemovesNothing() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>(Arrays.asList("a", "b", "c"));
        boolean changed = q.removeAll(Collections.<String>emptyList());
        check(!changed, "removeAll with empty collection must return false");
        check(q.size() == 3, "queue size must stay 3");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
