/*
 * @test
 * @summary Witup-generated checks for SynchronousQueue.drainTo null, self and empty-queue behavior
 * @run main SynchronousQueueWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueWitupTest {

    public static void main(String[] args) {
        testNullCollectionThrowsNPE(false);
        testNullCollectionThrowsNPE(true);
        testSelfDrainThrowsIAE(false);
        testSelfDrainThrowsIAE(true);
        testEmptyQueueDrainReturnsZero(false);
        testEmptyQueueDrainReturnsZero(true);
        testEmptyQueueLeavesCollectionUntouched(false);
        testEmptyQueueLeavesCollectionUntouched(true);
        testPollStillNullAfterDrain(false);
        testPollStillNullAfterDrain(true);
    }

    static void testNullCollectionThrowsNPE(boolean fair) {
        SynchronousQueue<Integer> q = new SynchronousQueue<Integer>(fair);
        try {
            q.drainTo(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new AssertionError("expected NullPointerException");
    }

    static void testSelfDrainThrowsIAE(boolean fair) {
        SynchronousQueue<Integer> q = new SynchronousQueue<Integer>(fair);
        try {
            q.drainTo(q);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new AssertionError("expected IllegalArgumentException");
    }

    static void testEmptyQueueDrainReturnsZero(boolean fair) {
        SynchronousQueue<Integer> q = new SynchronousQueue<Integer>(fair);
        List<Integer> sink = new ArrayList<Integer>();
        int n = q.drainTo(sink);
        if (n != 0) {
            throw new AssertionError("expected 0 but was " + n);
        }
    }

    static void testEmptyQueueLeavesCollectionUntouched(boolean fair) {
        SynchronousQueue<Integer> q = new SynchronousQueue<Integer>(fair);
        List<Integer> sink = new ArrayList<Integer>();
        sink.add(42);
        q.drainTo(sink);
        if (sink.size() != 1 || sink.get(0).intValue() != 42) {
            throw new AssertionError("collection was modified: " + sink);
        }
    }

    static void testPollStillNullAfterDrain(boolean fair) {
        SynchronousQueue<Integer> q = new SynchronousQueue<Integer>(fair);
        List<Integer> sink = new ArrayList<Integer>();
        q.drainTo(sink);
        if (q.poll() != null) {
            throw new AssertionError("expected poll() to return null");
        }
    }
}
