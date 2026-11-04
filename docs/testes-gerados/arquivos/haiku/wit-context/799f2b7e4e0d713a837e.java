/*
 * @test
 * @summary Witup-generated behavioral checks for LinkedTransferQueue.drainTo
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.ArrayList;
import java.util.List;

public class LinkedTransferQueueWitupTest {
    public static void main(String[] args) {
        testNullCollectionThrowsNPE();
        testSelfDrainThrowsIllegalArgumentException();
        testEmptyQueueReturnsZero();
        testDrainsAllElementsInFifoOrder();
        testDrainAppendsToNonEmptyTarget();
    }

    static void testNullCollectionThrowsNPE() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        q.add(1);
        try {
            q.drainTo(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
        check(q.size() == 1, "queue must not be modified when argument is null");
    }

    static void testSelfDrainThrowsIllegalArgumentException() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        q.add(1);
        try {
            q.drainTo(q);
            throw new RuntimeException("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
        check(q.size() == 1, "queue must not be modified after self-drain rejection");
    }

    static void testEmptyQueueReturnsZero() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        List<Integer> c = new ArrayList<>();
        int n = q.drainTo(c);
        check(n == 0, "expected 0 drained elements but was " + n);
        check(c.isEmpty(), "target must remain empty");
    }

    static void testDrainsAllElementsInFifoOrder() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        q.add(1);
        q.add(2);
        q.add(3);
        List<Integer> c = new ArrayList<>();
        int n = q.drainTo(c);
        check(n == 3, "expected 3 drained elements but was " + n);
        check(c.equals(java.util.Arrays.asList(1, 2, 3)), "unexpected target contents " + c);
        check(q.isEmpty(), "queue must be empty after drainTo");
    }

    static void testDrainAppendsToNonEmptyTarget() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        q.add(1);
        q.add(2);
        List<Integer> c = new ArrayList<>();
        c.add(9);
        int n = q.drainTo(c);
        check(n == 2, "expected 2 drained elements but was " + n);
        check(c.equals(java.util.Arrays.asList(9, 1, 2)), "unexpected target contents " + c);
        check(q.isEmpty(), "queue must be empty after drainTo");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
