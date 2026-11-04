/*
 * @test
 * @summary Tests observable behavior of LinkedTransferQueue.drainTo(Collection, int)
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static LinkedTransferQueue<Integer> filledQueue(int from, int to) {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<Integer>();
        for (int i = from; i <= to; i++) {
            q.add(i);
        }
        return q;
    }

    static void testDrainAll() {
        LinkedTransferQueue<Integer> q = filledQueue(1, 5);
        List<Integer> sink = new ArrayList<Integer>();
        int n = q.drainTo(sink, Integer.MAX_VALUE);
        check(n == 5, "expected 5 drained, got " + n);
        check(sink.size() == 5, "sink size mismatch");
        for (int i = 0; i < 5; i++) {
            check(sink.get(i) == i + 1, "FIFO order violated at " + i);
        }
        check(q.isEmpty(), "queue should be empty after full drain");
    }

    static void testDrainWithMaxElements() {
        LinkedTransferQueue<Integer> q = filledQueue(1, 5);
        List<Integer> sink = new ArrayList<Integer>();
        int n = q.drainTo(sink, 2);
        check(n == 2, "expected 2 drained, got " + n);
        check(sink.size() == 2 && sink.get(0) == 1 && sink.get(1) == 2, "wrong elements drained");
        check(q.size() == 3, "remaining size should be 3");
        check(q.poll() == 3, "next element should be 3");
    }

    static void testZeroMax() {
        LinkedTransferQueue<Integer> q = filledQueue(1, 3);
        List<Integer> sink = new ArrayList<Integer>();
        int n = q.drainTo(sink, 0);
        check(n == 0, "zero max should drain nothing");
        check(sink.isEmpty(), "sink should stay empty");
        check(q.size() == 3, "queue should be unchanged");
    }

    static void testNegativeMax() {
        LinkedTransferQueue<Integer> q = filledQueue(1, 3);
        List<Integer> sink = new ArrayList<Integer>();
        int n = q.drainTo(sink, -1);
        check(n == 0, "negative max should drain nothing");
        check(sink.isEmpty(), "sink should stay empty");
        check(q.size() == 3, "queue should be unchanged");
    }

    static void testEmptyQueue() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<Integer>();
        List<Integer> sink = new ArrayList<Integer>();
        int n = q.drainTo(sink, 10);
        check(n == 0, "empty queue should drain 0");
        check(sink.isEmpty(), "sink should stay empty");
    }

    static void testAppendsToNonEmptyCollection() {
        LinkedTransferQueue<Integer> q = filledQueue(7, 8);
        List<Integer> sink = new ArrayList<Integer>();
        sink.add(0);
        int n = q.drainTo(sink, 10);
        check(n == 2, "expected 2 drained");
        check(sink.size() == 3 && sink.get(0) == 0 && sink.get(1) == 7 && sink.get(2) == 8,
              "elements should be appended after existing content");
    }

    static void testNullCollection() {
        LinkedTransferQueue<Integer> q = filledQueue(1, 2);
        boolean thrown = false;
        try {
            q.drainTo(null, 1);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null collection should throw NullPointerException");
        check(q.size() == 2, "queue should be unchanged after NPE");
    }

    static void testSelfDrain() {
        LinkedTransferQueue<Integer> q = filledQueue(1, 3);
        boolean thrown = false;
        try {
            q.drainTo(q, 1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "draining into itself should throw IllegalArgumentException");
        check(q.size() == 3, "queue should be unchanged after IAE");
    }

    public static void main(String[] args) {
        testDrainAll();
        testDrainWithMaxElements();
        testZeroMax();
        testNegativeMax();
        testEmptyQueue();
        testAppendsToNonEmptyCollection();
        testNullCollection();
        testSelfDrain();
    }
}
