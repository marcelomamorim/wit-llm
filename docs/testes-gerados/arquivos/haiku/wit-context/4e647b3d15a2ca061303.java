/*
 * @test
 * @summary Witup-generated behavioral tests for PriorityQueue.forEach
 * @run main PriorityQueueWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.PriorityQueue;

public class PriorityQueueWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testEmptyQueueDoesNotInvokeAction();
        testVisitsAllElements();
        testModificationDuringIterationThrowsCME();
    }

    static void testNullActionThrowsNPE() {
        PriorityQueue<Integer> q = new PriorityQueue<>();
        q.offer(1);
        try {
            q.forEach(null);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    static void testEmptyQueueDoesNotInvokeAction() {
        PriorityQueue<Integer> q = new PriorityQueue<>();
        int[] count = {0};
        q.forEach(e -> count[0]++);
        if (count[0] != 0) {
            throw new AssertionError("action invoked on empty queue: " + count[0]);
        }
    }

    static void testVisitsAllElements() {
        PriorityQueue<Integer> q = new PriorityQueue<>();
        q.offer(5);
        q.offer(3);
        q.offer(8);
        q.offer(1);
        long[] sum = {0};
        int[] count = {0};
        q.forEach(e -> {
            sum[0] += e;
            count[0]++;
        });
        if (count[0] != 4) {
            throw new AssertionError("expected 4 visits but was " + count[0]);
        }
        if (sum[0] != 17L) {
            throw new AssertionError("expected sum 17 but was " + sum[0]);
        }
    }

    static void testModificationDuringIterationThrowsCME() {
        PriorityQueue<Integer> q = new PriorityQueue<>();
        q.offer(1);
        try {
            q.forEach(e -> q.offer(2));
            throw new AssertionError("expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
        }
    }
}
