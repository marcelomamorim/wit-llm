/*
 * @test
 * @summary Behavioral tests for ArrayBlockingQueue.toArray()
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testEmptyQueue();
        testFifoOrder();
        testWrapAround();
        testFullWrappedQueue();
        testReturnedArrayIsCopy();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyQueue() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        Object[] a = q.toArray();
        check(a != null, "toArray must not return null for empty queue");
        check(a.length == 0, "empty queue toArray must have length 0");
    }

    static void testFifoOrder() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(5);
        q.add(10);
        q.add(20);
        q.add(30);
        Object[] expected = {10, 20, 30};
        check(Arrays.equals(expected, q.toArray()),
              "toArray must return elements in FIFO order");
    }

    static void testWrapAround() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        q.add(1);
        q.add(2);
        q.add(3);
        check(q.poll() == 1, "poll must return head 1");
        q.add(4);
        Object[] expected = {2, 3, 4};
        check(Arrays.equals(expected, q.toArray()),
              "toArray must preserve order after wrap-around");
    }

    static void testFullWrappedQueue() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        q.add(1);
        q.add(2);
        q.add(3);
        check(q.poll() == 1, "poll must return head 1");
        q.add(4);
        check(q.size() == 3, "queue must be full");
        Object[] a = q.toArray();
        check(a.length == 3, "full queue toArray must have length 3");
        check(a[0].equals(2) && a[1].equals(3) && a[2].equals(4),
              "full wrapped queue toArray must return [2, 3, 4]");
    }

    static void testReturnedArrayIsCopy() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        q.add(1);
        q.add(2);
        Object[] a = q.toArray();
        a[0] = 99;
        Object[] again = q.toArray();
        check(again[0].equals(1), "modifying toArray result must not affect queue");
        check(q.size() == 2, "queue size must be unchanged");
        check(q.poll().equals(1), "queue head must still be 1");
    }
}
