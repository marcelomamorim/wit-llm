/*
 * @test
 * @summary Verify PriorityBlockingQueue.put adds elements like offer, keeping ordering, size and null rejection
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testPutSingleElement();
        testPutOrdersNaturally();
        testPutWithReverseComparator();
        testPutGrowsBeyondInitialCapacity();
        testPutNullThrowsNpe();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testPutSingleElement() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<Integer>();
        q.put(7);
        check(q.size() == 1, "size after single put should be 1");
        check(!q.isEmpty(), "queue should not be empty after put");
        check(q.peek().intValue() == 7, "peek should return the put element");
    }

    static void testPutOrdersNaturally() throws InterruptedException {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<Integer>();
        q.put(5);
        q.put(1);
        q.put(3);
        q.put(2);
        q.put(4);
        check(q.size() == 5, "size should be 5");
        for (int i = 1; i <= 5; i++) {
            check(q.take().intValue() == i, "elements should come out in ascending natural order");
        }
        check(q.isEmpty(), "queue should be empty after draining");
    }

    static void testPutWithReverseComparator() throws InterruptedException {
        PriorityBlockingQueue<Integer> q =
                new PriorityBlockingQueue<Integer>(11, Comparator.<Integer>reverseOrder());
        q.put(1);
        q.put(3);
        q.put(2);
        check(q.take().intValue() == 3, "reverse comparator should yield 3 first");
        check(q.take().intValue() == 2, "reverse comparator should yield 2 second");
        check(q.take().intValue() == 1, "reverse comparator should yield 1 last");
    }

    static void testPutGrowsBeyondInitialCapacity() throws InterruptedException {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<Integer>(1);
        for (int i = 0; i < 100; i++) {
            q.put(i);
        }
        check(q.size() == 100, "size should be 100 after 100 puts");
        for (int i = 0; i < 100; i++) {
            check(q.take().intValue() == i, "elements should be retrieved in order after growth");
        }
    }

    static void testPutNullThrowsNpe() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<Integer>();
        try {
            q.put(null);
            throw new AssertionError("put(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
        check(q.isEmpty(), "queue should remain empty after rejected null put");
    }
}
