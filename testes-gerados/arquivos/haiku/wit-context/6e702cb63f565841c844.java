/*
 * @test
 * @summary Basic observable behavior of LinkedTransferQueue.offer(E, long, TimeUnit)
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testOfferReturnsTrueAndKeepsFifoOrder();
        testOfferIgnoresTimeoutForAsyncInsertion();
        testOfferNullThrowsNullPointerException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testOfferReturnsTrueAndKeepsFifoOrder() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        check(q.offer("a", 0, TimeUnit.MILLISECONDS), "offer(\"a\") should return true");
        check(q.offer("b", 0, TimeUnit.MILLISECONDS), "offer(\"b\") should return true");
        check(q.size() == 2, "size should be 2 after two offers");
        check("a".equals(q.poll()), "first polled element should be \"a\"");
        check("b".equals(q.poll()), "second polled element should be \"b\"");
        check(q.poll() == null, "queue should be empty after polling both elements");
    }

    static void testOfferIgnoresTimeoutForAsyncInsertion() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        check(q.offer(1, -1L, TimeUnit.NANOSECONDS), "offer with negative timeout should return true");
        check(q.offer(2, 0L, TimeUnit.SECONDS), "offer with zero timeout should return true");
        check(q.size() == 2, "both elements should be enqueued regardless of timeout");
    }

    static void testOfferNullThrowsNullPointerException() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        try {
            q.offer(null, 0, TimeUnit.SECONDS);
        } catch (NullPointerException expected) {
            check(q.isEmpty(), "queue should remain empty after rejected null offer");
            return;
        }
        throw new RuntimeException("offer(null, ...) should throw NullPointerException");
    }
}
