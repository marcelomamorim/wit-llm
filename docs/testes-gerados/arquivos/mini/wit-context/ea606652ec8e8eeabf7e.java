/*
 * @test
 * @summary Test PriorityBlockingQueue.drainTo(Collection<? super E>, int) for null, self, zero maxElements and normal draining
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Objects;
import java.lang.IllegalArgumentException;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        testNullCollectionThrows();
        testSelfCollectionThrows();
        testZeroMaxElementsReturnsZero();
        testDrainToRemovesElements();
    }

    private static void testNullCollectionThrows() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        boolean npeThrown = false;
        try {
            pq.drainTo(null, 1);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when passing null collection");
        }
    }

    private static void testSelfCollectionThrows() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        pq.add(1);
        boolean iaeThrown = false;
        try {
            pq.drainTo(pq, 1);
        } catch (IllegalArgumentException e) {
            iaeThrown = true;
        }
        if (!iaeThrown) {
            throw new AssertionError("Expected IllegalArgumentException when passing self as collection");
        }
    }

    private static void testZeroMaxElementsReturnsZero() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        pq.add(1);
        int drained = pq.drainTo(new ArrayList<Integer>(), 0);
        if (drained != 0) {
            throw new AssertionError("Expected drainTo to return 0 when maxElements is 0");
        }
    }

    private static void testDrainToRemovesElements() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        pq.add(10);
        pq.add(20);
        pq.add(30);
        List<Integer> target = new ArrayList<>();
        int drained = pq.drainTo(target, 2);
        if (drained != 2) {
            throw new AssertionError("Expected drainTo to drain 2 elements, got " + drained);
        }
        if (target.size() != 2) {
            throw new AssertionError("Target collection size expected 2 but was " + target.size());
        }
        if (pq.size() != 1) {
            throw new AssertionError("PriorityBlockingQueue size expected 1 but was " + pq.size());
        }
        // The drained elements should be the two smallest (priority order)
        if (!(target.contains(10) && target.contains(20))) {
            throw new AssertionError("Drained elements expected to contain 10 and 20");
        }
    }
}
