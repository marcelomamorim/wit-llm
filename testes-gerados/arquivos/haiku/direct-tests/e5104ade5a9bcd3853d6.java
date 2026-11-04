/*
 * @test
 * @summary Verifies PriorityBlockingQueue.removeAll removes matching elements, reports changes, and rejects null
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testRemoveAllRemovesMatchingElements();
        testRemoveAllWithNoMatchReturnsFalse();
        testRemoveAllWithEmptyArgumentReturnsFalse();
        testRemoveAllOnEmptyQueueReturnsFalse();
        testRemoveAllWithNullArgumentThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static List<Integer> drain(PriorityBlockingQueue<Integer> q) {
        List<Integer> result = new ArrayList<>();
        Integer x;
        while ((x = q.poll()) != null) {
            result.add(x);
        }
        return result;
    }

    static void testRemoveAllRemovesMatchingElements() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(5);
        q.add(1);
        q.add(3);
        q.add(2);
        q.add(4);
        boolean changed = q.removeAll(Arrays.asList(2, 4));
        check(changed, "removeAll should report a change");
        check(q.size() == 3, "size should be 3 after removing two elements");
        check(!q.contains(2) && !q.contains(4), "removed elements must not remain");
        List<Integer> remaining = drain(q);
        check(remaining.equals(Arrays.asList(1, 3, 5)), "remaining elements in priority order: " + remaining);
    }

    static void testRemoveAllWithNoMatchReturnsFalse() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(1);
        q.add(2);
        boolean changed = q.removeAll(Arrays.asList(7));
        check(!changed, "removeAll with no matches should return false");
        check(q.size() == 2, "size should remain 2");
        check(drain(q).equals(Arrays.asList(1, 2)), "contents should be unchanged");
    }

    static void testRemoveAllWithEmptyArgumentReturnsFalse() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(1);
        q.add(2);
        boolean changed = q.removeAll(Collections.emptyList());
        check(!changed, "removeAll with empty collection should return false");
        check(q.size() == 2, "size should remain 2");
    }

    static void testRemoveAllOnEmptyQueueReturnsFalse() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        boolean changed = q.removeAll(Arrays.asList(1));
        check(!changed, "removeAll on empty queue should return false");
        check(q.isEmpty(), "queue should remain empty");
    }

    static void testRemoveAllWithNullArgumentThrowsNPE() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(1);
        try {
            q.removeAll(null);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
        check(q.size() == 1, "queue must be unchanged after NPE");
    }
}
