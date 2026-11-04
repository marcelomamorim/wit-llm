/*
 * @test
 * @summary Verify PriorityBlockingQueue.add delegates to offer: returns true, keeps priority order, grows size and rejects null
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testAddReturnsTrue();
        testAddIncreasesSize();
        testNaturalOrdering();
        testComparatorOrdering();
        testAddNullThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testAddReturnsTrue() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        check(q.add(1) == true, "add should return true");
        check(q.add(2) == true, "add should return true for second element");
    }

    static void testAddIncreasesSize() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>(2);
        check(q.size() == 0, "new queue should be empty");
        q.add(10);
        check(q.size() == 1, "size should be 1 after one add");
        q.add(20);
        q.add(5);
        check(q.size() == 3, "size should be 3 after three adds");
    }

    static void testNaturalOrdering() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(3);
        q.add(1);
        q.add(2);
        check(q.poll() == 1, "first poll should be smallest element");
        check(q.poll() == 2, "second poll should be 2");
        check(q.poll() == 3, "third poll should be 3");
        check(q.poll() == null, "queue should be empty after polls");
    }

    static void testComparatorOrdering() {
        PriorityBlockingQueue<Integer> q =
            new PriorityBlockingQueue<>(11, Comparator.reverseOrder());
        q.add(3);
        q.add(7);
        q.add(5);
        check(q.poll() == 7, "reverse comparator should poll largest first");
        check(q.poll() == 5, "reverse comparator should poll 5 second");
        check(q.poll() == 3, "reverse comparator should poll 3 last");
    }

    static void testAddNullThrowsNPE() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        boolean thrown = false;
        try {
            q.add(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "add(null) should throw NullPointerException");
        check(q.size() == 0, "queue should remain empty after rejected null");
    }
}
