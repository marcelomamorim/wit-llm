/*
 * @test
 * @summary Test LinkedTransferQueue.drainTo(Collection<? super E>, int) for null argument and normal behavior
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.Collection;
import java.util.ArrayList;
import java.util.IllegalArgumentException;
import java.util.Objects;

public class LinkedTransferQueueWitupTest {
    public static void main(String[] args) {
        testDrainToNullCollection();
        testDrainToSelfThrows();
        testDrainToNormal();
    }

    private static void testDrainToNullCollection() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        boolean npeThrown = false;
        try {
            queue.drainTo(null, 1);
        } catch (NullPointerException ex) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when passing null collection");
        }
    }

    private static void testDrainToSelfThrows() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        boolean iaeThrown = false;
        try {
            queue.drainTo(queue, 1);
        } catch (IllegalArgumentException ex) {
            iaeThrown = true;
        }
        if (!iaeThrown) {
            throw new AssertionError("Expected IllegalArgumentException when passing self as collection");
        }
    }

    private static void testDrainToNormal() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");

        Collection<String> target = new ArrayList<>();
        int drained = queue.drainTo(target, 2);

        if (drained != 2) {
            throw new AssertionError("Expected to drain 2 elements, but drained " + drained);
        }

        if (target.size() != 2) {
            throw new AssertionError("Target collection size expected 2 but was " + target.size());
        }

        if (queue.size() != 1) {
            throw new AssertionError("Queue size expected 1 after draining 2 elements but was " + queue.size());
        }

        // Check that drained elements are the first two added (FIFO)
        String[] expected = {"a", "b"};
        int i = 0;
        for (String s : target) {
            if (!Objects.equals(s, expected[i])) {
                throw new AssertionError("Expected element " + expected[i] + " but found " + s);
            }
            i++;
        }
    }
}
