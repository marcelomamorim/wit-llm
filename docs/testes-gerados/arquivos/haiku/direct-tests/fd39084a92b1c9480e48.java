/*
 * @test
 * @summary Tests observable behavior of ArrayBlockingQueue.remove(Object), including null, absent, duplicate, empty and wrap-around cases
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testRemoveNullReturnsFalseAndKeepsQueue();
        testRemoveFromEmptyReturnsFalse();
        testRemoveAbsentElementReturnsFalse();
        testRemovePresentElementPreservesFifoOrder();
        testRemoveRemovesOnlyFirstOccurrence();
        testRemoveAcrossWrapAround();
    }

    static void testRemoveNullReturnsFalseAndKeepsQueue() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        q.add(Integer.valueOf(1));
        q.add(Integer.valueOf(2));
        check(!q.remove(null), "remove(null) must return false");
        check(q.size() == 2, "size must be unchanged after remove(null)");
        check(Arrays.toString(q.toArray()).equals("[1, 2]"), "contents changed after remove(null)");
    }

    static void testRemoveFromEmptyReturnsFalse() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        check(!q.remove(Integer.valueOf(1)), "remove on empty queue must return false");
        check(q.isEmpty(), "queue must remain empty");
    }

    static void testRemoveAbsentElementReturnsFalse() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        q.add(Integer.valueOf(1));
        q.add(Integer.valueOf(2));
        check(!q.remove(Integer.valueOf(9)), "remove of absent element must return false");
        check(Arrays.toString(q.toArray()).equals("[1, 2]"), "contents changed after failed remove");
    }

    static void testRemovePresentElementPreservesFifoOrder() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(4);
        q.add(Integer.valueOf(1));
        q.add(Integer.valueOf(2));
        q.add(Integer.valueOf(3));
        check(q.remove(Integer.valueOf(2)), "remove of present element must return true");
        check(q.size() == 2, "size must decrease by one");
        check(Arrays.toString(q.toArray()).equals("[1, 3]"), "FIFO order broken after remove");
        check(q.remainingCapacity() == 2, "remaining capacity must reflect removal");
    }

    static void testRemoveRemovesOnlyFirstOccurrence() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(4);
        q.add(Integer.valueOf(7));
        q.add(Integer.valueOf(5));
        q.add(Integer.valueOf(7));
        check(q.remove(Integer.valueOf(7)), "remove of duplicate must return true");
        check(Arrays.toString(q.toArray()).equals("[5, 7]"), "only first occurrence must be removed");
    }

    static void testRemoveAcrossWrapAround() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(4);
        q.add(Integer.valueOf(1));
        q.add(Integer.valueOf(2));
        q.add(Integer.valueOf(3));
        q.add(Integer.valueOf(4));
        check(q.poll().intValue() == 1, "poll must return head 1");
        check(q.poll().intValue() == 2, "poll must return head 2");
        q.add(Integer.valueOf(5));
        q.add(Integer.valueOf(6));
        check(Arrays.toString(q.toArray()).equals("[3, 4, 5, 6]"), "contents before wrapped remove are wrong");
        check(q.remove(Integer.valueOf(6)), "remove of element in wrapped segment must return true");
        check(Arrays.toString(q.toArray()).equals("[3, 4, 5]"), "order wrong after removing wrapped element");
        check(q.remove(Integer.valueOf(3)), "remove of head element must return true");
        check(Arrays.toString(q.toArray()).equals("[4, 5]"), "order wrong after removing head");
        check(q.remainingCapacity() == 2, "remaining capacity wrong after removals");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
