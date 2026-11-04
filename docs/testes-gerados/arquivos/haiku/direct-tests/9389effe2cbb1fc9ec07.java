/*
 * @test
 * @summary Tests for the LinkedBlockingQueue(Collection) constructor
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new AssertionError(msg);
        }
    }

    static void testCopiesElementsInOrder() {
        List<Integer> src = new ArrayList<>();
        src.add(1);
        src.add(2);
        src.add(3);
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<Integer>(src);
        check(q.size() == 3, "size should be 3 but was " + q.size());
        check(q.poll().equals(1), "first element should be 1");
        check(q.poll().equals(2), "second element should be 2");
        check(q.poll().equals(3), "third element should be 3");
        check(q.poll() == null, "queue should be empty");
    }

    static void testEmptyCollection() {
        LinkedBlockingQueue<String> q =
            new LinkedBlockingQueue<String>(new ArrayList<String>());
        check(q.isEmpty(), "queue built from empty collection should be empty");
        check(q.size() == 0, "size should be 0");
        check(q.poll() == null, "poll on empty queue should return null");
    }

    static void testRemainingCapacityIsUnbounded() {
        LinkedBlockingQueue<Integer> q =
            new LinkedBlockingQueue<Integer>(Arrays.asList(7, 8));
        check(q.remainingCapacity() == Integer.MAX_VALUE,
              "remaining capacity should be Integer.MAX_VALUE");
    }

    static void testSourceModificationDoesNotAffectQueue() {
        List<Integer> src = new ArrayList<>();
        src.add(10);
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<Integer>(src);
        src.add(20);
        check(q.size() == 1, "queue should not reflect later source changes");
        check(q.poll().equals(10), "queue head should be 10");
    }

    static void testNullElementThrowsNPE() {
        boolean thrown = false;
        try {
            new LinkedBlockingQueue<Integer>(Arrays.asList(1, null, 3));
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null element should cause NullPointerException");
    }

    static void testNullCollectionThrowsNPE() {
        boolean thrown = false;
        try {
            List<Integer> nullList = null;
            new LinkedBlockingQueue<Integer>(nullList);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null collection should cause NullPointerException");
    }

    public static void main(String[] args) {
        testCopiesElementsInOrder();
        testEmptyCollection();
        testRemainingCapacityIsUnbounded();
        testSourceModificationDoesNotAffectQueue();
        testNullElementThrowsNPE();
        testNullCollectionThrowsNPE();
    }
}
