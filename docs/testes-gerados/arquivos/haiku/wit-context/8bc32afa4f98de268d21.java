/*
 * @test
 * @summary Witup-derived checks for PriorityBlockingQueue.put: null rejection, ordering and size effects
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testPutNullThrowsNullPointerException();
        testPutNullDoesNotChangeQueue();
        testPutAddsElementsInNaturalOrder();
        testPutWithComparatorOrder();
    }

    static void testPutNullThrowsNullPointerException() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        try {
            q.put(null);
            throw new AssertionError("put(null) must throw NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    static void testPutNullDoesNotChangeQueue() throws InterruptedException {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.put(7);
        try {
            q.put(null);
            throw new AssertionError("put(null) must throw NullPointerException");
        } catch (NullPointerException expected) {
        }
        if (q.size() != 1) {
            throw new AssertionError("size changed after rejected put: " + q.size());
        }
        if (q.take().intValue() != 7) {
            throw new AssertionError("element changed after rejected put");
        }
        if (!q.isEmpty()) {
            throw new AssertionError("queue should be empty");
        }
    }

    static void testPutAddsElementsInNaturalOrder() throws InterruptedException {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.put(5);
        q.put(1);
        q.put(3);
        if (q.size() != 3) {
            throw new AssertionError("unexpected size: " + q.size());
        }
        if (q.take().intValue() != 1) {
            throw new AssertionError("expected 1 first");
        }
        if (q.take().intValue() != 3) {
            throw new AssertionError("expected 3 second");
        }
        if (q.take().intValue() != 5) {
            throw new AssertionError("expected 5 third");
        }
        if (!q.isEmpty()) {
            throw new AssertionError("queue should be empty");
        }
    }

    static void testPutWithComparatorOrder() throws InterruptedException {
        PriorityBlockingQueue<Integer> q =
                new PriorityBlockingQueue<>(11, Comparator.reverseOrder());
        q.put(1);
        q.put(5);
        q.put(3);
        if (q.take().intValue() != 5) {
            throw new AssertionError("expected 5 first with reverse comparator");
        }
        if (q.take().intValue() != 3) {
            throw new AssertionError("expected 3 second with reverse comparator");
        }
        if (q.take().intValue() != 1) {
            throw new AssertionError("expected 1 third with reverse comparator");
        }
    }
}
