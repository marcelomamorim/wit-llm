/*
 * @test
 * @summary Witup-derived behavioral tests for LinkedBlockingQueue.offer
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testOfferNullThrowsNPE();
        testOfferNullDoesNotModifyQueue();
        testOfferOnFullBoundedQueueReturnsFalse();
        testOfferFifoOrder();
        testOfferRespectsCapacityBoundary();
    }

    static void testOfferNullThrowsNPE() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        boolean thrown = false;
        try {
            q.offer(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("offer(null) must throw NullPointerException");
        }
    }

    static void testOfferNullDoesNotModifyQueue() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(2);
        if (!q.offer(1)) {
            throw new RuntimeException("offer(1) should succeed on empty bounded queue");
        }
        try {
            q.offer(null);
        } catch (NullPointerException expected) {
        }
        if (q.size() != 1) {
            throw new RuntimeException("size changed after rejected null offer: " + q.size());
        }
        if (q.poll() != 1) {
            throw new RuntimeException("head element altered by rejected null offer");
        }
    }

    static void testOfferOnFullBoundedQueueReturnsFalse() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(1);
        if (!q.offer(10)) {
            throw new RuntimeException("first offer should succeed");
        }
        if (q.offer(20)) {
            throw new RuntimeException("offer on full queue should return false");
        }
        if (q.size() != 1) {
            throw new RuntimeException("size should remain 1, was " + q.size());
        }
        if (q.poll() != 10) {
            throw new RuntimeException("original element should be preserved");
        }
    }

    static void testOfferFifoOrder() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        if (!q.offer(1) || !q.offer(2) || !q.offer(3)) {
            throw new RuntimeException("unbounded offers should succeed");
        }
        if (q.poll() != 1 || q.poll() != 2 || q.poll() != 3) {
            throw new RuntimeException("FIFO order violated");
        }
        if (q.poll() != null) {
            throw new RuntimeException("queue should be empty");
        }
    }

    static void testOfferRespectsCapacityBoundary() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(3);
        for (int i = 0; i < 3; i++) {
            if (!q.offer(i)) {
                throw new RuntimeException("offer " + i + " should succeed");
            }
        }
        if (q.offer(99)) {
            throw new RuntimeException("offer beyond capacity should return false");
        }
        if (q.size() != 3) {
            throw new RuntimeException("size should be 3, was " + q.size());
        }
    }
}
