/*
 * @test
 * @summary Test LinkedTransferQueue.drainTo(Collection) for null argument and normal draining
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Objects;
import java.lang.IllegalArgumentException;

public class LinkedTransferQueueWitupTest {
    public static void main(String[] args) {
        testDrainToNullCollection();
        testDrainToSelfThrows();
        testDrainToDrainsElements();
    }

    private static void testDrainToNullCollection() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        boolean npeThrown = false;
        try {
            queue.drainTo(null);
        } catch (NullPointerException ex) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to drainTo");
        }
    }

    private static void testDrainToSelfThrows() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        boolean iaeThrown = false;
        try {
            queue.drainTo(queue);
        } catch (IllegalArgumentException ex) {
            iaeThrown = true;
        }
        if (!iaeThrown) {
            throw new AssertionError("Expected IllegalArgumentException when passing self to drainTo");
        }
    }

    private static void testDrainToDrainsElements() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.add("one");
        queue.add("two");
        Collection<String> target = new ArrayList<>();
        int drained = queue.drainTo(target);
        if (drained != 2) {
            throw new AssertionError("Expected to drain 2 elements, but drained " + drained);
        }
        if (queue.size() != 0) {
            throw new AssertionError("Expected queue to be empty after drainTo");
        }
        if (!target.contains("one") || !target.contains("two")) {
            throw new AssertionError("Target collection does not contain all drained elements");
        }
    }
}
