/*
 * @test
 * @summary Test ConcurrentLinkedQueue.retainAll(Collection<?>) behavior
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.Objects;

public class ConcurrentLinkedQueueWitupTest {
    public static void main(String[] args) {
        testRetainAllWithNullCollection();
        testRetainAllWithEmptyCollection();
        testRetainAllWithPartialRetain();
        testRetainAllWithFullRetain();
        testRetainAllWithNoRetain();
    }

    static void testRetainAllWithNullCollection() {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        try {
            q.retainAll(null);
            throw new Error("Expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testRetainAllWithEmptyCollection() {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        q.add("a");
        q.add("b");
        q.add("c");
        boolean changed = q.retainAll(Collections.emptyList());
        if (!changed) throw new Error("Expected retainAll to return true when removing elements");
        if (!q.isEmpty()) throw new Error("Expected queue to be empty after retainAll with empty collection");
    }

    static void testRetainAllWithPartialRetain() {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        q.add("a");
        q.add("b");
        q.add("c");
        Collection<String> retain = Arrays.asList("b", "c", "d");
        boolean changed = q.retainAll(retain);
        if (!changed) throw new Error("Expected retainAll to return true when removing elements");
        if (q.size() != 2) throw new Error("Expected queue size 2 after retainAll");
        if (!q.contains("b") || !q.contains("c")) throw new Error("Expected queue to contain 'b' and 'c'");
        if (q.contains("a")) throw new Error("Expected queue not to contain 'a'");
    }

    static void testRetainAllWithFullRetain() {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        q.add("a");
        q.add("b");
        Collection<String> retain = Arrays.asList("a", "b");
        boolean changed = q.retainAll(retain);
        if (changed) throw new Error("Expected retainAll to return false when no elements removed");
        if (q.size() != 2) throw new Error("Expected queue size 2 after retainAll");
        if (!q.contains("a") || !q.contains("b")) throw new Error("Expected queue to contain 'a' and 'b'");
    }

    static void testRetainAllWithNoRetain() {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        q.add("a");
        q.add("b");
        Collection<String> retain = Arrays.asList("x", "y");
        boolean changed = q.retainAll(retain);
        if (!changed) throw new Error("Expected retainAll to return true when all elements removed");
        if (!q.isEmpty()) throw new Error("Expected queue to be empty after retainAll with no matching elements");
    }
}
