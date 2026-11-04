/*
 * @test
 * @summary Verify ConcurrentLinkedQueue.removeAll(Collection) removes matching elements, reports changes, and rejects null
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRemovesMatchingElements() {
        ConcurrentLinkedQueue<String> q =
            new ConcurrentLinkedQueue<String>(Arrays.asList("a", "b", "c", "d"));
        boolean changed = q.removeAll(Arrays.asList("b", "d"));
        check(changed, "removeAll should return true when elements are removed");
        check(q.size() == 2, "size should be 2, was " + q.size());
        check(!q.contains("b"), "b should be removed");
        check(!q.contains("d"), "d should be removed");
        check(q.contains("a") && q.contains("c"), "a and c should remain");
        check("a".equals(q.poll()), "head should remain a");
        check("c".equals(q.poll()), "next should remain c");
        check(q.poll() == null, "queue should be empty");
    }

    static void testNoMatchReturnsFalse() {
        ConcurrentLinkedQueue<String> q =
            new ConcurrentLinkedQueue<String>(Arrays.asList("1", "2"));
        boolean changed = q.removeAll(Arrays.asList("x"));
        check(!changed, "removeAll should return false when nothing matches");
        check(q.size() == 2, "size should remain 2, was " + q.size());
        check(q.contains("1") && q.contains("2"), "elements should be unchanged");
    }

    static void testEmptyArgumentCollection() {
        ConcurrentLinkedQueue<String> q =
            new ConcurrentLinkedQueue<String>(Arrays.asList("p", "q"));
        boolean changed = q.removeAll(Collections.emptyList());
        check(!changed, "removeAll with empty collection should return false");
        check(q.size() == 2, "size should remain 2, was " + q.size());
    }

    static void testRemoveAllElements() {
        ConcurrentLinkedQueue<String> q =
            new ConcurrentLinkedQueue<String>(Arrays.asList("a", "b"));
        boolean changed = q.removeAll(Arrays.asList("a", "b"));
        check(changed, "removeAll should return true when all elements are removed");
        check(q.isEmpty(), "queue should be empty");
    }

    static void testNullArgumentThrowsNPE() {
        ConcurrentLinkedQueue<String> q =
            new ConcurrentLinkedQueue<String>(Arrays.asList("a"));
        try {
            q.removeAll(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected: Objects.requireNonNull(c)
        }
        check(q.size() == 1, "queue should be unchanged after NPE");
    }

    public static void main(String[] args) {
        testRemovesMatchingElements();
        testNoMatchReturnsFalse();
        testEmptyArgumentCollection();
        testRemoveAllElements();
        testNullArgumentThrowsNPE();
    }
}
