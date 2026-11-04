/*
 * @test
 * @summary Test LinkedTransferQueue.drainTo(Collection) method behavior
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Objects;
import java.util.IllegalFormatException;
import java.lang.IllegalArgumentException;

public class LinkedTransferQueueWitupTest {
    public static void main(String[] args) {
        testDrainToNullCollection();
        testDrainToSelfCollection();
        testDrainToEmptyQueue();
        testDrainToNonEmptyQueue();
    }

    static void testDrainToNullCollection() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        try {
            queue.drainTo(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testDrainToSelfCollection() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        try {
            queue.drainTo(queue);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testDrainToEmptyQueue() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        Collection<String> target = new ArrayList<>();
        int drained = queue.drainTo(target);
        if (drained != 0) {
            throw new RuntimeException("Expected drained count 0 for empty queue, got " + drained);
        }
        if (!target.isEmpty()) {
            throw new RuntimeException("Target collection should remain empty when draining empty queue");
        }
    }

    static void testDrainToNonEmptyQueue() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");
        Collection<String> target = new ArrayList<>();
        int drained = queue.drainTo(target);
        if (drained != 3) {
            throw new RuntimeException("Expected drained count 3, got " + drained);
        }
        if (target.size() != 3) {
            throw new RuntimeException("Target collection size expected 3, got " + target.size());
        }
        // Check order preserved
        String[] expected = {"a", "b", "c"};
        int i = 0;
        for (String s : target) {
            if (!expected[i].equals(s)) {
                throw new RuntimeException("Element order mismatch at index " + i + ": expected " + expected[i] + ", got " + s);
            }
            i++;
        }
        // After drainTo, queue should be empty
        if (queue.poll() != null) {
            throw new RuntimeException("Queue should be empty after drainTo");
        }
    }
}
