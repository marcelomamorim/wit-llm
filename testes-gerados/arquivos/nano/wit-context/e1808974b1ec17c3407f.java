/*
 * @test
 * @summary Tests for LinkedBlockingQueue drainTo method
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.Collection;
import java.util.NoSuchElementException;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testDrainToWithNullCollection();
        testDrainToWithValidCollection();
    }

    public static void testDrainToWithNullCollection() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        try {
            queue.drainTo(null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException expected) {
            // Test passes
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        }
    }

    public static void testDrainToWithValidCollection() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.add(1);
        queue.add(2);
        java.util.ArrayList<Integer> list = new java.util.ArrayList<>();
        int drainedCount = queue.drainTo(list);
        if (drainedCount != 2) {
            System.err.println("drainTo returned wrong count: " + drainedCount);
            System.exit(1);
        }
        if (list.size() != 2 || !list.contains(1) || !list.contains(2)) {
            System.err.println("drainTo did not transfer elements correctly");
            System.exit(1);
        }
        if (!queue.isEmpty()) {
            System.err.println("Queue not empty after drainTo");
            System.exit(1);
        }
    }
}
