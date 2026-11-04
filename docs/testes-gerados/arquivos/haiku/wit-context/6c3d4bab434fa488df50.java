/*
 * @test
 * @summary Witup generated tests for ArrayBlockingQueue(int capacity) constructor
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testZeroCapacityRejected();
        testNegativeCapacityRejected();
        testMinimumPositiveCapacityAccepted();
        testCapacityBoundIsObservable();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testZeroCapacityRejected() {
        try {
            new ArrayBlockingQueue<Integer>(0);
            throw new RuntimeException("expected IllegalArgumentException for capacity 0");
        } catch (IllegalArgumentException expected) {
        }
    }

    static void testNegativeCapacityRejected() {
        try {
            new ArrayBlockingQueue<Integer>(-1);
            throw new RuntimeException("expected IllegalArgumentException for capacity -1");
        } catch (IllegalArgumentException expected) {
        }
    }

    static void testMinimumPositiveCapacityAccepted() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<Integer>(1);
        check(q.remainingCapacity() == 1, "remainingCapacity should be 1");
        check(q.isEmpty(), "new queue should be empty");
    }

    static void testCapacityBoundIsObservable() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<Integer>(2);
        check(q.offer(1), "first offer should succeed");
        check(q.offer(2), "second offer should succeed");
        check(!q.offer(3), "offer on full queue should return false");
        check(q.remainingCapacity() == 0, "remainingCapacity should be 0 when full");
        check(q.size() == 2, "size should equal capacity when full");
    }
}
