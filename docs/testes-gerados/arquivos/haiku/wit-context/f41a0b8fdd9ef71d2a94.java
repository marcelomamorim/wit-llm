/*
 * @test
 * @summary Witup-generated checks for LinkedTransferQueue.retainAll
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testNullArgumentThrowsNPE();
        testRetainsOnlyGivenElements();
        testReturnsFalseWhenNothingRemoved();
        testEmptyCollectionClearsQueue();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullArgumentThrowsNPE() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        q.add("a");
        try {
            q.retainAll(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
        check(q.size() == 1 && q.contains("a"), "queue changed after NPE");
    }

    static void testRetainsOnlyGivenElements() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        q.add("a");
        q.add("b");
        q.add("c");
        boolean changed = q.retainAll(Arrays.asList("a", "c"));
        check(changed, "retainAll should report a change");
        check(q.size() == 2, "expected size 2 but was " + q.size());
        check(q.contains("a"), "a should be retained");
        check(q.contains("c"), "c should be retained");
        check(!q.contains("b"), "b should be removed");
    }

    static void testReturnsFalseWhenNothingRemoved() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        q.add("a");
        boolean changed = q.retainAll(Arrays.asList("a", "x"));
        check(!changed, "retainAll should return false when nothing is removed");
        check(q.size() == 1 && q.contains("a"), "queue should be unchanged");
    }

    static void testEmptyCollectionClearsQueue() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        q.add("a");
        q.add("b");
        boolean changed = q.retainAll(Collections.emptyList());
        check(changed, "retainAll with empty collection should report a change");
        check(q.isEmpty(), "queue should be empty");
    }
}
