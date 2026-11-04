/*
 * @test
 * @summary Test LinkedTransferQueue.drainTo(Collection<? super E>, int) method
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.lang.IllegalArgumentException;

public class LinkedTransferQueueWitupTest {
    public static void main(String[] args) {
        testDrainToBasic();
        testDrainToMaxElementsZero();
        testDrainToEmptyQueue();
        testDrainToSelfThrows();
    }

    static void testDrainToBasic() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");

        List<String> target = new ArrayList<>();
        int drained = queue.drainTo(target, 2);

        if (drained != 2) {
            throw new AssertionError("Expected drained count 2 but got " + drained);
        }
        if (target.size() != 2) {
            throw new AssertionError("Target collection size expected 2 but got " + target.size());
        }
        if (!"a".equals(target.get(0)) || !"b".equals(target.get(1))) {
            throw new AssertionError("Elements drained are not as expected");
        }
        // The queue should still have one element left
        String remaining = queue.poll();
        if (!"c".equals(remaining)) {
            throw new AssertionError("Remaining element expected 'c' but got " + remaining);
        }
    }

    static void testDrainToMaxElementsZero() {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        queue.add(1);
        queue.add(2);

        List<Integer> target = new ArrayList<>();
        int drained = queue.drainTo(target, 0);

        if (drained != 0) {
            throw new AssertionError("Expected drained count 0 but got " + drained);
        }
        if (!target.isEmpty()) {
            throw new AssertionError("Target collection should be empty when maxElements is 0");
        }
        // The queue should remain unchanged
        if (queue.size() != 2) {
            throw new AssertionError("Queue size expected 2 but got " + queue.size());
        }
    }

    static void testDrainToEmptyQueue() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        List<String> target = new ArrayList<>();
        int drained = queue.drainTo(target, 5);

        if (drained != 0) {
            throw new AssertionError("Expected drained count 0 from empty queue but got " + drained);
        }
        if (!target.isEmpty()) {
            throw new AssertionError("Target collection should be empty when queue is empty");
        }
    }

    static void testDrainToSelfThrows() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.add("x");
        boolean threw = false;
        try {
            queue.drainTo(queue, 1);
        } catch (IllegalArgumentException iae) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException when draining to self");
        }
    }
}
