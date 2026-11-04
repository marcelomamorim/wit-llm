/*
 * @test
 * @summary Witup-derived tests for ArrayBlockingQueue(int capacity, boolean fair) constructor
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAILED: " + msg);
        }
    }

    static void testZeroCapacityThrows(boolean fair) {
        try {
            new ArrayBlockingQueue<Integer>(0, fair);
            check(false, "capacity 0 should throw IllegalArgumentException (fair=" + fair + ")");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testNegativeCapacityThrows(boolean fair) {
        try {
            new ArrayBlockingQueue<Integer>(-1, fair);
            check(false, "capacity -1 should throw IllegalArgumentException (fair=" + fair + ")");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testPositiveCapacityCreatesEmptyQueue(boolean fair) {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<Integer>(1, fair);
        check(q.isEmpty(), "new queue should be empty (fair=" + fair + ")");
        check(q.remainingCapacity() == 1, "remainingCapacity should equal capacity (fair=" + fair + ")");
        check(q.offer(42), "offer into empty queue of capacity 1 should succeed");
        check(!q.offer(43), "offer into full queue should return false");
        check(q.size() == 1, "size should be 1 after one successful offer");
    }

    public static void main(String[] args) {
        testZeroCapacityThrows(false);
        testZeroCapacityThrows(true);
        testNegativeCapacityThrows(false);
        testNegativeCapacityThrows(true);
        testPositiveCapacityCreatesEmptyQueue(false);
        testPositiveCapacityCreatesEmptyQueue(true);
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
