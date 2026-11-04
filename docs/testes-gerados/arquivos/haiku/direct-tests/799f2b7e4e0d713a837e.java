/*
 * @test
 * @summary Verify LinkedTransferQueue.drainTo observable behavior
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testEmptyQueueReturnsZero();
        testDrainAllElementsInFifoOrder();
        testSecondDrainReturnsZero();
        testNullCollectionThrowsNpeAndKeepsElements();
        testSelfDrainThrowsIaeAndKeepsElements();
    }

    static void testEmptyQueueReturnsZero() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        List<Integer> out = new ArrayList<>();
        int n = q.drainTo(out);
        check(n == 0, "empty queue drain count");
        check(out.isEmpty(), "empty queue target list");
    }

    static void testDrainAllElementsInFifoOrder() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        q.add(1);
        q.add(2);
        q.add(3);
        List<Integer> out = new ArrayList<>();
        int n = q.drainTo(out);
        check(n == 3, "drain count for three elements");
        check(out.equals(Arrays.asList(1, 2, 3)), "fifo order in target");
        check(q.isEmpty(), "queue empty after drain");
    }

    static void testSecondDrainReturnsZero() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        q.add(7);
        List<Integer> first = new ArrayList<>();
        check(q.drainTo(first) == 1, "first drain count");
        List<Integer> second = new ArrayList<>();
        check(q.drainTo(second) == 0, "second drain count");
        check(second.isEmpty(), "second target list");
        check(first.equals(Arrays.asList(7)), "first target content");
    }

    static void testNullCollectionThrowsNpeAndKeepsElements() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        q.add(1);
        boolean thrown = false;
        try {
            q.drainTo(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null collection must throw NullPointerException");
        check(q.size() == 1, "element retained after null drain");
    }

    static void testSelfDrainThrowsIaeAndKeepsElements() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        q.add(1);
        boolean thrown = false;
        try {
            q.drainTo(q);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "self drain must throw IllegalArgumentException");
        check(q.size() == 1, "element retained after self drain");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
