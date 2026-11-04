/*
 * @test
 * @summary Witup-derived behavioral tests for LinkedTransferQueue.drainTo(Collection, int)
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testNullCollectionThrowsNPE();
        testSelfDrainThrowsIAE();
        testDrainAllInFifoOrder();
        testDrainRespectsMaxElements();
        testZeroMaxElementsDrainsNothing();
        testEmptyQueueReturnsZero();
    }

    static void testNullCollectionThrowsNPE() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        q.add("a");
        try {
            q.drainTo(null, 10);
            throw new RuntimeException("Expected NullPointerException");
        } catch (NullPointerException expected) {
        }
        check(q.size() == 1, "element must remain after NPE");
    }

    static void testSelfDrainThrowsIAE() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        q.add("a");
        try {
            q.drainTo(q, 10);
            throw new RuntimeException("Expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
        }
        check(q.size() == 1, "element must remain after IAE");
    }

    static void testDrainAllInFifoOrder() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        q.add("a");
        q.add("b");
        q.add("c");
        List<String> target = new ArrayList<>();
        int n = q.drainTo(target, Integer.MAX_VALUE);
        check(n == 3, "expected 3 drained, got " + n);
        check(target.equals(List.of("a", "b", "c")), "unexpected order: " + target);
        check(q.isEmpty(), "queue must be empty after full drain");
    }

    static void testDrainRespectsMaxElements() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        q.add("a");
        q.add("b");
        q.add("c");
        List<String> target = new ArrayList<>();
        int n = q.drainTo(target, 2);
        check(n == 2, "expected 2 drained, got " + n);
        check(target.equals(List.of("a", "b")), "unexpected drained: " + target);
        check(q.size() == 1, "expected 1 remaining, got " + q.size());
        check(q.peek().equals("c"), "remaining head must be c");
    }

    static void testZeroMaxElementsDrainsNothing() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        q.add("a");
        List<String> target = new ArrayList<>();
        int n = q.drainTo(target, 0);
        check(n == 0, "expected 0, got " + n);
        check(target.isEmpty(), "target must stay empty");
        check(q.size() == 1, "queue must be unchanged");
    }

    static void testEmptyQueueReturnsZero() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        List<String> target = new ArrayList<>();
        int n = q.drainTo(target, 5);
        check(n == 0, "expected 0 from empty queue, got " + n);
        check(target.isEmpty(), "target must stay empty");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
