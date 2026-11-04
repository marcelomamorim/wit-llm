/*
 * @test
 * @summary Basic observable behavior of the ArrayBlockingQueue(int capacity) constructor
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testCapacityIsRespected() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(2);
        check(q.isEmpty(), "new queue should be empty");
        check(q.size() == 0, "new queue size should be 0");
        check(q.remainingCapacity() == 2, "remainingCapacity should equal capacity");
        check(q.offer(1), "first offer should succeed");
        check(q.offer(2), "second offer should succeed");
        check(!q.offer(3), "offer to full queue should return false");
        check(q.remainingCapacity() == 0, "remainingCapacity should be 0 when full");
        check(q.size() == 2, "size should be 2 when full");
    }

    static void testFifoOrder() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(3);
        q.add("a");
        q.add("b");
        q.add("c");
        check("a".equals(q.poll()), "poll should return head first");
        check("b".equals(q.poll()), "poll should return second element next");
        check("c".equals(q.poll()), "poll should return last element");
        check(q.poll() == null, "poll on empty queue should return null");
    }

    static void testCapacityOne() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(1);
        check(q.remainingCapacity() == 1, "capacity 1 should report remainingCapacity 1");
        check(q.offer(42), "offer should succeed on capacity 1");
        check(!q.offer(43), "second offer should fail on capacity 1");
        check(q.peek() == 42, "peek should return the only element");
    }

    static void testNonPositiveCapacityRejected() {
        int[] invalid = {0, -1};
        for (int cap : invalid) {
            boolean thrown = false;
            try {
                new ArrayBlockingQueue<Integer>(cap);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            check(thrown, "capacity " + cap + " should throw IllegalArgumentException");
        }
    }

    public static void main(String[] args) {
        testCapacityIsRespected();
        testFifoOrder();
        testCapacityOne();
        testNonPositiveCapacityRejected();
    }
}
