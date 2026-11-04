/*
 * @test
 * @summary Witup-derived behavioral checks for LinkedTransferQueue.removeIf
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testNullFilterThrowsNullPointerException();
        testRemoveIfRemovesMatchingElements();
        testRemoveIfWithoutMatchReturnsFalse();
        testRemoveIfRemovesAllElements();
        testRemoveIfOnEmptyQueueReturnsFalse();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static List<Integer> contents(LinkedTransferQueue<Integer> q) {
        List<Integer> out = new ArrayList<>();
        for (Integer i : q) {
            out.add(i);
        }
        return out;
    }

    static void testNullFilterThrowsNullPointerException() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        q.add("a");
        boolean thrown = false;
        try {
            q.removeIf(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        check(thrown, "removeIf(null) must throw NullPointerException");
        check(q.size() == 1, "queue must be unchanged after failed removeIf(null)");
        check(q.contains("a"), "element must remain after failed removeIf(null)");
    }

    static void testRemoveIfRemovesMatchingElements() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        for (int i = 1; i <= 6; i++) {
            q.add(i);
        }
        boolean changed = q.removeIf(x -> x % 2 == 0);
        check(changed, "removeIf must return true when elements are removed");
        check(contents(q).equals(Arrays.asList(1, 3, 5)),
              "only odd elements must remain, in FIFO order: " + contents(q));
        check(q.size() == 3, "size must be 3 after removal");
    }

    static void testRemoveIfWithoutMatchReturnsFalse() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        q.add("a");
        q.add("b");
        boolean changed = q.removeIf(s -> s.equals("z"));
        check(!changed, "removeIf must return false when nothing matches");
        check(q.size() == 2, "size must be unchanged when nothing matches");
        check(q.contains("a") && q.contains("b"), "elements must be preserved");
    }

    static void testRemoveIfRemovesAllElements() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        q.add(10);
        q.add(20);
        boolean changed = q.removeIf(x -> true);
        check(changed, "removeIf must return true when all elements are removed");
        check(q.isEmpty(), "queue must be empty after removing all elements");
    }

    static void testRemoveIfOnEmptyQueueReturnsFalse() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        boolean changed = q.removeIf(x -> true);
        check(!changed, "removeIf on empty queue must return false");
        check(q.isEmpty(), "empty queue must remain empty");
    }
}
