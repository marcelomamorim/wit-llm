/*
 * @test
 * @summary Behavioral tests for ArrayBlockingQueue.remove(Object)
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testRemoveNullReturnsFalse();
        testRemoveAbsentReturnsFalse();
        testRemoveOnEmptyQueueReturnsFalse();
        testRemoveHead();
        testRemoveMiddle();
        testRemoveTail();
        testRemoveAcrossWrapAround();
        testRemoveUsesEquals();
        testRemoveFirstOccurrenceOnly();
    }

    static void testRemoveNullReturnsFalse() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(3);
        check(q.offer("a"), "offer a");
        check(!q.remove(null), "remove(null) must return false");
        check(q.size() == 1, "size unchanged after remove(null)");
        check("a".equals(q.poll()), "element preserved after remove(null)");
    }

    static void testRemoveAbsentReturnsFalse() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(3);
        check(q.offer("a"), "offer a");
        check(q.offer("b"), "offer b");
        check(!q.remove("z"), "absent element must return false");
        check(q.size() == 2, "size unchanged after absent remove");
    }

    static void testRemoveOnEmptyQueueReturnsFalse() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(2);
        check(!q.remove("a"), "remove on empty queue must return false");
        check(q.isEmpty(), "queue still empty");
    }

    static void testRemoveHead() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(3);
        q.offer("a");
        q.offer("b");
        q.offer("c");
        check(q.remove("a"), "remove head returns true");
        check("b".equals(q.poll()), "next head is b");
        check("c".equals(q.poll()), "next is c");
        check(q.poll() == null, "queue empty");
    }

    static void testRemoveMiddle() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(3);
        q.offer("a");
        q.offer("b");
        q.offer("c");
        check(q.remove("b"), "remove middle returns true");
        check(q.size() == 2, "size is 2");
        check("a".equals(q.poll()), "order a first");
        check("c".equals(q.poll()), "order c second");
    }

    static void testRemoveTail() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(3);
        q.offer("a");
        q.offer("b");
        q.offer("c");
        check(q.remove("c"), "remove tail returns true");
        check("a".equals(q.poll()), "order a");
        check("b".equals(q.poll()), "order b");
        check(q.poll() == null, "queue empty");
    }

    static void testRemoveAcrossWrapAround() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(3);
        q.offer("a");
        q.offer("b");
        q.offer("c");
        check("a".equals(q.poll()), "poll a");
        check(q.offer("d"), "offer d wraps around");
        check(q.remove("c"), "remove c after wrap returns true");
        check(q.size() == 2, "size is 2");
        check("b".equals(q.poll()), "order b");
        check("d".equals(q.poll()), "order d");
        check(q.poll() == null, "queue empty");
    }

    static void testRemoveUsesEquals() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(3);
        q.offer("a");
        q.offer("b");
        check(q.remove(new String("b")), "remove uses equals");
        check(q.size() == 1, "size is 1");
        check("a".equals(q.poll()), "remaining element a");
    }

    static void testRemoveFirstOccurrenceOnly() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(3);
        q.offer("x");
        q.offer("y");
        q.offer("x");
        check(q.remove("x"), "remove x returns true");
        check(q.size() == 2, "only one occurrence removed");
        check("y".equals(q.poll()), "order y");
        check("x".equals(q.poll()), "second x remains");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
