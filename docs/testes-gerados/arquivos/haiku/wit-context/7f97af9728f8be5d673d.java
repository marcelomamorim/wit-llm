/*
 * @test
 * @summary Witup-generated checks for PriorityBlockingQueue.offer(E): null rejection, size growth, and ordering with natural and custom comparators
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testOfferNullThrowsNPE();
        testOfferNullDoesNotChangeQueue();
        testOfferNonNullReturnsTrueAndIncrementsSize();
        testNaturalOrdering();
        testComparatorOrdering();
    }

    static void testOfferNullThrowsNPE() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        boolean thrown = false;
        try {
            q.offer(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("offer(null) did not throw NullPointerException");
        }
    }

    static void testOfferNullDoesNotChangeQueue() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.offer(7);
        try {
            q.offer(null);
        } catch (NullPointerException expected) {
        }
        if (q.size() != 1) {
            throw new RuntimeException("size changed after rejected null: " + q.size());
        }
        if (q.poll().intValue() != 7) {
            throw new RuntimeException("element altered after rejected null");
        }
    }

    static void testOfferNonNullReturnsTrueAndIncrementsSize() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>(1);
        for (int i = 0; i < 10; i++) {
            if (!q.offer(i)) {
                throw new RuntimeException("offer returned false for non-null element " + i);
            }
            if (q.size() != i + 1) {
                throw new RuntimeException("unexpected size " + q.size() + " after " + (i + 1) + " offers");
            }
        }
    }

    static void testNaturalOrdering() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.offer(5);
        q.offer(1);
        q.offer(3);
        int[] expected = {1, 3, 5};
        for (int e : expected) {
            int actual = q.poll().intValue();
            if (actual != e) {
                throw new RuntimeException("expected " + e + " but got " + actual);
            }
        }
        if (q.poll() != null) {
            throw new RuntimeException("queue should be empty");
        }
    }

    static void testComparatorOrdering() {
        Comparator<Integer> reverse = (a, b) -> b.compareTo(a);
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>(4, reverse);
        q.offer(5);
        q.offer(1);
        q.offer(3);
        int[] expected = {5, 3, 1};
        for (int e : expected) {
            int actual = q.poll().intValue();
            if (actual != e) {
                throw new RuntimeException("expected " + e + " but got " + actual);
            }
        }
    }
}
