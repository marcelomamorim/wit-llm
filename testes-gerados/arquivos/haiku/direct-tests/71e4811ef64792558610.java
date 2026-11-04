/*
 * @test
 * @summary Verifies PriorityBlockingQueue.remove(Object) observable behavior: present, absent, null, duplicates and heap order preservation.
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testRemovePresentReturnsTrueAndDecrementsSize() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(3);
        q.add(1);
        q.add(2);
        check(q.remove(Integer.valueOf(2)), "remove of present element should return true");
        check(q.size() == 2, "size should be 2 after removal, was " + q.size());
        check(!q.contains(Integer.valueOf(2)), "removed element should not be contained");
    }

    static void testRemoveAbsentReturnsFalse() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(1);
        q.add(2);
        check(!q.remove(Integer.valueOf(99)), "remove of absent element should return false");
        check(q.size() == 2, "size should be unchanged when element is absent");
    }

    static void testRemoveFromEmptyReturnsFalse() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        check(!q.remove(Integer.valueOf(1)), "remove on empty queue should return false");
        check(q.isEmpty(), "queue should remain empty");
    }

    static void testRemoveNullReturnsFalse() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(1);
        check(!q.remove(null), "remove(null) should return false");
        check(q.size() == 1, "size should be unchanged after remove(null)");
    }

    static void testRemoveRemovesOnlyOneOccurrence() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(5);
        q.add(5);
        check(q.remove(Integer.valueOf(5)), "first remove of duplicate should return true");
        check(q.size() == 1, "only one occurrence should be removed, size was " + q.size());
        check(q.contains(Integer.valueOf(5)), "second occurrence should remain");
    }

    static void testHeapOrderPreservedAfterRemove() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(3);
        q.add(1);
        q.add(2);
        q.add(4);
        check(q.remove(Integer.valueOf(1)), "remove of minimum should return true");
        check(q.poll().intValue() == 2, "poll after remove should return 2");
        check(q.poll().intValue() == 3, "poll should then return 3");
        check(q.poll().intValue() == 4, "poll should then return 4");
        check(q.poll() == null, "queue should be empty");
    }

    static void testRemoveLastElementEmptiesQueue() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(7);
        check(q.remove(Integer.valueOf(7)), "remove of sole element should return true");
        check(q.isEmpty(), "queue should be empty");
        check(q.peek() == null, "peek should return null on empty queue");
    }

    public static void main(String[] args) {
        testRemovePresentReturnsTrueAndDecrementsSize();
        testRemoveAbsentReturnsFalse();
        testRemoveFromEmptyReturnsFalse();
        testRemoveNullReturnsFalse();
        testRemoveRemovesOnlyOneOccurrence();
        testHeapOrderPreservedAfterRemove();
        testRemoveLastElementEmptiesQueue();
    }
}
