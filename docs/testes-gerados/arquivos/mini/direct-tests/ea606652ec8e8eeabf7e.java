/*
 * @test
 * @summary Test PriorityBlockingQueue.drainTo(Collection<? super E>, int) method
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IllegalArgumentException;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        testDrainToNullCollection();
        testDrainToSelfCollection();
        testDrainToZeroOrNegativeMaxElements();
        testDrainToPartialDrain();
        testDrainToFullDrain();
    }

    static void testDrainToNullCollection() {
        PriorityBlockingQueue<Integer> pbq = new PriorityBlockingQueue<>();
        try {
            pbq.drainTo(null, 1);
            throw new Error("Expected NullPointerException for null collection");
        } catch (NullPointerException expected) {
        }
    }

    static void testDrainToSelfCollection() {
        PriorityBlockingQueue<Integer> pbq = new PriorityBlockingQueue<>();
        try {
            pbq.drainTo(pbq, 1);
            throw new Error("Expected IllegalArgumentException when draining to self");
        } catch (IllegalArgumentException expected) {
        }
    }

    static void testDrainToZeroOrNegativeMaxElements() {
        PriorityBlockingQueue<Integer> pbq = new PriorityBlockingQueue<>();
        pbq.add(1);
        int drained = pbq.drainTo(new ArrayList<Integer>(), 0);
        if (drained != 0) {
            throw new Error("Expected 0 elements drained when maxElements=0");
        }
        drained = pbq.drainTo(new ArrayList<Integer>(), -1);
        if (drained != 0) {
            throw new Error("Expected 0 elements drained when maxElements<0");
        }
    }

    static void testDrainToPartialDrain() {
        PriorityBlockingQueue<Integer> pbq = new PriorityBlockingQueue<>();
        pbq.add(10);
        pbq.add(20);
        pbq.add(30);
        ArrayList<Integer> target = new ArrayList<>();
        int drained = pbq.drainTo(target, 2);
        if (drained != 2) {
            throw new Error("Expected 2 elements drained, got " + drained);
        }
        if (target.size() != 2) {
            throw new Error("Target collection size mismatch after partial drain");
        }
        if (pbq.size() != 1) {
            throw new Error("PriorityBlockingQueue size mismatch after partial drain");
        }
        // The elements drained should be the two smallest (priority queue order)
        if (!target.contains(10) || !target.contains(20)) {
            throw new Error("Drained elements incorrect in partial drain");
        }
    }

    static void testDrainToFullDrain() {
        PriorityBlockingQueue<Integer> pbq = new PriorityBlockingQueue<>();
        pbq.add(5);
        pbq.add(15);
        ArrayList<Integer> target = new ArrayList<>();
        int drained = pbq.drainTo(target, 10); // maxElements > size
        if (drained != 2) {
            throw new Error("Expected 2 elements drained, got " + drained);
        }
        if (target.size() != 2) {
            throw new Error("Target collection size mismatch after full drain");
        }
        if (pbq.size() != 0) {
            throw new Error("PriorityBlockingQueue should be empty after full drain");
        }
        if (!target.contains(5) || !target.contains(15)) {
            throw new Error("Drained elements incorrect in full drain");
        }
    }
}
