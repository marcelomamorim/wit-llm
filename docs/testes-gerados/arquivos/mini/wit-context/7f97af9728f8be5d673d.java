/*
 * @test
 * @summary Deterministic tests for PriorityBlockingQueue.offer(E) covering null input and normal insertion
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        testOfferNullThrowsNPE();
        testOfferNormalInsertion();
    }

    private static void testOfferNullThrowsNPE() {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
        try {
            queue.offer(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testOfferNormalInsertion() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        boolean offered = queue.offer(10);
        if (!offered) {
            throw new Error("offer returned false unexpectedly");
        }
        Integer polled = queue.poll();
        if (polled == null || polled.intValue() != 10) {
            throw new Error("Polled element does not match offered element");
        }
    }
}
