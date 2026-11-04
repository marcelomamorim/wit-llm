/*
 * @test
 * @summary Test ArrayBlockingQueue.drainTo(Collection) for null argument and basic behavior
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) {
        testDrainToNullCollection();
        testDrainToBasicFunctionality();
    }

    private static void testDrainToNullCollection() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        try {
            queue.drainTo(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testDrainToBasicFunctionality() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        queue.add("a");
        queue.add("b");
        queue.add("c");

        List<String> target = new ArrayList<>();
        int drained = queue.drainTo(target);

        if (drained != 3) {
            throw new RuntimeException("Expected to drain 3 elements but drained " + drained);
        }

        if (target.size() != 3) {
            throw new RuntimeException("Target collection size expected 3 but was " + target.size());
        }

        if (!target.get(0).equals("a") || !target.get(1).equals("b") || !target.get(2).equals("c")) {
            throw new RuntimeException("Elements drained are not in FIFO order");
        }

        if (!queue.isEmpty()) {
            throw new RuntimeException("Queue expected to be empty after drainTo");
        }
    }
}
