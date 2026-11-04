/*
 * @test
 * @summary Checks observable behavior of PriorityBlockingQueue.toArray()
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testEmptyQueueReturnsEmptyArray();
        testReturnsAllElements();
        testReturnedArrayIsIndependentCopy();
        testSnapshotReflectsRemovals();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyQueueReturnsEmptyArray() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        Object[] arr = q.toArray();
        check(arr != null, "toArray must not return null");
        check(arr.length == 0, "empty queue must yield empty array");
    }

    static void testReturnsAllElements() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>(4);
        q.offer(5);
        q.offer(1);
        q.offer(3);
        q.offer(2);
        Object[] arr = q.toArray();
        check(arr.length == q.size(), "array length must equal queue size");
        check(arr.length == 4, "expected 4 elements");
        Integer[] sorted = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++) {
            sorted[i] = (Integer) arr[i];
        }
        Arrays.sort(sorted);
        check(sorted[0] == 1 && sorted[1] == 2 && sorted[2] == 3 && sorted[3] == 5,
              "array must contain exactly the queued elements");
    }

    static void testReturnedArrayIsIndependentCopy() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.offer(7);
        q.offer(8);
        Object[] arr = q.toArray();
        arr[0] = Integer.valueOf(99);
        check(q.size() == 2, "modifying returned array must not change queue size");
        Object[] again = q.toArray();
        for (Object o : again) {
            check(!Integer.valueOf(99).equals(o),
                  "modifying returned array must not affect queue contents");
        }
    }

    static void testSnapshotReflectsRemovals() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.offer(10);
        q.offer(20);
        q.offer(30);
        Object[] before = q.toArray();
        check(before.length == 3, "expected 3 elements before removal");
        q.poll();
        Object[] after = q.toArray();
        check(after.length == 2, "expected 2 elements after poll");
        check(after.length == q.size(), "array length must match size after removal");
    }
}
