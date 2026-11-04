/*
 * @test
 * @summary Checks observable behavior of PriorityBlockingQueue.contains(Object)
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testEmptyQueue();
        testPresentAndAbsentElements();
        testNullArgument();
        testDifferentTypeArgument();
        testAfterRemove();
        testAfterPoll();
        testEqualButDistinctInstance();
        testWithComparator();
    }

    static void testEmptyQueue() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        check(!q.contains(Integer.valueOf(5)), "empty queue must not contain 5");
        check(!q.contains(null), "empty queue must not contain null");
    }

    static void testPresentAndAbsentElements() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(Integer.valueOf(5));
        q.add(Integer.valueOf(1));
        q.add(Integer.valueOf(9));
        check(q.contains(Integer.valueOf(5)), "5 should be present");
        check(q.contains(Integer.valueOf(1)), "1 should be present");
        check(q.contains(Integer.valueOf(9)), "9 should be present");
        check(!q.contains(Integer.valueOf(7)), "7 should be absent");
    }

    static void testNullArgument() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(Integer.valueOf(3));
        check(!q.contains(null), "contains(null) must return false");
    }

    static void testDifferentTypeArgument() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(Integer.valueOf(5));
        check(!q.contains("5"), "String \"5\" must not match Integer 5");
    }

    static void testAfterRemove() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(Integer.valueOf(5));
        q.add(Integer.valueOf(9));
        check(q.remove(Integer.valueOf(5)), "remove(5) should succeed");
        check(!q.contains(Integer.valueOf(5)), "5 must not be contained after removal");
        check(q.contains(Integer.valueOf(9)), "9 must still be contained");
    }

    static void testAfterPoll() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(Integer.valueOf(1));
        q.add(Integer.valueOf(4));
        Integer head = q.poll();
        check(head != null && head.intValue() == 1, "poll should return smallest element 1");
        check(!q.contains(Integer.valueOf(1)), "polled element must not be contained");
        check(q.contains(Integer.valueOf(4)), "4 must still be contained");
    }

    static void testEqualButDistinctInstance() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(Integer.valueOf(1000));
        check(q.contains(Integer.valueOf(1000)), "contains must use equals semantics");
    }

    static void testWithComparator() {
        PriorityBlockingQueue<String> q =
            new PriorityBlockingQueue<>(10, Comparator.reverseOrder());
        q.add("a");
        q.add("b");
        q.add("c");
        check(q.contains("b"), "b should be present with comparator queue");
        check(!q.contains("z"), "z should be absent with comparator queue");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
