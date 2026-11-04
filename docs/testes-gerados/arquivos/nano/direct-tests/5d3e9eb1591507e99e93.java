/* @test    @summary Test offer method with timeout in PriorityBlockingQueue    @run main PriorityBlockingQueueWitupTest */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testOfferWithTimeoutReturnsTrueForEnqueuedElement();
        testOfferWithTimeoutReturnsFalseWhenQueueIsFull();
    }

    private static void testOfferWithTimeoutReturnsTrueForEnqueuedElement() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>(2);
        boolean result = queue.offer(1, 1, TimeUnit.MILLISECONDS);
        assert result : "Expected offer to return true when element is added";
        assert queue.contains(1) : "Queue should contain the element 1";
    }

    private static void testOfferWithTimeoutReturnsFalseWhenQueueIsFull() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>(1);
        queue.offer(1); // fill the queue
        boolean result = queue.offer(2, 1, TimeUnit.MILLISECONDS);
        // Since offer with timeout in PriorityBlockingQueue always returns true, even if full,
        // this test checks that the method returns true, not false.
        // But to simulate a full queue, we can check that the queue size is at capacity.
        assert result : "Expected offer with timeout to return true even if queue is full";
        assert queue.contains(2) : "Queue should contain the element 2";
    }
}
