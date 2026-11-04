/*
 * @test
 * @summary Observable behavior of PriorityBlockingQueue.remove(Object) including null and absent arguments
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRemoveNullOnEmptyQueue() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        check(!q.remove(null), "remove(null) on empty queue should return false");
        check(q.size() == 0, "size should remain 0");
    }

    static void testRemoveNullOnNonEmptyQueue() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(1);
        q.add(2);
        check(!q.remove(null), "remove(null) should return false");
        check(q.size() == 2, "size should remain 2 after remove(null)");
        check(q.contains(1) && q.contains(2), "elements should be preserved after remove(null)");
    }

    static void testRemoveAbsentElement() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(4);
        q.add(8);
        check(!q.remove(Integer.valueOf(5)), "remove of absent element should return false");
        check(q.size() == 2, "size should be unchanged after removing absent element");
    }

    static void testRemovePresentElement() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(4);
        q.add(8);
        q.add(6);
        check(q.remove(Integer.valueOf(8)), "remove of present element should return true");
        check(q.size() == 2, "size should decrease by one");
        check(!q.contains(8), "removed element should no longer be contained");
        check(q.contains(4) && q.contains(6), "other elements should remain");
    }

    static void testRemoveDuplicateRemovesOneOccurrence() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(3);
        q.add(3);
        q.add(7);
        check(q.remove(Integer.valueOf(3)), "first remove of duplicate should return true");
        check(q.size() == 2, "only one occurrence should be removed");
        check(q.contains(3), "second occurrence should remain");
        check(q.remove(Integer.valueOf(3)), "second remove of duplicate should return true");
        check(!q.contains(3), "no occurrences should remain");
        check(q.size() == 1, "size should be 1");
    }

    static void testRemoveRootUpdatesPeek() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(5);
        q.add(1);
        q.add(3);
        check(q.remove(Integer.valueOf(1)), "removing minimum should return true");
        check(q.peek() == 3, "peek should return new minimum 3");
    }

    static void testHeapOrderPreservedAfterRemove() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(9);
        q.add(4);
        q.add(7);
        q.add(2);
        check(q.remove(Integer.valueOf(7)), "remove of 7 should return true");
        check(q.poll() == 2, "first poll should be 2");
        check(q.poll() == 4, "second poll should be 4");
        check(q.poll() == 9, "third poll should be 9");
        check(q.poll() == null, "queue should be empty");
    }

    public static void main(String[] args) {
        testRemoveNullOnEmptyQueue();
        testRemoveNullOnNonEmptyQueue();
        testRemoveAbsentElement();
        testRemovePresentElement();
        testRemoveDuplicateRemovesOneOccurrence();
        testRemoveRootUpdatesPeek();
        testHeapOrderPreservedAfterRemove();
    }
}
