/*
 * @test
 * @summary Verify LinkedTransferQueue.offer returns true, updates size and preserves FIFO order
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testOfferReturnsTrue();
        testOfferIncreasesSize();
        testOfferPreservesFifoOrder();
        testOfferAllowsDuplicateElements();
    }

    static void testOfferReturnsTrue() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        if (!q.offer(1)) {
            throw new AssertionError("offer(1) should return true");
        }
        if (!q.offer(2)) {
            throw new AssertionError("offer(2) should return true");
        }
    }

    static void testOfferIncreasesSize() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        if (q.size() != 0) {
            throw new AssertionError("new queue should be empty");
        }
        q.offer(10);
        if (q.size() != 1) {
            throw new AssertionError("size should be 1 after one offer, was " + q.size());
        }
        q.offer(20);
        if (q.size() != 2) {
            throw new AssertionError("size should be 2 after two offers, was " + q.size());
        }
    }

    static void testOfferPreservesFifoOrder() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        q.offer(1);
        q.offer(2);
        q.offer(3);
        Integer first = q.poll();
        Integer second = q.poll();
        Integer third = q.poll();
        if (first == null || first != 1) {
            throw new AssertionError("expected 1 first, got " + first);
        }
        if (second == null || second != 2) {
            throw new AssertionError("expected 2 second, got " + second);
        }
        if (third == null || third != 3) {
            throw new AssertionError("expected 3 third, got " + third);
        }
        if (q.poll() != null) {
            throw new AssertionError("queue should be empty after polling all elements");
        }
    }

    static void testOfferAllowsDuplicateElements() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        q.offer(7);
        q.offer(7);
        if (q.size() != 2) {
            throw new AssertionError("duplicate offers should both be stored, size=" + q.size());
        }
        if (q.poll() != 7 || q.poll() != 7) {
            throw new AssertionError("duplicate elements should be returned in order");
        }
    }
}
