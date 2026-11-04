/*
 * @test
 * @summary Verifies LinkedBlockingQueue(int capacity) rejects non-positive capacities and sets the observable capacity bound
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testNonPositiveCapacityRejected();
        testPositiveCapacityRemaining();
        testCapacityOneBoundsOffer();
        testMaxIntegerCapacity();
    }

    static void testNonPositiveCapacityRejected() {
        int[] invalid = {0, -1, Integer.MIN_VALUE};
        for (int c : invalid) {
            boolean thrown = false;
            try {
                new LinkedBlockingQueue<String>(c);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("Expected IllegalArgumentException for capacity " + c);
            }
        }
    }

    static void testPositiveCapacityRemaining() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(3);
        if (q.remainingCapacity() != 3) {
            throw new RuntimeException("Expected remainingCapacity 3, got " + q.remainingCapacity());
        }
        q.add(1);
        q.add(2);
        if (q.remainingCapacity() != 1) {
            throw new RuntimeException("Expected remainingCapacity 1, got " + q.remainingCapacity());
        }
        if (q.size() != 2) {
            throw new RuntimeException("Expected size 2, got " + q.size());
        }
    }

    static void testCapacityOneBoundsOffer() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>(1);
        if (!q.offer("a")) {
            throw new RuntimeException("First offer should succeed");
        }
        if (q.offer("b")) {
            throw new RuntimeException("Second offer should fail when full");
        }
        if (q.remainingCapacity() != 0) {
            throw new RuntimeException("Expected remainingCapacity 0, got " + q.remainingCapacity());
        }
    }

    static void testMaxIntegerCapacity() {
        LinkedBlockingQueue<Object> q = new LinkedBlockingQueue<>(Integer.MAX_VALUE);
        if (q.remainingCapacity() != Integer.MAX_VALUE) {
            throw new RuntimeException("Expected remainingCapacity MAX_VALUE, got " + q.remainingCapacity());
        }
    }
}
