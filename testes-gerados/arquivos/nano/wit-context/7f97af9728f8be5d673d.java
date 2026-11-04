/* @test    @summary Test offer method of PriorityBlockingQueue for null input and normal insertion    @run main PriorityBlockingQueueWitupTest */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.Comparator;
import java.util.Arrays;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testOfferNull();
        testOfferNormal();
    }

    private static void testOfferNull() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        boolean exceptionThrown = false;
        try {
            queue.offer(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when offering null");
        }
    }

    private static void testOfferNormal() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        boolean result = queue.offer(10);
        if (!result) {
            throw new AssertionError("offer should return true for valid element");
        }
        if (queue.isEmpty()) {
            throw new AssertionError("Queue should not be empty after offer");
        }
        Integer head = queue.peek();
        if (head == null || head != 10) {
            throw new AssertionError("Head of queue should be 10");
        }
    }
}
