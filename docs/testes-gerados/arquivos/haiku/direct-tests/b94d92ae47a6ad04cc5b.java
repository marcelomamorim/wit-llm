/*
 * @test
 * @summary Tests ArrayBlockingQueue(int capacity, boolean fair) constructor: invalid capacity rejection and observable capacity behavior
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testInvalidCapacity(0, false);
        testInvalidCapacity(0, true);
        testInvalidCapacity(-1, false);
        testInvalidCapacity(-1, true);
        testInvalidCapacity(Integer.MIN_VALUE, true);

        testCapacityBehavior(false);
        testCapacityBehavior(true);
        testSingleSlot(false);
        testSingleSlot(true);
    }

    static void testInvalidCapacity(int capacity, boolean fair) {
        try {
            new ArrayBlockingQueue<String>(capacity, fair);
            throw new RuntimeException("Expected IllegalArgumentException for capacity " + capacity);
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testCapacityBehavior(boolean fair) {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(2, fair);
        check(q.remainingCapacity() == 2, "initial remainingCapacity should be 2");
        check(q.isEmpty(), "new queue should be empty");
        check(q.offer(1), "first offer should succeed");
        check(q.offer(2), "second offer should succeed");
        check(!q.offer(3), "offer on full queue should return false");
        check(q.remainingCapacity() == 0, "remainingCapacity should be 0 when full");
        check(q.size() == 2, "size should be 2 when full");
        Integer head = q.poll();
        check(head != null && head == 1, "poll should return head 1 (FIFO)");
        check(q.remainingCapacity() == 1, "remainingCapacity should be 1 after poll");
        check(q.offer(3), "offer should succeed after a slot is freed");
    }

    static void testSingleSlot(boolean fair) {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(1, fair);
        check(q.remainingCapacity() == 1, "capacity 1 should report remainingCapacity 1");
        check(q.offer("a"), "offer into single slot should succeed");
        check(!q.offer("b"), "second offer into single slot should fail");
        check("a".equals(q.poll()), "poll should return \"a\"");
        check(q.poll() == null, "poll on empty queue should return null");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
