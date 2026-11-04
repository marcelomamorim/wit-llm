/*
 * @test
 * @summary Witup-generated tests for PriorityBlockingQueue.offer(E, long, TimeUnit)
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testNullElementThrowsNPE();
        testOfferReturnsTrueAndInserts();
        testOrderingPreserved();
        testNegativeTimeoutStillOffers();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullElementThrowsNPE() {
        PriorityBlockingQueue<String> q = new PriorityBlockingQueue<>();
        try {
            q.offer(null, 1, TimeUnit.SECONDS);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
        check(q.isEmpty(), "queue must remain empty after rejected null");
    }

    static void testOfferReturnsTrueAndInserts() {
        PriorityBlockingQueue<String> q = new PriorityBlockingQueue<>();
        check(q.offer("a", 1, TimeUnit.SECONDS), "offer must return true");
        check(q.size() == 1, "size must be 1");
        check("a".equals(q.peek()), "peek must return inserted element");
    }

    static void testOrderingPreserved() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        check(q.offer(3, 1, TimeUnit.SECONDS), "offer 3 failed");
        check(q.offer(1, 1, TimeUnit.SECONDS), "offer 1 failed");
        check(q.offer(2, 1, TimeUnit.SECONDS), "offer 2 failed");
        check(Integer.valueOf(1).equals(q.poll()), "expected 1 first");
        check(Integer.valueOf(2).equals(q.poll()), "expected 2 second");
        check(Integer.valueOf(3).equals(q.poll()), "expected 3 third");
        check(q.poll() == null, "queue must be empty");
    }

    static void testNegativeTimeoutStillOffers() {
        PriorityBlockingQueue<String> q = new PriorityBlockingQueue<>();
        check(q.offer("x", -1, TimeUnit.SECONDS), "offer must not block or fail on negative timeout");
        check(q.size() == 1, "size must be 1 after offer");
    }
}
