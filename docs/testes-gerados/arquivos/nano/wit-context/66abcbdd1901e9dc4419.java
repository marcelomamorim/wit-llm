/*
 * @test
 * @summary Tests for ArrayBlockingQueue.drainTo(Collection) method
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.Collection;
import java.util.Arrays;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testDrainToWithNullCollection();
        testDrainToWithValidCollection();
    }

    public static void testDrainToWithNullCollection() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        try {
            queue.drainTo(null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    public static void testDrainToWithValidCollection() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        queue.addAll(Arrays.asList(1, 2, 3));
        Collection<Integer> col = new java.util.ArrayList<>();
        int drainedCount = queue.drainTo(col);
        if (drainedCount != 3) {
            System.err.println("drainTo did not drain all elements");
            System.exit(1);
        }
        if (!col.containsAll(Arrays.asList(1, 2, 3))) {
            System.err.println("Collection does not contain drained elements");
            System.exit(1);
        }
        if (!queue.isEmpty()) {
            System.err.println("Queue not empty after drainTo");
            System.exit(1);
        }
    }
}
