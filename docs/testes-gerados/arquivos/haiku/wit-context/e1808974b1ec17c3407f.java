/*
 * @test
 * @summary Witup-derived tests for LinkedBlockingQueue.drainTo(Collection)
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testDrainAllFifo();
        testDrainEmptyQueue();
        testDrainNullCollectionThrowsNPE();
        testDrainToSelfThrowsIAE();
        testDrainAppendsToExistingElements();
        testBoundedCapacityFreedAfterDrain();
        testDrainFromCollectionConstructor();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testDrainAllFifo() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.add(1);
        q.add(2);
        q.add(3);
        List<Integer> sink = new ArrayList<>();
        int n = q.drainTo(sink);
        check(n == 3, "expected 3 drained, got " + n);
        check(sink.equals(Arrays.asList(1, 2, 3)), "unexpected order: " + sink);
        check(q.isEmpty(), "queue should be empty after drainTo");
    }

    static void testDrainEmptyQueue() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        List<String> sink = new ArrayList<>();
        int n = q.drainTo(sink);
        check(n == 0, "expected 0, got " + n);
        check(sink.isEmpty(), "sink should remain empty");
    }

    static void testDrainNullCollectionThrowsNPE() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        q.add("a");
        try {
            q.drainTo(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
        check(q.size() == 1, "queue must not be modified on NPE");
    }

    static void testDrainToSelfThrowsIAE() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        q.add("a");
        try {
            q.drainTo(q);
            throw new RuntimeException("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
        check(q.size() == 1, "queue must not be modified on IAE");
    }

    static void testDrainAppendsToExistingElements() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.add(10);
        q.add(20);
        List<Integer> sink = new ArrayList<>();
        sink.add(5);
        int n = q.drainTo(sink);
        check(n == 2, "expected 2, got " + n);
        check(sink.equals(Arrays.asList(5, 10, 20)), "unexpected sink: " + sink);
    }

    static void testBoundedCapacityFreedAfterDrain() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(2);
        check(q.offer(1), "offer 1 should succeed");
        check(q.offer(2), "offer 2 should succeed");
        check(!q.offer(3), "offer 3 should fail when full");
        List<Integer> sink = new ArrayList<>();
        int n = q.drainTo(sink);
        check(n == 2, "expected 2 drained, got " + n);
        check(q.remainingCapacity() == 2, "capacity should be restored");
        check(q.offer(3), "offer 3 should succeed after drain");
    }

    static void testDrainFromCollectionConstructor() {
        List<String> init = Arrays.asList("x", "y", "z");
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>(init);
        List<String> sink = new ArrayList<>();
        int n = q.drainTo(sink);
        check(n == 3, "expected 3, got " + n);
        check(sink.equals(Arrays.asList("x", "y", "z")), "unexpected sink: " + sink);
    }
}
