/*
 * @test
 * @summary Verify that PriorityBlockingQueue.offer(E, long, TimeUnit) inserts elements, ignores the timeout, and preserves priority ordering
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testOfferReturnsTrueAndInserts();
        testTimeoutIsIgnored();
        testNegativeTimeoutAccepted();
        testNaturalOrderingAfterTimedOffer();
        testComparatorOrderingAfterTimedOffer();
    }

    static void testOfferReturnsTrueAndInserts() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        check(q.offer(Integer.valueOf(1), 0L, TimeUnit.MILLISECONDS), "offer must return true");
        check(q.size() == 1, "size must be 1 after one offer");
        check(q.peek().intValue() == 1, "peek must return the inserted element");
    }

    static void testTimeoutIsIgnored() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        check(q.offer(Integer.valueOf(7), Long.MAX_VALUE, TimeUnit.SECONDS), "offer with large timeout must return true");
        check(q.size() == 1, "size must be 1");
        check(q.poll().intValue() == 7, "polled element must be 7");
    }

    static void testNegativeTimeoutAccepted() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        check(q.offer(Integer.valueOf(3), -1L, TimeUnit.NANOSECONDS), "offer with negative timeout must return true");
        check(q.size() == 1, "size must be 1");
    }

    static void testNaturalOrderingAfterTimedOffer() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        check(q.offer(Integer.valueOf(5), 0L, TimeUnit.MILLISECONDS), "offer 5 must return true");
        check(q.offer(Integer.valueOf(2), 0L, TimeUnit.MILLISECONDS), "offer 2 must return true");
        check(q.offer(Integer.valueOf(8), 0L, TimeUnit.MILLISECONDS), "offer 8 must return true");
        check(q.size() == 3, "size must be 3");
        check(q.poll().intValue() == 2, "first poll must be 2");
        check(q.poll().intValue() == 5, "second poll must be 5");
        check(q.poll().intValue() == 8, "third poll must be 8");
        check(q.isEmpty(), "queue must be empty");
    }

    static void testComparatorOrderingAfterTimedOffer() {
        PriorityBlockingQueue<Integer> q =
            new PriorityBlockingQueue<>(11, Comparator.reverseOrder());
        check(q.offer(Integer.valueOf(1), 0L, TimeUnit.MILLISECONDS), "offer 1 must return true");
        check(q.offer(Integer.valueOf(3), 0L, TimeUnit.MILLISECONDS), "offer 3 must return true");
        check(q.offer(Integer.valueOf(2), 0L, TimeUnit.MILLISECONDS), "offer 2 must return true");
        check(q.poll().intValue() == 3, "first poll with reverse comparator must be 3");
        check(q.poll().intValue() == 2, "second poll with reverse comparator must be 2");
        check(q.poll().intValue() == 1, "third poll with reverse comparator must be 1");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
