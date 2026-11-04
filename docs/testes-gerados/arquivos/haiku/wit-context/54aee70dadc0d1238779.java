/*
 * @test
 * @summary Verify PriorityBlockingQueue(int) rejects non-positive initial capacities and accepts valid ones
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testNonPositiveCapacityRejected(0);
        testNonPositiveCapacityRejected(-1);
        testNonPositiveCapacityRejected(Integer.MIN_VALUE);
        testPositiveCapacityWorks();
        testMinimalCapacityOne();
    }

    static void testNonPositiveCapacityRejected(int capacity) {
        try {
            new PriorityBlockingQueue<Integer>(capacity);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new AssertionError("Expected IllegalArgumentException for capacity " + capacity);
    }

    static void testPositiveCapacityWorks() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>(10);
        if (!q.isEmpty()) {
            throw new AssertionError("New queue should be empty");
        }
        q.offer(3);
        q.offer(1);
        q.offer(2);
        if (q.size() != 3) {
            throw new AssertionError("Expected size 3 but was " + q.size());
        }
        if (q.poll() != 1 || q.poll() != 2 || q.poll() != 3) {
            throw new AssertionError("Elements not returned in natural priority order");
        }
        if (q.poll() != null) {
            throw new AssertionError("Queue should be empty after draining");
        }
    }

    static void testMinimalCapacityOne() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>(1);
        q.offer(5);
        q.offer(4);
        if (q.size() != 2) {
            throw new AssertionError("Expected size 2 but was " + q.size());
        }
        if (q.poll() != 4 || q.poll() != 5) {
            throw new AssertionError("Unexpected ordering with capacity 1");
        }
    }
}
