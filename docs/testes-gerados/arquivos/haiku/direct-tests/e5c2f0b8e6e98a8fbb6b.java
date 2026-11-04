/*
 * @test
 * @summary Checks observable behavior of LinkedBlockingQueue.offer: null rejection, bounded full-queue refusal, space reuse after poll, unbounded acceptance, collection constructor interaction and FIFO order
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testNullRejected();
        testBoundedFullReturnsFalse();
        testOfferAfterPollSucceeds();
        testUnboundedAcceptsMany();
        testCollectionConstructorThenOffer();
        testFifoOrder();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testNullRejected() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        boolean thrown = false;
        try {
            q.offer(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "offer(null) must throw NullPointerException");
        check(q.isEmpty(), "queue must remain empty after rejected null");
    }

    static void testBoundedFullReturnsFalse() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>(2);
        check(q.offer("a"), "first offer should succeed");
        check(q.offer("b"), "second offer should succeed");
        check(!q.offer("c"), "offer on full queue should return false");
        check(q.size() == 2, "size should stay at capacity");
        check(q.remainingCapacity() == 0, "remaining capacity should be 0");
    }

    static void testOfferAfterPollSucceeds() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>(1);
        check(q.offer("x"), "offer into empty bounded queue should succeed");
        check(!q.offer("y"), "offer into full queue should return false");
        check("x".equals(q.poll()), "poll should return first element");
        check(q.offer("y"), "offer after poll should succeed");
        check("y".equals(q.peek()), "peek should return newly offered element");
    }

    static void testUnboundedAcceptsMany() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        for (int i = 0; i < 1000; i++) {
            check(q.offer(i), "unbounded offer should succeed at " + i);
        }
        check(q.size() == 1000, "size should be 1000");
    }

    static void testCollectionConstructorThenOffer() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>(Arrays.asList("a", "b"));
        check(q.offer("c"), "offer after collection constructor should succeed");
        check(q.size() == 3, "size should be 3");
        check("a".equals(q.poll()), "head should be first element from collection");
    }

    static void testFifoOrder() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(3);
        check(q.offer(1) && q.offer(2) && q.offer(3), "three offers should succeed");
        check(q.poll() == 1, "first poll should return 1");
        check(q.poll() == 2, "second poll should return 2");
        check(q.poll() == 3, "third poll should return 3");
        check(q.poll() == null, "poll on empty queue should return null");
    }
}
