/*
 * @test
 * @summary Verify PriorityBlockingQueue.toArray returns a consistent copy of the queue contents
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testEmptyQueue();
        testContents();
        testReturnedArrayIsCopy();
        testAfterPoll();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyQueue() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        Object[] arr = q.toArray();
        check(arr != null, "toArray must not return null");
        check(arr.length == 0, "empty queue must yield empty array");
    }

    static void testContents() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>(4);
        q.add(5);
        q.add(1);
        q.add(3);
        Object[] arr = q.toArray();
        check(arr.length == 3, "length must equal queue size");
        Arrays.sort(arr);
        check(Arrays.equals(arr, new Object[] {1, 3, 5}),
              "toArray must contain all queued elements");
    }

    static void testReturnedArrayIsCopy() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(5);
        q.add(1);
        q.add(3);
        Object[] arr = q.toArray();
        arr[0] = Integer.valueOf(999);
        Object[] again = q.toArray();
        check(!Arrays.asList(again).contains(999),
              "modifying returned array must not affect the queue");
        check(q.size() == 3, "size must be unchanged");
        check(q.peek().equals(1), "head must remain the minimum element");
    }

    static void testAfterPoll() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(5);
        q.add(1);
        q.add(3);
        Integer head = q.poll();
        check(head.equals(1), "poll must return minimum element");
        Object[] arr = q.toArray();
        check(arr.length == 2, "toArray length must reflect removal");
        check(!Arrays.asList(arr).contains(1), "removed element must not appear");
    }
}
