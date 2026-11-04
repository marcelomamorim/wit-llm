/* @test    @summary Test drainTo with null collection to verify NullPointerException is thrown    @run main PriorityBlockingQueueWitupTest */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.Collection;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testDrainToNullCollection();
    }

    public static void testDrainToNullCollection() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        try {
            queue.drainTo(null);
            System.err.println("Expected NullPointerException was not thrown.");
            System.exit(1);
        } catch (NullPointerException expected) {
            // Test passes
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
