/* @test    @summary Test for PriorityBlockingQueue forEachRemaining method to verify behavior with null Consumer */
/* @run main PriorityBlockingQueueWitupTest */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.function.Consumer;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullConsumer();
    }

    public static void testForEachRemainingWithNullConsumer() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        try {
            queue.forEachRemaining(null);
            // If no exception, test fails
            System.err.println("Expected NullPointerException was not thrown.");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("NullPointerException correctly thrown.");
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        }
    }
}
