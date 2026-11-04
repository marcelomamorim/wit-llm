/*
 * @test
 * @summary Verifies observable behavior of PriorityBlockingQueue.size() across add, poll, remove, clear, drainTo and constructors
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testEmptySize();
        testSizeAfterOffers();
        testSizeAfterPoll();
        testSizeAfterRemoveObject();
        testSizeAfterClear();
        testSizeFromCollectionConstructor();
        testSizeWithCapacityAndComparator();
        testSizeAfterDrainTo();
        testSizeAfterFailedRemove();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void checkSize(PriorityBlockingQueue<Integer> q, int expected, String msg) {
        int actual = q.size();
        if (actual != expected) {
            throw new RuntimeException(msg + ": expected " + expected + " but was " + actual);
        }
    }

    static void testEmptySize() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        checkSize(q, 0, "new empty queue");
        PriorityBlockingQueue<Integer> q2 = new PriorityBlockingQueue<>(16);
        checkSize(q2, 0, "new queue with initial capacity");
    }

    static void testSizeAfterOffers() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(5);
        checkSize(q, 1, "after one add");
        q.offer(3);
        checkSize(q, 2, "after offer");
        q.put(9);
        checkSize(q, 3, "after put");
        q.add(1);
        q.add(7);
        checkSize(q, 5, "after several adds");
    }

    static void testSizeAfterPoll() throws InterruptedException {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(4);
        q.add(2);
        q.add(8);
        Integer head = q.poll();
        check(head != null && head == 2, "poll should return smallest element");
        checkSize(q, 2, "after poll");
        q.take();
        checkSize(q, 1, "after take");
        q.poll();
        checkSize(q, 0, "after polling last element");
        check(q.poll() == null, "poll on empty queue should return null");
        checkSize(q, 0, "size unchanged after poll on empty queue");
    }

    static void testSizeAfterRemoveObject() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(10);
        q.add(20);
        q.add(30);
        check(q.remove(20), "remove(Object) should report success");
        checkSize(q, 2, "after remove(Object)");
        check(q.remove(10), "remove(Object) of head should succeed");
        checkSize(q, 1, "after removing head");
    }

    static void testSizeAfterFailedRemove() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(1);
        check(!q.remove(Integer.valueOf(99)), "remove of absent element should return false");
        checkSize(q, 1, "size unchanged after failed remove");
    }

    static void testSizeAfterClear() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(1);
        q.add(2);
        q.add(3);
        q.clear();
        checkSize(q, 0, "after clear");
        q.add(4);
        checkSize(q, 1, "after clear and add");
    }

    static void testSizeFromCollectionConstructor() {
        List<Integer> src = Arrays.asList(5, 1, 4, 2);
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>(src);
        checkSize(q, 4, "size from collection constructor");
        q.poll();
        checkSize(q, 3, "size after poll on collection-built queue");
    }

    static void testSizeWithCapacityAndComparator() {
        PriorityBlockingQueue<Integer> q =
            new PriorityBlockingQueue<>(4, Comparator.reverseOrder());
        q.add(1);
        q.add(2);
        q.add(3);
        checkSize(q, 3, "size with comparator queue");
        check(q.poll() == 3, "reverse comparator should poll largest first");
        checkSize(q, 2, "size after poll with comparator");
    }

    static void testSizeAfterDrainTo() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(3);
        q.add(1);
        q.add(2);
        List<Integer> out = new ArrayList<>();
        int n = q.drainTo(out);
        check(n == 3, "drainTo should return 3");
        checkSize(q, 0, "size after drainTo");
        check(out.size() == 3, "drained collection should have 3 elements");
    }
}
